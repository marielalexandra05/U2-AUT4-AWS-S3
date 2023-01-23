package com.ista.backend.apirest.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ista.backend.apirest.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer>{

}
