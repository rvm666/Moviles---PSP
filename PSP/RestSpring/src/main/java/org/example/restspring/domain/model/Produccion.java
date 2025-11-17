package org.example.restspring.domain.model;

public record Produccion(
    int id,
    String titulo,
    int anio,
    String director,
    String genero,
    int userId
) {
}
