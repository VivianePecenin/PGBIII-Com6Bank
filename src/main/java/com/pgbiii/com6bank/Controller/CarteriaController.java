package com.pgbiii.com6bank.Controller;

import com.pgbiii.com6bank.Models.Carteira;
import com.pgbiii.com6bank.Repository.CarteiraRepository;
import com.pgbiii.com6bank.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/v1")
public class CarteriaController {

    @Autowired
    private CarteiraRepository repository;


    @GetMapping("/carteiras")
    public List<Carteira> getAllUsers(){
        return repository.findAll();
    }

    @GetMapping("/carteira/{id}")
    public ResponseEntity<Carteira> getById(@PathVariable(value = "id") long carteiraId) throws ResourceNotFoundException {

        Carteira carteira = repository.findById(carteiraId).orElseThrow(() ->
                new ResourceNotFoundException("Carteira não encontrado: " + carteiraId));
        return ResponseEntity.ok().body(carteira);

    }
    @PostMapping("/carteira/criar")
    public Carteira createCarteira(@Validated @RequestBody Carteira carteira) {

        return repository.save(carteira);
    }

    @PutMapping("/carteira/modificar/{id}")
    public ResponseEntity<Carteira> updateCarteira(@PathVariable(value = "id") Long carteiraId,
                                                   @Validated @RequestBody Carteira carteira) throws  ResourceNotFoundException{
        Carteira carteira1 = repository.findById(carteiraId).orElseThrow(()-> new ResourceNotFoundException("Carteira não encontrado: " + carteiraId));
        carteira1.setUsuario(carteira.getUsuario());
        carteira1.setSaldo(carteira.getSaldo());
        return ResponseEntity.ok(repository.save(carteira1));
    }

    @DeleteMapping("/carteira/deletar/{id}")
    public Map<String, Boolean> deleteCarteira(@PathVariable(value = "id") Long carteiraId) throws Exception{
        Carteira carteira = repository.findById(carteiraId).orElseThrow(()-> new ResourceNotFoundException("Carteira não encontrado: " + carteiraId));
        repository.delete(carteira);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
}
