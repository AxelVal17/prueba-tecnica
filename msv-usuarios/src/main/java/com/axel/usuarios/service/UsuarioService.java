package com.axel.usuarios.service;

import java.util.List;
import java.util.Optional;

import com.axel.commons.dtos.UsuarioRequest;
import com.axel.commons.dtos.UsuarioResponse;
import com.axel.commons.services.CommonService;

public interface UsuarioService extends CommonService<UsuarioRequest, UsuarioResponse> {
    
	 // Métodos específicos de Usuario:
    List<UsuarioResponse> buscarPorNombre(String nombre);
    
    // CORREGIR: Cambiar Long por String
    List<UsuarioResponse> buscarPorRolId(String rolId);  

}
