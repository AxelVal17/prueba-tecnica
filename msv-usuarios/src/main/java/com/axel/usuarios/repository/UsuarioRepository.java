package com.axel.usuarios.repository;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.axel.usuarios.entity.Usuario;

@Repository
public interface UsuarioRepository extends MongoRepository<Usuario, String> {
	
	// 1. Buscar usuarios por nombre exacto
    List<Usuario> findByNombre(String nombre);

    // 2. Buscar usuarios que contengan el nombre (búsqueda parcial case insensitive)
    List<Usuario> findByNombreContainingIgnoreCase(String nombre);

    // 3. Buscar usuarios por apellido paterno
    List<Usuario> findByAPaternoContainingIgnoreCase(String aPaterno);

    // 4. Buscar usuarios por apellido materno
    List<Usuario> findByAMaternoContainingIgnoreCase(String aMaterno);

    // 5. Buscar usuarios que tengan un rol específico
    //Usando query (para cuando rolesId es una lista)
    @Query("{ 'rolesId': ?0 }")
    List<Usuario> findByRolesIdContaining(String rolId);

    // 6. Buscar usuarios que tengan múltiples roles
    @Query("{ 'rolesId': { $in: ?0 } }")
    List<Usuario> findByRolesIdIn(List<String> rolesIds);

    // 7. Verificar si existe un usuario con ese nombre
    boolean existsByNombre(String nombre);

    // 8. Contar usuarios por nombre
    long countByNombre(String nombre);

    // 9. Eliminar usuario por nombre
    void deleteByNombre(String nombre);

}
