package com.pgbiii.com6bank.Models;


import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;

@Entity
@Table(name = "lancamentos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@EntityListeners(AuditingEntityListener.class)
public class Lancamentos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "descricao", nullable = false, length = 100)
    private String descricao;

    @Column(name = "valor", nullable = false, length = 100)
    private Double valor;

    @Column(name = "data",nullable = false)
    private Date data;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", nullable = false, length = 100)
    private TipoLancamento tipo;

    @ManyToOne()
    @JoinColumn(name = "usuario")
    private Usuario usuario;

}
