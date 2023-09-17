package com.pgbiii.com6bank.Service;

import com.pgbiii.com6bank.Models.Carteira;
import com.pgbiii.com6bank.Models.Usuario;
import com.pgbiii.com6bank.Repository.CarteiraRepository;
import com.pgbiii.com6bank.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private CarteiraRepository carteiraRepository;

    @Autowired
    private PasswordEncoder bcryptEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Usuario user = repository.findByUsername(username);

        Usuario user2;

        if (user == null) {

            user2 = repository.findByEmail(username);
        } else {
            return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getSenha(), new ArrayList<>());
        }

        if (user2 == null) {
            throw new UsernameNotFoundException("Usuario não encontrado!");
        }
        return new org.springframework.security.core.userdetails.User(user2.getEmail(), user2.getSenha(), new ArrayList<>());
    }

    public Usuario save(Usuario user) {
        Carteira carteira = new Carteira();
        carteira.setSaldo(0.00);
        carteiraRepository.save(carteira);

        Usuario novo = new Usuario();
        novo.setUsername(user.getUsername());
        novo.setCpf(user.getCpf());
        novo.setEmail(user.getEmail());
        novo.setCarteira(carteira);
        novo.setSenha(bcryptEncoder.encode(user.setSenhaFromCpf(user.getCpf())));
        return repository.save(novo);
    }
}
