package com.minsait.jamendozag.springcloud.msvc.cursos.services;

import com.minsait.jamendozag.springcloud.msvc.cursos.models.Usuario;
import com.minsait.jamendozag.springcloud.msvc.cursos.models.entity.Curso;

import java.util.List;
import java.util.Optional;

public interface ICursosServices {

    List<Curso> listar();

    Optional<Curso> detalle(Long id);

    Curso guardar(Curso curso);

    void eliminar(Long id);

    Optional<Usuario> asignarUsuario(Usuario usuario, Long cursoId);

    Optional<Usuario> crearUsuario(Usuario usuario, Long cursoId);

    Optional<Usuario> eliminarUsuario(Usuario usuario, Long cursoId);

    void eliminarCursoUsuarioPorUsuario(Long usuarioId);
}
