package com.axel.roles.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.axel.commons.controllers.CommonControllers;
import com.axel.commons.dtos.RolRequest;
import com.axel.commons.dtos.RolResponse;
import com.axel.roles.service.RolService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/roles")
@Validated
@Slf4j
public class RolController extends CommonControllers<RolRequest, RolResponse, RolService> {

    public RolController(RolService service) {
        super(service);
    }
    
  
    @GetMapping
    public ResponseEntity<?> listarTodos() {
        log.info("Listando todos los roles");
        
        List<RolResponse> roles = service.listar();
        
        if (roles.isEmpty()) {
            return ResponseEntity.ok(new Object() {
                public final String mensaje = "No hay roles registrados";
                public final List<RolResponse> datos = roles;
            });
        }
        
        return ResponseEntity.ok(new Object() {
            public final String mensaje = String.format("Se encontraron %d roles", roles.size());
            public final List<RolResponse> datos = roles;
        });
    }
    
    // Sobrescribir GET por ID
    @Override
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerPorId(@PathVariable @Positive(message = "El id debe ser positivo") String id) {
        return service.buscarPorId(id)
            .<ResponseEntity<?>>map(rol -> ResponseEntity.ok(new Object() {
                public final String mensaje = String.format("Rol '%s' obtenido con el ID: %s", 
                    rol.nombre(), rol.id());
                public final RolResponse datos = rol;
            }))
            .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new Object() {
                    public final String error = String.format("Rol no encontrado con el ID: %s", id);
                }));
    }
    
    // Sobrescribir POST (CREAR) 
    @Override
    @PostMapping
    public ResponseEntity<?> insertar(@Valid @RequestBody RolRequest request) {
        RolResponse rolCreado = service.registrar(request);
        
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(new Object() {
                public final String mensaje = String.format("Rol '%s' creado con el ID: %s", 
                    rolCreado.nombre(), rolCreado.id());
                public final RolResponse datos = rolCreado;
            });
    }
    
    // Sobrescribir PUT (ACTUALIZAR) 
    @Override
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(
            @PathVariable @Positive(message = "El id debe ser positivo") String id,  
            @Valid @RequestBody RolRequest request) {
        
        RolResponse rolActualizado = service.actualizar(request, id);
        
        if (rolActualizado != null) {
            return ResponseEntity.ok(new Object() {
                public final String mensaje = String.format("Rol '%s' actualizado con el ID: %s", 
                    rolActualizado.nombre(), rolActualizado.id());
                public final RolResponse datos = rolActualizado;
            });
        }
        
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(new Object() {
                public final String error = String.format("No se pudo actualizar. Rol no encontrado con el ID: %s", id);
            });
    }
    
    // Sobrescribir DELETE (ELIMINAR) 
    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable @Positive(message = "El id debe ser positivo") String id) {
        return service.buscarPorId(id)
            .<ResponseEntity<?>>map(rol -> {
                service.eliminar(id);
                return ResponseEntity.ok(new Object() {
                    public final String mensaje = String.format("Rol '%s' eliminado con el ID: %s", 
                        rol.nombre(), rol.id());
                });
            })
            .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new Object() {
                    public final String error = String.format("No se pudo eliminar. Rol no encontrado con el ID: %s", id);
                }));
    }
    
    @GetMapping("/buscar-nombre/{nombre}")
    public ResponseEntity<List<RolResponse>> buscarPorNombre(@PathVariable String nombre) {
        return ResponseEntity.ok(service.buscarPorNombre(nombre));
    }
    
    @PostMapping("/por-ids")
    public ResponseEntity<List<RolResponse>> obtenerRolesPorIds(@RequestBody List<String> ids) {
        log.info("Obteniendo roles por IDs: {}", ids);
        return ResponseEntity.ok(service.obtenerRolesPorIds(ids));
    }
	
}
