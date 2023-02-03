package com.minsait.jamendozag.springcloud.msvc.usuarios.repositories;

import com.minsait.jamendozag.springcloud.msvc.usuarios.models.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByEmail(String email);

    @Query("select u from Usuario u where u.id in ?1")
    List<Usuario> findById(List<Long> Ids);
}
