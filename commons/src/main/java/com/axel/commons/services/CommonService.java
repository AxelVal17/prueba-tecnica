package com.axel.commons.services;

import java.util.List;
import java.util.Optional;

public interface CommonService<RQ, RS> {

	List<RS> listar();

	Optional<RS> buscarPorId(Long id);

	RS registrar(RQ request);

	RS actualizar(RQ request, Long id);

	void eliminar(Long id);

	boolean existePorId(Long id);

}
