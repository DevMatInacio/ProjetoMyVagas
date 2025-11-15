package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.example.demo.model.Candidato;
import com.example.demo.repository.CandidatoRepository;
import java.util.List;

@Controller
@RequestMapping("/candidatos")
public class CandidatoController {

    @Autowired
    private CandidatoRepository candidatoRepository;

    @GetMapping
    public String listarCandidatos(Model model, @RequestParam(required = false) String msg) {
        List<Candidato> candidatos = candidatoRepository.findAll();
        model.addAttribute("candidatos", candidatos);
        model.addAttribute("msg", msg);
        return "candidato";
    }

    @PostMapping
    public String salvar(@RequestParam String nome,
                        @RequestParam String email,
                        @RequestParam String senha,
                        @RequestParam String curriculo,
                        @RequestParam String telefone,
                        @RequestParam String experiencia) {
        
        Candidato candidato = new Candidato(nome, email, senha, curriculo, telefone, experiencia);
        candidatoRepository.save(candidato);
        return "redirect:/candidatos?msg=Candidato cadastrado com sucesso!";
    }

    @GetMapping("/deletar/{id}")
    public String deletar(@PathVariable Long id) {
        candidatoRepository.deleteById(id);
        return "redirect:/candidatos?msg=Candidato removido com sucesso!";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        Candidato candidato = candidatoRepository.findById(id).orElse(null);
        model.addAttribute("candidato", candidato);
        return "candidato";
    }
}