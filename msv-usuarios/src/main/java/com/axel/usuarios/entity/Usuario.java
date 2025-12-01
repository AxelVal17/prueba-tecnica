package com.axel.usuarios.entity;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


@Document(collection = "usuarios")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Usuario {
	

    @Id
    private Long id;

    @NotNull(message = "El nombre es obligatorio")
    @Size(min = 1, max = 50, message = "El nombre debe tener entre 1 y 50 caracteres")
    @Pattern(regexp = "^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$", message = "El nombre solo puede contener letras y espacios")
    private String nombre;

    @NotNull(message = "El apellido paterno es obligatorio")
    @Size(min = 1, max = 50, message = "El apellido paterno debe tener entre 1 y 50 caracteres")
    @Pattern(regexp = "^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$", message = "El apellido paterno solo puede contener letras y espacios")
    private String aPaterno;

    @NotNull(message = "El apellido materno es obligatorio")
    @Size(min = 1, max = 50, message = "El apellido materno debe tener entre 1 y 50 caracteres")
    @Pattern(regexp = "^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$", message = "El apellido materno solo puede contener letras y espacios")
    private String aMaterno;

    private List<Long> rolesId; // [1, 3, 5] - IDs de roles del otro microservicio

    @Transient
    private List<Object> roles; // Se llenará con datos del Rol-Service via HTTP

}
