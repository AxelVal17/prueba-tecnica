package com.axel.commons.dtos;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UsuarioRequest(
		
		 @NotBlank(message = "El nombre es requerido")
		 @Pattern(regexp = "^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$", message = "El nombre solo puede contener letras y espacios")
	     @Size(min = 1, max = 50, message = "El nombre debe tener entre 1 y 50 caracteres")
		 String nombre,
		 
		 @NotNull(message = "El apellido paterno es obligatorio")
		 @Size(min = 1, max = 50, message = "El apellido paterno debe tener entre 1 y 50 caracteres")
		 @Pattern(regexp = "^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$", message = "El apellido paterno solo puede contener letras y espacios")
		 String aPaterno,
		 
		 @NotNull(message = "El apellido materno es obligatorio")
		 @Size(min = 1, max = 50, message = "El apellido materno debe tener entre 1 y 50 caracteres")
		 @Pattern(regexp = "^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$", message = "El apellido materno solo puede contener letras y espacios")
		 String aMaterno,
		 
		  // Lista de IDs de roles - puede ser null o vacía
		  List<String> rolesId
		
		) {

}
