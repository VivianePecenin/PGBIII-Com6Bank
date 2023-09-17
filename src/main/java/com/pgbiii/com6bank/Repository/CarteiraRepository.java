package com.pgbiii.com6bank.Repository;


import com.pgbiii.com6bank.Models.Carteira;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarteiraRepository extends JpaRepository<Carteira, Long> {
}
