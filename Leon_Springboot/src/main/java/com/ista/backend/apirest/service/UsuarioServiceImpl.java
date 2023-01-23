package com.ista.backend.apirest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import com.ista.backend.apirest.model.Usuario;
import com.ista.backend.apirest.repository.UsuarioRepository;

@Service
public class UsuarioServiceImpl extends GenericServiceImpl<Usuario, Integer> implements UsuarioService {

	@Autowired
    UsuarioRepository usuarioRepository;
	
	@Override
	public CrudRepository<Usuario, Integer> getDao() {
		return usuarioRepository;
	}

}
