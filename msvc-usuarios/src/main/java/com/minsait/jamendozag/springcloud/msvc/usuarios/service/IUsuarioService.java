package com.minsait.jamendozag.springcloud.msvc.usuarios.service;

import com.minsait.jamendozag.springcloud.msvc.usuarios.models.entity.Usuario;

import java.util.List;
import java.util.Optional;

public interface IUsuarioService {

    List<Usuario> findAll();

    Optional<Usuario> findById(Long id);

    List<Usuario> buscarListaIds(Iterable<Long> Ids);

    void deleteById(Long id);

    Usuario save(Usuario usuario);

    Optional<Usuario> findByEmail(String email);
}
