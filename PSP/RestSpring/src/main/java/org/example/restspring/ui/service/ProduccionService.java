package org.example.restspring.ui.service;

import org.example.restspring.data.ProduccionRepository;
import org.example.restspring.domain.model.Produccion;
import org.example.restspring.ui.dto.ProduccionDTO;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class ProduccionService {

    private final ProduccionRepository produccionRepository;

    public ProduccionService(ProduccionRepository produccionRepository) {
        this.produccionRepository = produccionRepository;
    }

    public List<ProduccionDTO> getAll(){
        List<Produccion> producciones = produccionRepository.getAll();
        List<ProduccionDTO> produccionesDTOS = new ArrayList<>();
        producciones.forEach(p ->
                produccionesDTOS.add(
                        new ProduccionDTO(
                                p.titulo(), p.director(), p.genero()
                        )));
        return produccionesDTOS;
    }

    public List<ProduccionDTO> getByUserId(int userId){
        List<Produccion> produccion = produccionRepository.getByUserId(userId);
        List<ProduccionDTO> produccionDTOS = new ArrayList<>();
        if(produccion.isEmpty()) return Collections.emptyList();
        produccion.forEach(p -> produccionDTOS.add(new ProduccionDTO(p.titulo(), p.director(), p.genero())));
        return produccionDTOS;
    }

    public ProduccionDTO getById(int id){
        Produccion produccion = produccionRepository.getById(id);
        if(produccion == null) return null;
        return new ProduccionDTO(produccion.titulo(), produccion.director(), produccion.genero());
    }

    public ProduccionDTO getByName(String name){
        Produccion produccion = produccionRepository.getByName(name);
        if(produccion == null) return null;
        return new ProduccionDTO(produccion.titulo(), produccion.director(), produccion.genero());
    }

    public ProduccionDTO save(Produccion produccion){
        Produccion produccion1 = produccionRepository.save(produccion);
        return new ProduccionDTO(produccion1.titulo(), produccion1.director(), produccion1.genero());
    }

    public ProduccionDTO update(int id, ProduccionDTO produccionDTO){
        Produccion produccionAntes = produccionRepository.getById(id);
        if(produccionAntes == null) return null;
        Produccion produccion1 = produccionRepository.update(id, new Produccion(id, produccionDTO.titulo(), produccionAntes.anio(), produccionDTO.director(), produccionDTO.genero(), produccionAntes.userId()));
        return new ProduccionDTO(produccion1.titulo(), produccion1.director(), produccion1.genero());
    }

    public boolean delete(int id, int userId){
        return produccionRepository.delete(id, userId);
    }
}
