package org.example.restspring.ui.service;

import org.example.restspring.data.UsuarioRepository;
import org.example.restspring.domain.model.Usuario;
import org.example.restspring.ui.dto.UsuarioDTO;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public List<UsuarioDTO> getAll(){
        List<Usuario> usuarios = usuarioRepository.getAll();
        List<UsuarioDTO> usuariosDTO = new ArrayList<>();
        usuarios.forEach(u ->
                usuariosDTO.add(
                        new UsuarioDTO(
                                u.username(), u.password()
                        )));
        return usuariosDTO;
    }

    public UsuarioDTO getById(int id){
        Usuario usuario = usuarioRepository.getById(id);

        return new UsuarioDTO(usuario.username(), usuario.password());
    }

    public UsuarioDTO getByName(String name){
        Usuario usuario = usuarioRepository.getByName(name);
        if (usuario == null) {
            return null;
        }
        return new UsuarioDTO(usuario.username(), usuario.password());
    }



}
