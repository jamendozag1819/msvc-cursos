package com.minsait.jamendozag.springcloud.msvc.usuarios.service;

import com.minsait.jamendozag.springcloud.msvc.usuarios.clients.CursoFeign;
import com.minsait.jamendozag.springcloud.msvc.usuarios.models.entity.Usuario;
import com.minsait.jamendozag.springcloud.msvc.usuarios.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioServiceImpl implements IUsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CursoFeign cursoFeign;

    @Transactional(readOnly = true)
    @Override
    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Usuario> findById(Long id) {
        return usuarioRepository.findById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Usuario> buscarListaIds(Iterable<Long> Ids) {
        return usuarioRepository.findAllById(Ids);
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        cursoFeign.eliminarUsuario(id);
        usuarioRepository.deleteById(id);
    }

    @Transactional
    @Override
    public Usuario save(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Usuario> findByEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }
}
