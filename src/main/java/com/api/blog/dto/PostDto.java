package com.api.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostDto {
    private Long id;
    @NotNull
    private String titulo;
    @NotNull
    private String contenido;
    @NotNull
    private String imagen;
    @NotNull
    private Date fechaCreacion;
    @NotNull
    private String categoria;
    @NotNull
    private String usuario;
}
