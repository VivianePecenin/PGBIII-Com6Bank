package com.pgbiii.com6bank.Controller;

import com.pgbiii.com6bank.Models.Usuario;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ExtratoController {

    @PostMapping("/gerarExtrato")
    @ResponseBody
    public ModelAndView gerarExtrato(@RequestBody Usuario dados) {
        String cpf = dados.getCpf();
        String password = cpf.substring(0, 5);


        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("url", "http://localhost:8080/extrato.pdf");
        modelAndView.addObject("password", password);
        modelAndView.addObject("availableDownloads", 2);
        modelAndView.setViewName("extratoResult");

        return modelAndView;
    }
}
