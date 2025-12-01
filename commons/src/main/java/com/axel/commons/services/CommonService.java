package com.axel.commons.services;

import java.util.List;
import java.util.Optional;

public interface CommonService<RQ, RS> {
	
	List<RS> listar();
    Optional<RS> buscarPorId(Long id);
    List<RS> buscarPorNombre(String nombre);
    RS registrar(RQ request);
    RS actualizar(RQ request, Long id);
    void eliminar(Long id);
    boolean existePorId(Long id);
    List<RS> buscarPorRolId(Long rolId);

}
