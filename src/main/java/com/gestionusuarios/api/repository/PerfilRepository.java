package com.gestionusuarios.api.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.gestionusuarios.api.model.Perfil;

import java.util.Optional;

@Repository
public interface PerfilRepository extends MongoRepository<Perfil, String> {

    Optional<Perfil> findByCuentaId(String cuentaId);
}
