package com.pgbiii.com6bank.Controller;

import com.pgbiii.com6bank.Models.Carteira;
import com.pgbiii.com6bank.Models.Lancamentos;
import com.pgbiii.com6bank.Models.TipoLancamento;
import com.pgbiii.com6bank.Repository.CarteiraRepository;
import com.pgbiii.com6bank.Repository.LancamentoRepository;
import com.pgbiii.com6bank.Repository.UserRepository;
import com.pgbiii.com6bank.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class LancamentosController {

    @Autowired
    private LancamentoRepository repository;

    @Autowired
    private CarteiraRepository carteiraRepository;

    @Autowired
    private UserRepository repositoryUser;

    @GetMapping("/lancamentos")
    public List<Lancamentos> getAllUsers(){
        return repository.findAll();
    }

    @GetMapping("/lancamentos/{id}")
    public ResponseEntity<Lancamentos> getById(@PathVariable(value = "id") long lancamentosId) throws ResourceNotFoundException {

        Lancamentos lancamentos = repository.findById(lancamentosId).orElseThrow(() ->
                new ResourceNotFoundException("Lancamentos não encontrado: " + lancamentosId));
        return ResponseEntity.ok().body(lancamentos);

    }
    @PostMapping("/lancamentos/depositar")
    public Lancamentos createLancaentosdepositar(@Validated @RequestBody Lancamentos lancamentos, Authentication authentication) {
        lancamentos.setTipo(TipoLancamento.DEPOSITO);

        String user = authentication.getName();
        Carteira carteira = repositoryUser.findByEmail(user).getCarteira();

        carteira.setSaldo(carteira.getSaldo() + lancamentos.getValor());
        carteiraRepository.save(carteira);
        lancamentos.setData(new Date());
        lancamentos.setUsuario(carteira.getUsuario());
        return repository.save(lancamentos);

    }


    @PostMapping("/lancamentos/transferir/{usuariodestinoId}")
    public Lancamentos createLancaentostransferir(@PathVariable (value = "usuariodestinoId") Long usuariodestinoId, @Validated @RequestBody Lancamentos lancamentos, Authentication authentication) {
        lancamentos.setTipo(TipoLancamento.TRANSFERENCIA);

        String user = authentication.getName();

        Carteira carteira = repositoryUser.findByEmail(user).getCarteira();

        Carteira carteiraDestino = repositoryUser.findById(usuariodestinoId).get().getCarteira();

        if(carteira.getSaldo() < lancamentos.getValor()){
            throw new RuntimeException("Saldo insuficiente");
        }

        carteira.setSaldo(carteira.getSaldo() - lancamentos.getValor());
        carteiraDestino.setSaldo(carteiraDestino.getSaldo() + lancamentos.getValor());

        carteiraRepository.save(carteira);
        carteiraRepository.save(carteiraDestino);
        lancamentos.setData(new Date());
        lancamentos.setUsuario(carteira.getUsuario());
        return repository.save(lancamentos);

    }

    @PostMapping("/lancamentos/sacar")
    public Lancamentos createLancaentossacar(@Validated @RequestBody Lancamentos lancamentos, Authentication authentication) {
        lancamentos.setTipo(TipoLancamento.RETIRADA);

        String user = authentication.getName();
        Carteira carteira = repositoryUser.findByEmail(user).getCarteira();

        if(carteira.getSaldo() < lancamentos.getValor()){
           throw new RuntimeException("Saldo insuficiente");
        }

        carteira.setSaldo(carteira.getSaldo() - lancamentos.getValor());
        carteiraRepository.save(carteira);

        lancamentos.setData(new Date());
        lancamentos.setUsuario(carteira.getUsuario());
        return repository.save(lancamentos);

    }


}
