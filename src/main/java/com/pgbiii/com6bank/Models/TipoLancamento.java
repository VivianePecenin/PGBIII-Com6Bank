package com.pgbiii.com6bank.Models;

import com.pgbiii.com6bank.Repository.CarteiraRepository;
import com.pgbiii.com6bank.Repository.UserRepository;

import org.springframework.security.core.Authentication;


public enum TipoLancamento {


    TRANSFERENCIA,
    DEPOSITO,

    RETIRADA

}
