package com.ista.backend.apirest.model;


import javax.persistence.*;
import lombok.*;

import java.io.Serializable;


@Entity
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Usuario {


	@Id
    @Column(name = "id_usuario")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nombre", length = 100)
    private String nombre;

    @Column(name = "apellido", length = 100)
    private String apellido;

    @Column(name = "clave", length = 100, nullable = false, unique = true)
    private String clave;
    
    @Column(name = "email", length = 200, nullable = false, unique = true)
    private String email;

    @Column(name = "estado")
    private String estado;

	@NonNull
	private String titulo;
	private String imagenPath;
    private String cedula;

	@Transient
	private String imagenUrl;
    private String cedulaUrl;

}
