package com.pgbiii.com6bank.Repository;

import com.pgbiii.com6bank.Models.Lancamentos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LancamentoRepository extends JpaRepository<Lancamentos, Long> {
}
