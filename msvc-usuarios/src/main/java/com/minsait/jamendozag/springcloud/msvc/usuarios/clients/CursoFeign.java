package com.minsait.jamendozag.springcloud.msvc.usuarios.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "msvc-cursos")
public interface CursoFeign {

    @DeleteMapping("/eliminar-usuario-cursousuario/{usuarioId}")
    ResponseEntity<?> eliminarUsuario(@PathVariable Long usuarioId);

}
