package com.axel.roles.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.axel.commons.controllers.CommonControllers;
import com.axel.commons.dtos.RolRequest;
import com.axel.commons.dtos.RolResponse;
import com.axel.roles.service.RolService;

@RestController
@RequestMapping("/roles")
@Validated
public class RolController extends CommonControllers<RolRequest, RolResponse, RolService> {

	public RolController(RolService service) {
		super(service);
	}

	// ENDPOINT EXTRA para buscar por nombre - así tendrás exactamente 6 endpoints
	@GetMapping("/buscar-nombre/{nombre}")
	public ResponseEntity<List<RolResponse>> buscarPorNombre(@PathVariable String nombre) {
		return ResponseEntity.ok(service.buscarPorNombre(nombre));
	}
}
