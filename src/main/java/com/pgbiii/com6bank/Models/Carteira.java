package com.pgbiii.com6bank.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Set;

@Entity
@Table(name = "carteira")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@EntityListeners(AuditingEntityListener.class)
public class Carteira {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "saldo", nullable = false, length = 100)
    private Double saldo;

    @OneToOne(
            mappedBy     = "carteira",
            targetEntity = Usuario.class,
            fetch        = FetchType.LAZY,
            cascade      = CascadeType.ALL)

    @JsonIgnore
    private Usuario usuario;
}
