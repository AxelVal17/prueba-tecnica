package com.axel.commons.controllers;


import java.util.List;

import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.axel.commons.services.CommonService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;


@Validated
public class CommonControllers <RQ, RS, S extends CommonService<RQ, RS>> {
	
	protected final S service;


	public CommonControllers(S service) {
		this.service = service;
	}
	
	 @GetMapping
	    public ResponseEntity<List<RS>> listarTodos() {
	        return ResponseEntity.ok(service.listar());
	    }

	   @GetMapping("/{id}")
	    public ResponseEntity<?> obtenerPorId(@PathVariable @Positive(message = "El id debe ser positivo") String id) {
	        Optional<RS> resultado = service.buscarPorId(id);
	        return resultado.map(ResponseEntity::ok)
	                       .orElse(ResponseEntity.notFound().build());
	    }

	    @PostMapping
	    public ResponseEntity<?> insertar(@Valid @RequestBody RQ request) {
	        return ResponseEntity.status(HttpStatus.CREATED).body(service.registrar(request));
	    }

	    @PutMapping("/{id}")
	    public ResponseEntity<?> actualizar(@PathVariable @Positive(message = "El id debe ser positivo") String id,
	            @Valid @RequestBody RQ request) {
	        RS resultado = service.actualizar(request, id);
	        if (resultado != null) {
	            return ResponseEntity.ok(resultado);
	        }
	        return ResponseEntity.notFound().build();
	    }

	    @DeleteMapping("/{id}")
	    public ResponseEntity<?> eliminar(@PathVariable @Positive(message = "El id debe ser positivo") String id) {
	        service.eliminar(id);
	        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	    }
	
}
