package com.axel.usuarios.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.axel.commons.dtos.UsuarioRequest;
import com.axel.commons.dtos.UsuarioResponse;
import com.axel.usuarios.entity.Usuario;
import com.axel.usuarios.mapper.UsuarioMapper;
import com.axel.usuarios.repository.UsuarioRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UsuarioServiceImpl implements UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private UsuarioMapper usuarioMapper;

	@Override
	public List<UsuarioResponse> listar() {
		log.info("Listando todos los usuarios");
		// Obtener todos los usuarios y mostrar los ROLES como OBJETOS
		return usuarioRepository.findAll().stream().map(usuarioMapper::entityToResponse).collect(Collectors.toList());
	}

	@Override
	public Optional<UsuarioResponse> buscarPorId(Long id) {
		log.info("Buscando usuario por ID: {}", id);
		// Buscar usuario por ID y mostrar sus ROLES como OBJETOS
		return usuarioRepository.findById(id).map(usuarioMapper::entityToResponse);
	}

	@Override
	public List<UsuarioResponse> buscarPorNombre(String nombre) {
		log.info("Buscando usuarios por nombre: {}", nombre);
		// Buscar usuarios por nombre y mostrar ROLES como OBJETOS
		return usuarioRepository.findByNombreContainingIgnoreCase(nombre).stream().map(usuarioMapper::entityToResponse)
				.collect(Collectors.toList());
	}

	@Override
	public UsuarioResponse registrar(UsuarioRequest request) {
		log.info("Registrando nuevo usuario: {}", request);
		// Insertar usuario - la generación del ID debe ser automática
		Usuario usuario = usuarioMapper.requestToEntity(request);
		Usuario usuarioGuardado = usuarioRepository.save(usuario);
		log.info("Usuario registrado exitosamente con ID: {}", usuarioGuardado.getId());
		return usuarioMapper.entityToResponse(usuarioGuardado);
	}

	@Override
	public UsuarioResponse actualizar(UsuarioRequest request, Long id) {
		log.info("Actualizando usuario con ID: {}", id);
		// Actualizar usuario existente
		return usuarioRepository.findById(id).map(usuarioExistente -> {
			usuarioExistente.setNombre(request.nombre());
			usuarioExistente.setAPaterno(request.aPaterno());
			usuarioExistente.setAMaterno(request.aMaterno());
			usuarioExistente.setRolesId(request.rolesId());

			Usuario usuarioActualizado = usuarioRepository.save(usuarioExistente);
			log.info("Usuario actualizado exitosamente: {}", usuarioActualizado);
			return usuarioMapper.entityToResponse(usuarioActualizado);
		}).orElse(null);
	}

	@Override
	public void eliminar(Long id) {
		log.info("Eliminando usuario con ID: {}", id);
		// Eliminar usuario por ID
		usuarioRepository.deleteById(id);
		log.info("Usuario con ID: {} eliminado exitosamente", id);
	}

	@Override
	public boolean existePorId(Long id) {
		log.debug("Verificando existencia de usuario con ID: {}", id);
		// Verificar si existe usuario (útil para validaciones)
		return usuarioRepository.existsById(id);
	}

	@Override
	public List<UsuarioResponse> buscarPorRolId(Long rolId) {
		log.info("Buscando usuarios por rol ID: {}", rolId);
		// Buscar usuarios por rol específico
		return usuarioRepository.findByRolesIdContaining(rolId).stream().map(usuarioMapper::entityToResponse)
				.collect(Collectors.toList());
	}
}
