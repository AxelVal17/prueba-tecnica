package com.axel.commons.services;

import java.util.List;
import java.util.Optional;

public interface CommonService<RQ, RS> {

	List<RS> listar();

	Optional<RS> buscarPorId(String id);

	RS registrar(RQ request);

	RS actualizar(RQ request, String id);

	void eliminar(String id);

	boolean existePorId(String id);

}
