package org.example.restspring.domain.service;

import org.example.restspring.data.ProduccionRepository;
import org.example.restspring.domain.errores.NotFoundException;
import org.example.restspring.domain.model.Produccion;
import org.example.restspring.ui.config.Constantes;
import org.example.restspring.ui.dto.ProduccionDTO;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
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
        if(produccion.isEmpty()) throw new NotFoundException(Constantes.USUARIO_CON_ID + userId + Constantes.NO_TIENE_PRODUCCIONES);
        produccion.forEach(p -> produccionDTOS.add(new ProduccionDTO(p.titulo(), p.director(), p.genero())));
        return produccionDTOS;
    }

    public ProduccionDTO getById(int id){
        Produccion produccion = produccionRepository.getById(id);
        if(produccion != null) return new ProduccionDTO(produccion.titulo(), produccion.director(), produccion.genero());
        throw new NotFoundException(Constantes.PRODUCCION_CON_ID + id + Constantes.NO_ENCONTRADA);
    }

    public ProduccionDTO getByName(String name){
        Produccion produccion = produccionRepository.getByName(name);
        if(produccion != null) return new ProduccionDTO(produccion.titulo(), produccion.director(), produccion.genero());
        throw new NotFoundException(Constantes.NO_SE_HA_ENCONTRAD_NOMBRE + name);
    }

    public ProduccionDTO save(Produccion produccion){
        Produccion produccion1 = produccionRepository.save(produccion);
        return new ProduccionDTO(produccion1.titulo(), produccion1.director(), produccion1.genero());
    }

    public ProduccionDTO update(int id, ProduccionDTO produccionDTO){
        Produccion produccionAntes = produccionRepository.getById(id);
        if(produccionAntes != null){
            Produccion produccion1 = produccionRepository.update(id, new Produccion(id, produccionDTO.titulo(), produccionAntes.anio(), produccionDTO.director(), produccionDTO.genero(), produccionAntes.userId()));
            return new ProduccionDTO(produccion1.titulo(), produccion1.director(), produccion1.genero());
        }
        throw new NotFoundException(Constantes.PRODUCCION_CON_ID + id + Constantes.NO_ENCONTRADA);
    }

    public void delete(int id, int userId){

        if(!produccionRepository.delete(id, userId)) throw new NotFoundException(Constantes.NO_SE_HA_PODIDO_ELIMINAR_PRODUCCION + id + Constantes.PUEDE_QUE_NO_EXISTA + userId);
    }
}
