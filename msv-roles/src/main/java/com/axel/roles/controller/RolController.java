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
