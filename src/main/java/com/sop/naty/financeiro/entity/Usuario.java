package com.sop.naty.financeiro.entity;

import com.sop.naty.financeiro.enumeration.RoleUsuario;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Entity
@Table(name = "usuario")
@Data // do Lombok (getters, setters, toString)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String senha; // BCrypt

    @Column
    private RoleUsuario role;

}

