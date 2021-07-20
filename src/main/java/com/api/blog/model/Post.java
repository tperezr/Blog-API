package com.api.blog.model;

import lombok.Data;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.Date;

@Entity
@Data
@SQLDelete(sql = "update post set is_deleted = 1 where id =?")
@Where(clause = "isDeleted = 0")
public class Post {
    @Id
    private Long id;
    private String titulo;
    private String contenido;
    private String imagen;
    private Date fechaCreacion;
    private boolean isDeleted = Boolean.FALSE;

    @ManyToOne
    @JoinColumn(name = "id_categoria")
    private Category categoria;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private User usuario;
}
