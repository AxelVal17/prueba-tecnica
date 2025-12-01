package com.axel.roles.repository;


import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.axel.roles.entity.Rol;

@Repository
public interface RolRepository extends MongoRepository<Rol, String>  {
	
	  // 1. Buscar roles por nombre exacto
    List<Rol> findByNombre(String nombre);

    // 2. Buscar roles que contengan el nombre (búsqueda parcial case insensitive)
    List<Rol> findByNombreContainingIgnoreCase(String nombre);

    // 3. Verificar si existe un rol con ese nombre
    boolean existsByNombre(String nombre);

    // 4. Contar roles por nombre
    long countByNombre(String nombre);

    // 5. Eliminar rol por nombre
    void deleteByNombre(String nombre);

    // 6. Buscar roles por múltiples IDs
    @Query("{ '_id': { $in: ?0 } }")
    List<Rol> findByIdIn(List<String> ids);

}
