package com.minsait.jamendozag.springcloud.msvc.usuarios.controller;

import com.minsait.jamendozag.springcloud.msvc.usuarios.models.entity.Usuario;
import com.minsait.jamendozag.springcloud.msvc.usuarios.service.IUsuarioService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@Log
@RestController
public class UsuarioController {

    @Autowired
    private IUsuarioService usuarioService;

    @GetMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public Map<String, List<Usuario>> findAll() {
        return Collections.singletonMap("users", usuarioService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        Optional<Usuario> op = usuarioService.findById(id);
        if (op.isPresent()) {
            return ResponseEntity.ok(op.get());
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping(value = "/usuarios-por-curso")
    public List<Usuario> buscarPorListaDeIds(@RequestParam("Ids") List<Long> Ids) {
        for (Long id : Ids) {
            log.info(String.valueOf(id));
        }
        return usuarioService.buscarListaIds(Ids);
    }

    @PostMapping("/")
    public ResponseEntity<?> create(@Valid @RequestBody Usuario user, BindingResult result) {
        Map<String, Object> respuesta = new HashMap<>();
        if (user != null && usuarioService.findByEmail(user.getEmail()).isPresent()) {
            respuesta.put("email", "Este email ya existe!!");
            return ResponseEntity.badRequest().body(respuesta);
        }

        ResponseEntity<Map<String, Object>> respuesta1 = validar(result, respuesta);
        if (respuesta1 != null) return respuesta1;
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.save(user));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody Usuario user, BindingResult result, @PathVariable Long id) {
        Map<String, Object> respuesta = new HashMap<>();
        Optional<Usuario> o = usuarioService.findByEmail(user.getEmail());
        if (user != null && o.isPresent() && o.get().getId() != user.getId()) {
            respuesta.put("email", "Este correo ya existe!");
            return ResponseEntity.badRequest().body(respuesta);
        }
        ResponseEntity<Map<String, Object>> respuesta1 = validar(result, respuesta);

        if (respuesta1 != null) return respuesta1;
        Optional<Usuario> op = usuarioService.findById(id);
        if (op.isPresent()) {
            Usuario userDb = op.get();
            userDb.setNombre(user.getNombre());
            userDb.setEmail(user.getEmail());
            userDb.setPassword(user.getPassword());
            return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.save(userDb));

        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        usuarioService.deleteById(id);
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

}
