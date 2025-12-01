package com.axel.usuarios.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.axel.commons.dtos.UsuarioRequest;
import com.axel.commons.dtos.UsuarioResponse;
import com.axel.commons.mappers.CommonMapper;
import com.axel.usuarios.entity.Usuario;

@Component
public class UsuarioMapper implements CommonMapper<UsuarioRequest, UsuarioResponse, Usuario> {

	@Override
	public UsuarioResponse entityToResponse(Usuario entity) {
		// Verificar si la entidad es nula para evitar NullPointerException
		if (entity == null) return null;
				
		// Convertir la entidad Usuario a UsuarioResponse con todos sus campos
		return new UsuarioResponse(
			entity.getId(),
			entity.getNombre(),
			entity.getAPaterno(),
			entity.getAMaterno(),
			entity.getRolesId(),
			// Convertir la colección de roles a List<Object> para el response
			entity.getRoles() != null ? List.of(entity.getRoles().toArray()) : List.of()
		);
	}

	@Override
	public Usuario requestToEntity(UsuarioRequest request) {
		// Verificar si el request es nulo para evitar NullPointerException
		if (request == null) return null;
		
		
		// Crear una nueva instancia de la entidad Usuario
		Usuario usuario = new Usuario();
		// Mapear los campos del request a la entidad
		usuario.setNombre(request.nombre());
		usuario.setAPaterno(request.aPaterno());
		usuario.setAMaterno(request.aMaterno());
		usuario.setRolesId(request.rolesId());
		return usuario;
	}
	
	
	/*
	 Método sobrecargado para convertir UsuarioRequest a entidad Usuario
	 incluyendo la lista completa de roles
	 
	 @param request Objeto con los datos de entrada del usuario
	 @param roles Lista completa de objetos rol
	 @return Entidad Usuario con todos los datos mapeados
	 */
	
	public Usuario requestToEntity(UsuarioRequest request, List<Object> roles) {
		if (request == null) return null;
		
		Usuario usuario = requestToEntity(request);
		usuario.setRoles(roles != null ? roles : List.of());
		return usuario;
	}

}
