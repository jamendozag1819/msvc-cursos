package com.minsait.jamendozag.springcloud.msvc.cursos.services;

import com.minsait.jamendozag.springcloud.msvc.cursos.clients.UsuarioFeing;
import com.minsait.jamendozag.springcloud.msvc.cursos.models.Usuario;
import com.minsait.jamendozag.springcloud.msvc.cursos.models.entity.Curso;
import com.minsait.jamendozag.springcloud.msvc.cursos.models.entity.CursoUsuario;
import com.minsait.jamendozag.springcloud.msvc.cursos.repositores.CursoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CursoServiceImpl implements ICursosServices {

    private final CursoRepository cursoRepository;

    private final UsuarioFeing usuarioFeing;

    public CursoServiceImpl(CursoRepository cursoRepository, UsuarioFeing usuarioFeing) {
        this.cursoRepository = cursoRepository;
        this.usuarioFeing = usuarioFeing;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Curso> listar() {
        List<Curso> listaCurso = cursoRepository.listar();
        listaCurso.forEach(curso -> {
            if (!curso.getCursoUsuarios().isEmpty())
                curso.setUsuarios(usuarioFeing.buscarPorListaDeIds(
                        curso.getCursoUsuarios().stream().map(CursoUsuario::getUsuarioId).toList()
                ));
        });
        return listaCurso;
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Curso> detalle(Long id) {
        Optional<Curso> o = cursoRepository.findById(id);
        if (o.isPresent()) {
            Curso curso = o.get();
            if (!curso.getCursoUsuarios().isEmpty())
                curso.setUsuarios(usuarioFeing.buscarPorListaDeIds(
                        curso.getCursoUsuarios().stream().map(CursoUsuario::getUsuarioId).toList()
                ));
        }
        return o;
    }

    @Transactional
    @Override
    public Curso guardar(Curso curso) {
        return cursoRepository.save(curso);
    }

    @Transactional
    @Override
    public void eliminar(Long id) {
        cursoRepository.deleteById(id);
    }

    @Transactional
    @Override
    public Optional<Usuario> asignarUsuario(Usuario usuario, Long cursoId) {
        Optional<Curso> o = cursoRepository.findById(cursoId);
        if (o.isPresent()) {
            Usuario usuarioDb = usuarioFeing.findById(usuario.getId());
            CursoUsuario cursoUsuario = new CursoUsuario();
            cursoUsuario.setUsuarioId(usuarioDb.getId());
            Curso curso = o.get();
            curso.agregarCursoUsuario(cursoUsuario);
            cursoRepository.save(curso);
            return Optional.of(usuarioDb);
        }
        return Optional.empty();
    }

    @Transactional
    @Override
    public Optional<Usuario> crearUsuario(Usuario usuario, Long cursoId) {

        Optional<Curso> cursoOptional = cursoRepository.findById(cursoId);
        if (cursoOptional.isPresent()) {
            Curso curso = cursoOptional.get();
            Usuario usuarioMsvc = usuarioFeing.save(usuario);
            CursoUsuario cursoUsuario = new CursoUsuario();
            cursoUsuario.setUsuarioId(usuarioMsvc.getId());
            curso.agregarCursoUsuario(cursoUsuario);
            cursoRepository.save(curso);
            return Optional.of(usuarioMsvc);
        }
        return Optional.empty();
    }

    @Transactional
    @Override
    public Optional<Usuario> eliminarUsuario(Usuario usuario, Long cursoId) {

        Optional<Curso> cursoOptional = cursoRepository.findById(cursoId);
        if (cursoOptional.isPresent()) {
            Curso curso = cursoOptional.get();
            Usuario usuarioMsvc = usuarioFeing.findById(usuario.getId());

            CursoUsuario cursoUsuario = new CursoUsuario();
            cursoUsuario.setUsuarioId(usuarioMsvc.getId());
            curso.removerCursoUsuario(cursoUsuario);

            cursoRepository.save(curso);
            return Optional.of(usuarioMsvc);
        }
        return Optional.empty();
    }

    @Transactional
    @Override
    public void eliminarCursoUsuarioPorUsuario(Long usuarioId) {
        cursoRepository.eliminarCursoUsuarioPorUsuario(usuarioId);
    }
}