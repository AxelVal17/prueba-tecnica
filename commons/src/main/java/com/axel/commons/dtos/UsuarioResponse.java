package com.axel.commons.dtos;

import java.util.List;

public record UsuarioResponse(
		
		String id,
	    String nombre,
	    String aPaterno,
	    String aMaterno,
	    List<String> rolesId,
	    List<RolResponse> roles  // Roles completos (se llenarán desde el servicio)
		
		) {

}
