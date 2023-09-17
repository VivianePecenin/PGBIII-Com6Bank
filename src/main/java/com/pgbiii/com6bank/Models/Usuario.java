package com.pgbiii.com6bank.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Set;

@Entity
@Table(name = "usuario")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@EntityListeners(AuditingEntityListener.class)
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", nullable = false, length = 100)
    private String username;


    @Column(name = "email", nullable = false, length = 100, unique = true)
    private String email;

    @Column(name = "cpf", nullable = false, length = 11)
    private String cpf;

    @Column(name = "senha", length = 100)
    private String senha;

    public String setSenhaFromCpf(String cpf) {
        return this.senha = cpf.substring(0, 5);
    }

    @OneToOne()
    @JoinColumn(name = "carteira")
    private Carteira carteira;

    @OneToMany(
            mappedBy     = "usuario",
            targetEntity = Lancamentos.class,
            fetch        = FetchType.LAZY,
            cascade      = CascadeType.ALL)

    @JsonIgnore
    private Set<Lancamentos> lancamentos;



}
