package com.pgbiii.com6bank.Controller;

import com.pgbiii.com6bank.Config.JwtTokenUtil;
import com.pgbiii.com6bank.Models.Carteira;
import com.pgbiii.com6bank.Models.JwtRequest;
import com.pgbiii.com6bank.Models.JwtResponse;
import com.pgbiii.com6bank.Models.Usuario;
import com.pgbiii.com6bank.Repository.CarteiraRepository;
import com.pgbiii.com6bank.Service.JwtUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class JwtAuthenticateController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtUserDetailsService userDetailsService;

    @Autowired
    private CarteiraRepository carteiraRepository;

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("Usuario desabilitado", e);
        } catch (BadCredentialsException e) {
            throw new Exception("Credenciais invalidas", e);
        } catch (Exception e) {
            throw new Exception("Erro ao autenticar", e);
        }
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<?> SaveUser(@RequestBody Usuario user) throws Exception {

        Carteira carteira = new Carteira();
        carteira.setSaldo(0.00);
        carteiraRepository.save(carteira);

        user.setCarteira(carteira);

        return ResponseEntity.ok(userDetailsService.save(user));
    }

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest request) throws Exception {
        authenticate(request.getUsername(), request.getPassword());
        final UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
        final String token = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(token));
    }
}
