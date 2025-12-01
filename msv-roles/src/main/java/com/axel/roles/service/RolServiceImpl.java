package com.axel.roles.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.axel.commons.dtos.RolRequest;
import com.axel.commons.dtos.RolResponse;
import com.axel.roles.entity.Rol;
import com.axel.roles.mapper.RolMapper;
import com.axel.roles.repository.RolRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class RolServiceImpl implements RolService {

	// Inyección de dependencias para acceder a la capa de datos
	@Autowired
	private RolRepository rolRepository;

	// Inyección del mapper para convertir entre entidades y DTOs
	@Autowired
	private RolMapper rolMapper;

	/**
	 Obtiene todos los roles existentes en la base de datos
	 
	 @return Lista de RolResponse con todos los roles
	 */
	@Override
	public List<RolResponse> listar() {
		log.info("Listando todos los roles");
		// Consulta todos los roles, los convierte a DTOs y los retorna
		List<RolResponse> roles = rolRepository.findAll().stream().map(rolMapper::entityToResponse)
				.collect(Collectors.toList());
		log.info("Se encontraron {} roles", roles.size());
		return roles;
	}

	/**
	 Busca un rol específico por su ID
	 
	 @param id Identificador único del rol
	 @return Optional con el RolResponse si existe, vacío si no
	 */
	@Override
	public Optional<RolResponse> buscarPorId(Long id) {
		log.info("Buscando rol por ID: {}", id);
		// Busca el rol por ID y lo convierte a DTO si existe
		Optional<RolResponse> rol = rolRepository.findById(id).map(rolMapper::entityToResponse);
		if (rol.isPresent()) {
			log.info("Rol encontrado con ID: {}", id);
		} else {
			log.warn("No se encontró rol con ID: {}", id);
		}
		return rol;
	}

	/**
	 Busca roles que contengan el nombre especificado (búsqueda parcial case
	 insensitive)
	 
	 @param nombre Texto a buscar en los nombres de roles
	 @return Lista de RolResponse que coinciden con la búsqueda
	 */
	@Override
	public List<RolResponse> buscarPorNombre(String nombre) {
		log.info("Buscando roles por nombre: {}", nombre);
		// Realiza búsqueda parcial por nombre y convierte resultados a DTOs
		List<RolResponse> roles = rolRepository.findByNombreContainingIgnoreCase(nombre).stream()
				.map(rolMapper::entityToResponse).collect(Collectors.toList());
		log.info("Se encontraron {} roles con el nombre: {}", roles.size(), nombre);
		return roles;
	}

	/**
	 Registra un nuevo rol en el sistema
	 
	 @param request DTO con los datos del rol a crear
	 @return RolResponse con los datos del rol creado
	 */
	@Override
	public RolResponse registrar(RolRequest request) {
		log.info("Registrando nuevo rol: {}", request);
		// Convierte el DTO a entidad, guarda en BD y retorna como DTO
		Rol rol = rolMapper.requestToEntity(request);
		Rol rolGuardado = rolRepository.save(rol);
		log.info("Rol registrado exitosamente con ID: {}", rolGuardado.getId());
		return rolMapper.entityToResponse(rolGuardado);
	}

	/**
	  Actualiza un rol existente
	 
	  @param request DTO con los nuevos datos del rol
	  @param id      Identificador del rol a actualizar
	  @return RolResponse con los datos actualizados, o null si no existe
	 */
	@Override
	public RolResponse actualizar(RolRequest request, Long id) {
		log.info("Actualizando rol con ID: {}", id);
		// Busca el rol, actualiza sus datos y guarda los cambios
		return rolRepository.findById(id).map(rolExistente -> {
			log.info("Rol encontrado para actualización: {}", rolExistente);

			// Actualiza solo el nombre del rol
			rolExistente.setNombre(request.nombre());

			Rol rolActualizado = rolRepository.save(rolExistente);
			log.info("Rol actualizado exitosamente: {}", rolActualizado);
			return rolMapper.entityToResponse(rolActualizado);
		}).orElse(null); // Retorna null si el rol no existe
	}

	/**
	 Elimina un rol por su ID
	 
	 @param id Identificador del rol a eliminar
	 */
	@Override
	public void eliminar(Long id) {
		log.info("Eliminando rol con ID: {}", id);
		// Elimina el rol de la base de datos
		rolRepository.deleteById(id);
		log.info("Rol con ID: {} eliminado exitosamente", id);
	}

	/**
	 Verifica si existe un rol con el ID especificado
	 
	 @param id Identificador a verificar
	 @return true si existe, false si no
	 */
	@Override
	public boolean existePorId(Long id) {
		log.debug("Verificando existencia de rol con ID: {}", id);
		// Consulta si existe un rol con el ID proporcionado
		boolean existe = rolRepository.existsById(id);
		log.debug("Rol con ID: {} existe: {}", id, existe);
		return existe;
	}

	/**
	 Verifica si existe un rol con el nombre especificado
	 
	 @param nombre Nombre a verificar
	 @return true si existe, false si no
	 */
	@Override
	public boolean existePorNombre(String nombre) {
		log.debug("Verificando existencia de rol con nombre: {}", nombre);
		// Consulta si existe un rol con el nombre proporcionado
		boolean existe = rolRepository.existsByNombre(nombre);
		log.debug("Rol con nombre: {} existe: {}", nombre, existe);
		return existe;
	}

}
