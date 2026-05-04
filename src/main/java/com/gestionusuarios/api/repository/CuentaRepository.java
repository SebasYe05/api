package com.gestionusuarios.api.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;


import com.gestionusuarios.api.model.Cuenta;

@Repository
public interface CuentaRepository extends MongoRepository<Cuenta, ObjectId> {

    Optional<Cuenta> findByNombreUsuario(String nombreUsuario);

    Boolean existsByNombreUsuario(String nombreUsuario);

}
