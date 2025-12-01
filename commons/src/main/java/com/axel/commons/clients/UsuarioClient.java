package com.axel.commons.clients;

import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.axel.commons.dtos.UsuarioRequest;
import com.axel.commons.dtos.UsuarioResponse;

@FeignClient(name = "usuarios")  // Nombre del microservicio usuario-service
public interface UsuarioClient {
	
	  // 1. GET - Todos los usuarios
    @GetMapping
    List<UsuarioResponse> obtenerTodosLosUsuarios();

    // 2. GET - Usuario por ID
    @GetMapping("/{id}")
    UsuarioResponse obtenerUsuarioPorId(@PathVariable Long id);

    // 3. GET - Usuario por NOMBRE
    @GetMapping("/nombre/{nombre}")
    List<UsuarioResponse> obtenerUsuariosPorNombre(@PathVariable String nombre);

    // 4. POST - Crear usuario
    @PostMapping
    UsuarioResponse crearUsuario(@RequestBody UsuarioRequest usuarioRequest);

    // 5. PUT - Actualizar usuario
    @PutMapping("/{id}")
    UsuarioResponse actualizarUsuario(@PathVariable Long id, @RequestBody UsuarioRequest usuarioRequest);

    // 6. DELETE - Eliminar usuario
    @DeleteMapping("/{id}")
    void eliminarUsuario(@PathVariable Long id);

    // Método adicional: Verificar si usuario tiene un rol específico
    @GetMapping("/{idUsuario}/tiene-rol/{idRol}")
    boolean usuarioTieneRol(@PathVariable Long idUsuario, @PathVariable Long idRol);

    // Método adicional: Obtener usuarios por rol
    @GetMapping("/por-rol/{idRol}")
    List<UsuarioResponse> obtenerUsuariosPorRol(@PathVariable Long idRol);

}
