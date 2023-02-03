package com.minsait.jamendozag.springcloud.msvc.cursos.controller;

import com.minsait.jamendozag.springcloud.msvc.cursos.models.Usuario;
import com.minsait.jamendozag.springcloud.msvc.cursos.models.entity.Curso;
import com.minsait.jamendozag.springcloud.msvc.cursos.services.ICursosServices;
import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@Log
@RestController
public class CursoController {

    private final ICursosServices cursosServices;

    public CursoController(ICursosServices cursosServices) {
        this.cursosServices = cursosServices;
    }

    @GetMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public List<Curso> findAll() {
        List<Curso> cursos = cursosServices.listar();
        return cursos;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscar(@PathVariable Long id) {
        Optional<Curso> o = cursosServices.detalle(id);
        if (o.isPresent()) {
            return ResponseEntity.ok(o.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/")
    public ResponseEntity<?> crear(@Valid @RequestBody Curso curso, BindingResult result) {
        Map<String, Object> respuesta = new HashMap<>();

        ResponseEntity<Map<String, Object>> respuesta1 = validar(result, respuesta);
        if (respuesta1 != null) return respuesta1;
        return ResponseEntity.status(HttpStatus.CREATED).body(cursosServices.guardar(curso));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@Valid @RequestBody Curso curso, BindingResult result, @PathVariable Long id) {
        Map<String, Object> respuesta = new HashMap<>();

        ResponseEntity<Map<String, Object>> respuesta1 = validar(result, respuesta);
        if (respuesta1 != null) return respuesta1;

        Optional<Curso> o = cursosServices.detalle(id);
        if (o.isPresent()) {
            Curso cursoDb = o.get();
            cursoDb.setNombre(curso.getNombre());
            return ResponseEntity.status(HttpStatus.CREATED).body(cursosServices.guardar(cursoDb));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        cursosServices.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    private ResponseEntity<Map<String, Object>> validar(BindingResult result, Map<String, Object> respuesta) {
        if (result.hasErrors()) {
            result.getFieldErrors().forEach(err -> {
                respuesta.put(err.getField(), "El campo " + err.getField() + " " + err.getDefaultMessage());
            });
            return ResponseEntity.badRequest().body(respuesta);
        }
        return null;
    }

    @PutMapping("/asignar-usuario/{cursoId}")
    public ResponseEntity<?> asignarUsuario(@RequestBody Usuario usuario, @PathVariable Long cursoId) {
        Optional<Usuario> user;
        try {
            user = cursosServices.asignarUsuario(usuario, cursoId);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("mensaje", "No existe el usuario por el id o error en la comunicación: " + ex.getMessage()));
        }
        if (user.isPresent()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(user);
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/crear-usuario/{cursoId}")
    public ResponseEntity<?> crearUsuario(@RequestBody Usuario usuario, @PathVariable Long cursoId) {
        Optional<Usuario> user;
        try {
            user = cursosServices.crearUsuario(usuario, cursoId);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("mensaje", "No existe el usuario por " +
                            "el id o error en la comunicación: " + ex.getMessage()));
        }
        if (user.isPresent()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(user);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/eliminar-usuario/{cursoId}")
    public ResponseEntity<?> eliminarUsuario(@RequestBody Usuario usuario, @PathVariable Long cursoId) {
        Optional<Usuario> user;
        try {
            user = cursosServices.eliminarUsuario(usuario, cursoId);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("mensaje", "No existe el usuario por " +
                            "el id o error en la comunicación: " + ex.getMessage()));
        }
        if (user.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(user);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/eliminar-usuario-cursousuario/{usuarioId}")
    public ResponseEntity<?> eliminarUsuario(@PathVariable Long usuarioId) {
        cursosServices.eliminarCursoUsuarioPorUsuario(usuarioId);
        return ResponseEntity.ok().build();
    }
}
