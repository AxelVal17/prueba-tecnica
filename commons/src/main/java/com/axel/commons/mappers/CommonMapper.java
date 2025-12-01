package com.axel.commons.mappers;

public interface CommonMapper <RQ, RS, E>  {
	
	RS entityToResponse(E entity);
    E requestToEntity(RQ request);

}
