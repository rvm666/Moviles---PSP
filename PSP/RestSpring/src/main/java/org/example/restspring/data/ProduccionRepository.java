package org.example.restspring.data;

import org.example.restspring.domain.model.Produccion;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ProduccionRepository {

    private final List<Produccion> producciones;

    public ProduccionRepository() {
        this.producciones = new ArrayList<>();
        producciones.add(new Produccion(1, "The Shawshank Redemption", 1994, "Frank Darabont", "Drama", 1));
        producciones.add(new Produccion(2, "The Godfather", 1972, "Francis Ford Coppola", "Crime", 2));
        producciones.add(new Produccion(3, "The Dark Knight", 2008, "Christopher Nolan", "Action", 3));
    }



    public Produccion save(Produccion produccion) {
        int id = nextId();
        Produccion produccionGuardada = new Produccion(id, produccion.titulo(), produccion.anio(), produccion.director(), produccion.genero(), produccion.userId());
        producciones.add(produccionGuardada);
        return produccionGuardada;
    }

    public List<Produccion> getByUserId(int userId){
        return producciones.stream().filter(produccion -> produccion.userId() == userId).toList();
    }

    public List<Produccion> getAll() {
        return producciones;
    }

    public boolean delete(int id, int userId) {
        producciones.removeIf(produccion -> produccion.id() == id && produccion.userId() == userId);
        Produccion produccion = getById(id);
        return produccion == null;
    }

    public Produccion getById(int id) {
        return producciones.stream().filter(produccion -> produccion.id() == id).findFirst().orElse(null);
    }

    public Produccion update(int id, Produccion produccion) {
        Produccion antigua = getById(id);
        if(antigua == null){
            return null;
        }
        int index = producciones.indexOf(antigua);
        Produccion actualizada = new Produccion(id, produccion.titulo(), produccion.anio(), produccion.director(), produccion.genero(), produccion.userId());
        producciones.set(index, actualizada);
        return actualizada;
    }

    public Produccion getByName(String name) {
        return producciones.stream().filter(produccion -> produccion.titulo().equals(name)).findFirst().orElse(null);
    }

    public int nextId(){
        return producciones.size()+1;
    }
}
