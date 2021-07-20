package com.api.blog.model;

import ch.qos.logback.core.boolex.EvaluationException;
import lombok.Data;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@SQLDelete(sql = "update post set is_deleted = 1 where id =?")
@Where(clause = "is_deleted = 0")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    private String contenido;
    private String imagen;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fechaCreacion;

    private boolean isDeleted = Boolean.FALSE;

    @ManyToOne
    @JoinColumn(name = "id_categoria")
    private Category categoria;

    @ManyToOne
    @JoinColumn(name = "id_user")
    private User usuario;
}
