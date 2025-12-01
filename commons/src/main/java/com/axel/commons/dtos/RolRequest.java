package com.axel.commons.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RolRequest(
		
		 @NotBlank(message = "El nombre es requerido")
	     @Size(min = 1, max = 50, message = "El nombre debe tener entre 1 y 50 caracteres")
		 String nombre
		
		) {

}
