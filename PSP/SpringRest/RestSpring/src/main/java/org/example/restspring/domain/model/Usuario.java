package org.example.restspring.domain.model;


import java.time.LocalDateTime;

public record Usuario(
    int id,
    String username,
    String password,
    String email,
    String codigo,
    Boolean activado,
    String nombre,
    Boolean esAdmin,
    LocalDateTime fecha
) {
}
