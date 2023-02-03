package com.minsait.jamendozag.springcloud.msvc.cursos.repositores;

import com.minsait.jamendozag.springcloud.msvc.cursos.models.entity.Curso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CursoRepository extends JpaRepository<Curso, Long> {

    @Query("select c from Curso c")
    List<Curso> listar();

    @Modifying
    @Query("delete CursoUsuario cu where cu.usuarioId=?1")
    void eliminarCursoUsuarioPorUsuario(Long id);

}
