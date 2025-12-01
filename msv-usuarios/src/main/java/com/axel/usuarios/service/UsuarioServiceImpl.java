package com.axel.usuarios.service;

import java.util.ArrayList;
import java.util.List;

import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.axel.commons.clients.RolClient;
import com.axel.commons.dtos.RolResponse;
import com.axel.commons.dtos.UsuarioRequest;
import com.axel.commons.dtos.UsuarioResponse;
import com.axel.usuarios.entity.Usuario;
import com.axel.usuarios.mapper.UsuarioMapper;
import com.axel.usuarios.repository.UsuarioRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.concurrent.atomic.AtomicLong;



@Slf4j
@Service
public class UsuarioServiceImpl implements UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private UsuarioMapper usuarioMapper;
	
	@Autowired
    private RolClient rolClient; 
	
	 
    // Contador local para IDs de usuario
    private final AtomicLong usuarioCounter = new AtomicLong(2000L); // Empezará en 2001

    @Override
    public List<UsuarioResponse> listar() {
        log.info("Listando todos los usuarios");
        
        List<Usuario> usuarios = usuarioRepository.findAll();
        log.info("Usuarios encontrados: {}", usuarios.size());
        
        return usuarios.stream()
            .map(usuario -> {
                // Obtener roles completos por IDs
                List<RolResponse> rolesCompletos = obtenerRolesPorIds(usuario.getRolesId());
                
                // Establecer los roles en el usuario existente
                usuario.setRoles(rolesCompletos);
                
                return usuarioMapper.entityToResponse(usuario);
            })
            .collect(Collectors.toList());
    }

	 @Override
	    public Optional<UsuarioResponse> buscarPorId(String id) {
	        log.info("Buscando usuario por ID: {}", id);
	        
	        return usuarioRepository.findById(id)
	            .map(usuario -> {
	                List<RolResponse> rolesCompletos = obtenerRolesPorIds(usuario.getRolesId());
	                usuario.setRoles(rolesCompletos);
	                return usuarioMapper.entityToResponse(usuario);
	            });
	    }

	 @Override
	    public List<UsuarioResponse> buscarPorNombre(String nombre) {
	        log.info("Buscando usuarios por nombre: {}", nombre);
	        
	        return usuarioRepository.findByNombreContainingIgnoreCase(nombre).stream()
	            .map(usuario -> {
	                List<RolResponse> rolesCompletos = obtenerRolesPorIds(usuario.getRolesId());
	                usuario.setRoles(rolesCompletos);
	                return usuarioMapper.entityToResponse(usuario);
	            })
	            .collect(Collectors.toList());
	    }

	@Override
    public UsuarioResponse registrar(UsuarioRequest request) {
        log.info("Registrando nuevo usuario: {}", request);
        
        // Obtener roles completos
        List<RolResponse> rolesCompletos = obtenerRolesPorIds(request.rolesId());
        
        // Usar el método que incluye roles
        Usuario usuario = usuarioMapper.requestToEntity(request, rolesCompletos);
        
        // Generar ID
        Long nextId = usuarioCounter.incrementAndGet();
        if (nextId > 2999L) {
            throw new IllegalStateException("Límite de usuarios alcanzado");
        }
        String shortId = String.format("%04d", nextId);
        usuario.setId(shortId);
        
        Usuario usuarioGuardado = usuarioRepository.save(usuario);
        log.info("Usuario registrado exitosamente con ID: {}", usuarioGuardado.getId());
        
        return usuarioMapper.entityToResponse(usuarioGuardado);
    }

	@Override
    public UsuarioResponse actualizar(UsuarioRequest request, String id) {
        log.info("Actualizando usuario con ID: {}", id);
        
        return usuarioRepository.findById(id)
            .map(usuarioExistente -> {
                // Obtener roles completos nuevos
                List<RolResponse> nuevosRoles = obtenerRolesPorIds(request.rolesId());
                
                usuarioExistente.setNombre(request.nombre());
                usuarioExistente.setAPaterno(request.aPaterno());
                usuarioExistente.setAMaterno(request.aMaterno());
                usuarioExistente.setRolesId(request.rolesId());
                usuarioExistente.setRoles(nuevosRoles);
                
                Usuario usuarioActualizado = usuarioRepository.save(usuarioExistente);
                log.info("Usuario actualizado exitosamente: {}", usuarioActualizado);
                
                return usuarioMapper.entityToResponse(usuarioActualizado);
            })
            .orElse(null);
    }

	@Override
	public void eliminar(String id) { 
		log.info("Eliminando usuario con ID: {}", id);
		// Eliminar usuario por ID
		usuarioRepository.deleteById(id);
		log.info("Usuario con ID: {} eliminado exitosamente", id);
	}

	@Override
	public boolean existePorId(String id) {  
		log.debug("Verificando existencia de usuario con ID: {}", id);
		// Verificar si existe usuario (útil para validaciones)
		return usuarioRepository.existsById(id);
	}

	@Override
    public List<UsuarioResponse> buscarPorRolId(String rolId) {
        log.info("Buscando usuarios por rol ID: {}", rolId);
        
        return usuarioRepository.findByRolesIdContaining(rolId).stream()
            .map(usuario -> {
                List<RolResponse> rolesCompletos = obtenerRolesPorIds(usuario.getRolesId());
                usuario.setRoles(rolesCompletos);
                return usuarioMapper.entityToResponse(usuario);
            })
            .collect(Collectors.toList());
    }
	
	
	
	private List<RolResponse> obtenerRolesPorIds(List<String> ids) {
	    if (ids == null || ids.isEmpty()) {
	        log.warn("La lista de IDs de roles está vacía o es nula");
	        return new ArrayList<>();
	    }
	    
	    log.info("=== LLAMANDO A ROLCLIENT PARA OBTENER ROLES ===");
	    log.info("IDs a buscar: {}", ids);
	    
	    // Primero, verifica que el cliente no sea nulo
	    if (rolClient == null) {
	        log.error("rolClient es NULL!");
	        return new ArrayList<>();
	    }
	    
	    // Intenta la llamada - sin try-catch para ver el error real
	    List<RolResponse> roles = rolClient.obtenerRolesPorIds(ids);
	    
	    log.info("Respuesta recibida: {}", roles != null ? roles.size() + " roles" : "NULL");
	    
	    if (roles != null && !roles.isEmpty()) {
	        for (RolResponse rol : roles) {
	            log.info("  - Rol: id={}, nombre={}", rol.id(), rol.nombre());
	        }
	    } else {
	        log.warn("No se obtuvieron roles o la lista está vacía");
	    }
	    
	    return roles != null ? roles : new ArrayList<>();
	}
	 
	 
	 
	
}
