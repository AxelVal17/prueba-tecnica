package com.axel.roles.service;

import java.util.List;
import com.axel.commons.dtos.RolRequest;
import com.axel.commons.dtos.RolResponse;
import com.axel.commons.services.CommonService;

public interface RolService extends CommonService<RolRequest, RolResponse> {

	 List<RolResponse> buscarPorNombre(String nombre);
	    boolean existePorNombre(String nombre);
	    
	    List<RolResponse> obtenerRolesPorIds(List<String> ids);

}
