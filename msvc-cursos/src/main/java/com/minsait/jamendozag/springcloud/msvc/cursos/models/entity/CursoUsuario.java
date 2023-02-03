package com.minsait.jamendozag.springcloud.msvc.cursos.models.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "cursosUsuarios", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"usuario_id", "curso_id"})
})
public class CursoUsuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "usuario_id")
    private Long usuarioId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CursoUsuario that)) return false;

        if (Objects.equals(usuarioId, that.usuarioId)) return true;
        return false;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }
}
