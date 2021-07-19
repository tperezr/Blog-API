package com.api.blog.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.Date;

@Entity
@Data
public class Post {
    @Id
    private Long id;
    private String titulo;
    private String contenido;
    private String imagen;
    private Date fechaCreacion;

    @ManyToOne
    private Category categoria;

    @ManyToOne
    private User usuario;
}
