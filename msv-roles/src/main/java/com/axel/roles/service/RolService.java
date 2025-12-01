package com.axel.roles.service;

import java.util.List;
import com.axel.commons.dtos.RolRequest;
import com.axel.commons.dtos.RolResponse;
import com.axel.commons.services.CommonService;

public interface RolService extends CommonService<RolRequest, RolResponse> {

	List<RolResponse> listar();

	List<RolResponse> buscarPorNombre(String nombre);

	RolResponse registrar(RolRequest request);

	RolResponse actualizar(RolRequest request, Long id);

	void eliminar(Long id);

	boolean existePorId(Long id);

	boolean existePorNombre(String nombre);

}
