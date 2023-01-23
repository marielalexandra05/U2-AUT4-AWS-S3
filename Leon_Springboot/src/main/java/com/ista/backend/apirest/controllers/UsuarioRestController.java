package com.ista.backend.apirest.controllers;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.ista.backend.apirest.repository.UsuarioRepository;
import com.ista.backend.apirest.service.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ista.backend.apirest.model.Usuario;
import com.ista.backend.apirest.service.UsuarioService;


@RestController
@RequestMapping("/api")
public class UsuarioRestController {

	 @Autowired
	   private UsuarioService usuarioService;

	 @GetMapping("/listaUsuarios")
	    public ResponseEntity<List<Usuario>> obtenerLista() {
	        return new ResponseEntity<>(usuarioService.findByAll(), HttpStatus.OK);
	    }

	    @PostMapping("/crearUsuario")
	    public ResponseEntity<?> createUser (@RequestBody Usuario u) {
	        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.save(u));
	    }

	    @GetMapping("/{id}")
	    public ResponseEntity<?> readUser (@PathVariable (value = "id") Integer id){
	        Optional<Usuario> oUser = Optional.ofNullable(usuarioService.findById(id));

	        if (!oUser.isPresent()){
	            return ResponseEntity.notFound().build();
	        }
	        return ResponseEntity.ok(oUser);
	    }

	    @PutMapping("/updateUser/{id}")
	    public ResponseEntity<?> updateUser (@RequestBody Usuario use, @PathVariable (value = "id") Integer id) {
	        Optional<Usuario> us = Optional.ofNullable(usuarioService.findById(id));
	        if (!us.isPresent()) {
	            return ResponseEntity.notFound().build();
	        }
	            us.get().setNombre(use.getNombre());
	            us.get().setApellido(use.getApellido());
	            us.get().setClave(use.getClave());
	            us.get().setEmail(use.getEmail());
	            us.get().setEstado(use.getEstado());
	            return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.save(us.get()));
	    }
	    
	    @DeleteMapping("/EliminarUsuario/{id}")
	    public ResponseEntity<?> deleteUser (@PathVariable (value = "id") Integer id) {
	    	usuarioService.delete(id);
	        return new ResponseEntity<>(HttpStatus.OK);
	    }



	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private S3Service s3Service;

	@GetMapping
	List<Usuario> getAll(){
		return usuarioRepository.findAll()
				.stream()
				.peek(usuario -> usuario.setImagenUrl(s3Service.getObjectUrl(usuario.getImagenPath())))
				.collect(Collectors.toList());
	}

	@PostMapping
	Usuario create(@RequestBody Usuario usuario) {
		//return usuarioRepository.save(usuario);
		usuarioRepository.save(usuario);
		usuario.setImagenUrl(s3Service.getObjectUrl(usuario.getImagenPath()));
		usuario.setCedulaUrl(s3Service.getObjectUrl(usuario.getCedula()));
		return usuario;
	}

	
}
