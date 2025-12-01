package com.axel.usuarios.service;

import java.util.List;
import java.util.Optional;

import com.axel.commons.dtos.UsuarioRequest;
import com.axel.commons.dtos.UsuarioResponse;
import com.axel.commons.services.CommonService;

public interface UsuarioService extends CommonService<UsuarioRequest, UsuarioResponse> {
    
    List<UsuarioResponse> listar();
    Optional<UsuarioResponse> buscarPorId(Long id);
    List<UsuarioResponse> buscarPorNombre(String nombre);
    UsuarioResponse registrar(UsuarioRequest request);
    UsuarioResponse actualizar(UsuarioRequest request, Long id);
    void eliminar(Long id);
    boolean existePorId(Long id);
    List<UsuarioResponse> buscarPorRolId(Long rolId);

}
