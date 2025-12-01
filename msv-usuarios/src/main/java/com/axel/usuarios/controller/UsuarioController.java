package com.axel.usuarios.controller;

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
import com.axel.commons.dtos.UsuarioRequest;
import com.axel.commons.dtos.UsuarioResponse;
import com.axel.usuarios.service.UsuarioService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive; 

@RestController
@RequestMapping("/usuarios")
@Validated
public class UsuarioController extends CommonControllers<UsuarioRequest, UsuarioResponse, UsuarioService>{

    public UsuarioController(UsuarioService service) {
        super(service);
    }
    
    
    @GetMapping
    public ResponseEntity<?> listarTodos() {
        List<UsuarioResponse> usuarios = service.listar();
        
        if (usuarios.isEmpty()) {
            return ResponseEntity.ok(new Object() {
                public final String mensaje = "No hay usuarios registrados";
                public final List<UsuarioResponse> datos = usuarios;
            });
        }
        
        return ResponseEntity.ok(new Object() {
            public final String mensaje = String.format("Se encontraron %d usuarios", usuarios.size());
            public final List<UsuarioResponse> datos = usuarios;
        });
    }
    
    // Sobrescribir GET por ID 
    @Override
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerPorId(@PathVariable @Positive(message = "El id debe ser positivo") String id) {
        return service.buscarPorId(id)
            .<ResponseEntity<?>>map(usuario -> {
                String nombreCompleto = String.format("%s %s %s", 
                    usuario.nombre(), usuario.aPaterno(), usuario.aMaterno()).trim();
                return ResponseEntity.ok(new Object() {
                    public final String mensaje = String.format("Usuario '%s' obtenido con el ID: %s", 
                        nombreCompleto, usuario.id());
                    public final UsuarioResponse datos = usuario;
                });
            })
            .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new Object() {
                    public final String error = String.format("Usuario no encontrado con el ID: %s", id);
                }));
    }
    
    // Sobrescribir POST (CREAR) 
    @Override
    @PostMapping
    public ResponseEntity<?> insertar(@Valid @RequestBody UsuarioRequest request) {
        UsuarioResponse usuarioCreado = service.registrar(request);
        
        String nombreCompleto = String.format("%s %s %s", 
            usuarioCreado.nombre(), usuarioCreado.aPaterno(), usuarioCreado.aMaterno()).trim();
        
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(new Object() {
                public final String mensaje = String.format("Usuario '%s' creado con el ID: %s", 
                    nombreCompleto, usuarioCreado.id());
                public final UsuarioResponse datos = usuarioCreado;
            });
    }
    
    // Sobrescribir PUT (ACTUALIZAR) 
    @Override
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(
            @PathVariable @Positive(message = "El id debe ser positivo") String id, 
            @Valid @RequestBody UsuarioRequest request) {
        
        UsuarioResponse usuarioActualizado = service.actualizar(request, id);
        
        if (usuarioActualizado != null) {
            String nombreCompleto = String.format("%s %s %s", 
                usuarioActualizado.nombre(), usuarioActualizado.aPaterno(), 
                usuarioActualizado.aMaterno()).trim();
            
            return ResponseEntity.ok(new Object() {
                public final String mensaje = String.format("Usuario '%s' actualizado con el ID: %s", 
                    nombreCompleto, usuarioActualizado.id());
                public final UsuarioResponse datos = usuarioActualizado;
            });
        }
        
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(new Object() {
                public final String error = String.format("No se pudo actualizar. Usuario no encontrado con el ID: %s", id);
            });
    }
    
    // Sobrescribir DELETE (ELIMINAR) 
    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable @Positive(message = "El id debe ser positivo") String id) {
        return service.buscarPorId(id)
            .<ResponseEntity<?>>map(usuario -> {
                service.eliminar(id);
                String nombreCompleto = String.format("%s %s %s", 
                    usuario.nombre(), usuario.aPaterno(), usuario.aMaterno()).trim();
                
                return ResponseEntity.ok(new Object() {
                    public final String mensaje = String.format("Usuario '%s' eliminado con el ID: %s", 
                        nombreCompleto, usuario.id());
                });
            })
            .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new Object() {
                    public final String error = String.format("No se pudo eliminar. Usuario no encontrado con el ID: %s", id);
                }));
    }
    
    @GetMapping("/buscar-nombre/{nombre}")
    public ResponseEntity<List<UsuarioResponse>> buscarPorNombre(
            @PathVariable String nombre) {
        return ResponseEntity.ok(service.buscarPorNombre(nombre));
    }
}
