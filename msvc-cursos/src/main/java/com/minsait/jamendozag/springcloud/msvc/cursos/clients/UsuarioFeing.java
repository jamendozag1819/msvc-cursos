package com.minsait.jamendozag.springcloud.msvc.cursos.clients;

import com.minsait.jamendozag.springcloud.msvc.cursos.models.Usuario;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "msvc-usuarios")
public interface UsuarioFeing {

    @GetMapping("/")
    List<Usuario> findAll();

    @GetMapping("/{id}")
    Usuario findById(@PathVariable Long id);

    @GetMapping(value = "/usuarios-por-curso")
    List<Usuario> buscarPorListaDeIds(@RequestParam Iterable<Long> Ids);

    @PostMapping("/")
    Usuario save(@RequestBody Usuario usuario);

}
