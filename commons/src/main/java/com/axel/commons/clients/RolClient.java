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

@FeignClient(name = "msv-roles") 
public interface RolClient {
	
	 // 1. GET - Todos los roles
	 @GetMapping("/roles")  
	 List<RolResponse> obtenerTodosLosRoles();

    // 2. GET - Rol por ID
	 @GetMapping("/roles/{id}")  
	 RolResponse obtenerRolPorId(@PathVariable String id);

    // 3. GET - Rol por NOMBRE
	 @GetMapping("/roles/buscar-nombre/{nombre}")  
	 List<RolResponse> obtenerRolesPorNombre(@PathVariable String nombre);

    // 4. POST - Crear rol
    @PostMapping
    RolResponse crearRol(@RequestBody RolRequest rolRequest);

    // 5. PUT - Actualizar rol
    @PutMapping("/{id}")
    RolResponse actualizarRol(@PathVariable String id, @RequestBody RolRequest rolRequest);

    // 6. DELETE - Eliminar rol
    @DeleteMapping("/{id}")
    void eliminarRol(@PathVariable String id);

    // Método adicional: Verificar si rol existe
    @GetMapping("/existe/{id}")
    boolean rolExiste(@PathVariable String id);

    // Método adicional: Obtener múltiples roles por IDs
    @PostMapping("/roles/por-ids")  
    List<RolResponse> obtenerRolesPorIds(@RequestBody List<String> ids);

    // Método adicional: Contar usuarios con un rol específico
    @GetMapping("/{idRol}/cantidad-usuarios")
    long contarUsuariosConRol(@PathVariable String idRol);
	
}
