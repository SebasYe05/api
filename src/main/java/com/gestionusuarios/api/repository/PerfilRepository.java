package com.gestionusuarios.api.repository;

import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.gestionusuarios.api.model.Perfil;

@Repository
public interface PerfilRepository extends MongoRepository<Perfil, ObjectId> {
    Optional<Perfil> findById(ObjectId id);
}
