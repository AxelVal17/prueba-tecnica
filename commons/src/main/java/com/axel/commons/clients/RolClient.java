package com.axel.commons.clients;

import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.axel.commons.dtos.RolRequest;
import com.axel.commons.dtos.RolResponse;

@FeignClient(name = "roles")  // Nombre del microservicio rol-service
public interface RolClient {
	
	 // 1. GET - Todos los roles
    @GetMapping
    List<RolResponse> obtenerTodosLosRoles();

    // 2. GET - Rol por ID
    @GetMapping("/{id}")
    RolResponse obtenerRolPorId(@PathVariable Long id);

    // 3. GET - Rol por NOMBRE
    @GetMapping("/nombre/{nombre}")
    List<RolResponse> obtenerRolesPorNombre(@PathVariable String nombre);

    // 4. POST - Crear rol
    @PostMapping
    RolResponse crearRol(@RequestBody RolRequest rolRequest);

    // 5. PUT - Actualizar rol
    @PutMapping("/{id}")
    RolResponse actualizarRol(@PathVariable Long id, @RequestBody RolRequest rolRequest);

    // 6. DELETE - Eliminar rol
    @DeleteMapping("/{id}")
    void eliminarRol(@PathVariable Long id);

    // Método adicional: Verificar si rol existe
    @GetMapping("/existe/{id}")
    boolean rolExiste(@PathVariable Long id);

    // Método adicional: Obtener múltiples roles por IDs
    @GetMapping("/por-ids")
    List<RolResponse> obtenerRolesPorIds(@RequestBody List<Long> ids);

    // Método adicional: Contar usuarios con un rol específico
    @GetMapping("/{idRol}/cantidad-usuarios")
    long contarUsuariosConRol(@PathVariable Long idRol);
	
}
