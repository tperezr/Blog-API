package com.api.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostDto {
    private Long id;
    private String titulo;
    private String contenido;
    private String imagen;
    private Date fechaCreacion;
    private String categoria;
    private String usuario;
}
