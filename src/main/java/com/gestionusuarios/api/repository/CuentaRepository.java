package com.gestionusuarios.api.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;


import com.gestionusuarios.api.model.Cuenta;

@Repository
public interface CuentaRepository extends MongoRepository<Cuenta, String> {

    Optional<Cuenta> findByNombreUsuario(String nombreUsuario);

    Boolean existsByNombreUsuario(String nombreUsuario);

}
