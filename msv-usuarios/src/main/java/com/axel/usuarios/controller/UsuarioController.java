package com.axel.usuarios.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.axel.commons.controllers.CommonControllers;
import com.axel.commons.dtos.UsuarioRequest;
import com.axel.commons.dtos.UsuarioResponse;
import com.axel.usuarios.service.UsuarioService;

@RestController
@RequestMapping("/usuarios")
@Validated
public class UsuarioController extends CommonControllers<UsuarioRequest, UsuarioResponse, UsuarioService>{

    public UsuarioController(UsuarioService service) {
        super(service);
    }
    
    
    @GetMapping("/buscar-nombre/{nombre}")
    public ResponseEntity<List<UsuarioResponse>> buscarPorNombre(
            @PathVariable String nombre) {
        return ResponseEntity.ok(service.buscarPorNombre(nombre));
    }

}
