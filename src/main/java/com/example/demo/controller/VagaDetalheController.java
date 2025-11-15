package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.example.demo.model.Vaga;
import com.example.demo.repository.VagaRepository;

@Controller
@RequestMapping("/vaga")
public class VagaDetalheController {

    @Autowired
    private VagaRepository vagaRepository;

    @GetMapping("/{id}")
    public String detalheVaga(@PathVariable Long id, Model model) {
        Vaga vaga = vagaRepository.findById(id).orElse(null);
        
        if (vaga == null) {
            return "redirect:/?erro=Vaga+não+encontrada";
        }
        
        model.addAttribute("vaga", vaga);
        return "detalhe-vaga";
    }
}