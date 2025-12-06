package org.example.restspring.domain.model;


public record Usuario(
    int id,
    String username,
    String password,
    String email,
    String codigo,
    Boolean activado,
    String nombre,
    Boolean esAdmin
) {
}
