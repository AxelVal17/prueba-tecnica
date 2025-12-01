package com.axel.roles.mapper;

import org.springframework.stereotype.Component;

import com.axel.commons.dtos.RolRequest;
import com.axel.commons.dtos.RolResponse;
import com.axel.commons.mappers.CommonMapper;
import com.axel.roles.entity.Rol;

@Component
public class RolMapper implements CommonMapper<RolRequest, RolResponse, Rol> {

	 @Override
	    public RolResponse entityToResponse(Rol entity) {
	        // Verificar si la entidad es nula para evitar NullPointerException
	        if (entity == null) return null;
	        
	        // Convertir la entidad Rol a RolResponse
	        return new RolResponse(
	            entity.getId(),
	            entity.getNombre()
	        );
	    }

	    @Override
	    public Rol requestToEntity(RolRequest request) {
	        // Verificar si el request es nulo para evitar NullPointerException
	        if (request == null) return null;
	        
	        // Crear una nueva instancia de la entidad Rol
	        Rol rol = new Rol();
	        // Mapear los campos del request a la entidad
	        rol.setNombre(request.nombre());
	        return rol;
	    }
	

}
