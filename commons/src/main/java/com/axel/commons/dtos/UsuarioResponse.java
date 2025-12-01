package com.axel.commons.dtos;

import java.util.List;

public record UsuarioResponse(
		
		Long id,
	    String nombre,
	    String aPaterno,
	    String aMaterno,
	    List<Long> rolesId,
	    List<Object> roles  // Roles completos (se llenarán desde el servicio)
		
		) {

}
