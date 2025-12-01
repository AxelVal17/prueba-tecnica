package com.axel.roles.entity;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Document(collection = "roles")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Rol {
	
	@Id
    @NotNull(message = "El ID del rol es obligatorio")
    private String id;
    
    @NotNull(message = "El nombre del rol es obligatorio")
    @Size(min = 1, max = 20, message = "El nombre del rol debe tener entre 1 y 20 caracteres")
    private String nombre;

}
