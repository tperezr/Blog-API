package com.api.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostDetailsDto {
    private Long id;
    private String titulo;
    private String imagen;
    private String categoria;
    private Date fechaCreacion;

}
