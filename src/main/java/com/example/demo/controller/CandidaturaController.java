package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.example.demo.model.*;
import com.example.demo.repository.*;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/candidaturas")
public class CandidaturaController {

    @Autowired
    private CandidaturaRepository candidaturaRepository;
    
    @Autowired
    private CandidatoRepository candidatoRepository;
    
    @Autowired
    private VagaRepository vagaRepository;

    @GetMapping
    public String listarCandidaturas(Model model, @RequestParam(required = false) String msg) {
        List<Candidatura> candidaturas = candidaturaRepository.findAll();
        List<Candidato> candidatos = candidatoRepository.findAll();
        List<Vaga> vagas = vagaRepository.findAll();

        model.addAttribute("candidaturas", candidaturas);
        model.addAttribute("candidatos", candidatos);
        model.addAttribute("vagas", vagas);
        model.addAttribute("msg", msg);

        return "candidatura";
    }

    @PostMapping
    public String candidatar(@RequestParam Long candidatoId, @RequestParam Long vagaId) {

        Candidato candidato = candidatoRepository.findById(candidatoId).orElse(null);
        Vaga vaga = vagaRepository.findById(vagaId).orElse(null);

        if (candidato != null && vaga != null) {
            boolean jaCandidatou = candidaturaRepository.existsByCandidatoIdAndVagaId(candidatoId, vagaId);

            if (!jaCandidatou) {
                Candidatura candidatura = new Candidatura(candidato, vaga, LocalDate.now());
                candidaturaRepository.save(candidatura);
                candidato.candidatar(vaga);
                return "redirect:/candidaturas?msg=Candidatura realizada com sucesso!";
            } else {
                return "redirect:/candidaturas?msg=Você já se candidatou a esta vaga!";
            }
        }

        return "redirect:/candidaturas?msg=Erro ao realizar candidatura!";
    }

    @GetMapping("/deletar/{id}")
    public String deletar(@PathVariable Long id) {
        candidaturaRepository.deleteById(id);
        return "redirect:/candidaturas?msg=Candidatura removida com sucesso!";
    }

    @GetMapping("/por-vaga/{vagaId}")
    public String candidaturasPorVaga(@PathVariable Long vagaId, Model model) {
        List<Candidatura> candidaturasVaga = candidaturaRepository.findByVagaId(vagaId);
        Vaga vagaSelecionada = vagaRepository.findById(vagaId).orElse(null);

        model.addAttribute("candidaturas", candidaturasVaga);
        model.addAttribute("vaga", vagaSelecionada);
        return "candidatura-vaga";
    }
}