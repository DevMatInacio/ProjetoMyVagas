package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;
import com.example.demo.model.*;
import com.example.demo.repository.*;
import java.util.List;

@Controller
@RequestMapping("/candidato")
public class DashboardCandidatoController {

    @Autowired
    private VagaRepository vagaRepository;
    
    @Autowired
    private CandidaturaRepository candidaturaRepository;

    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {

        Object usuario = session.getAttribute("usuarioLogado");
        if (usuario == null || !(usuario instanceof Candidato)) {
            return "redirect:/login?redirect=/candidato/dashboard";
        }

        Candidato candidato = (Candidato) usuario;

        // Busca todas as vagas disponíveis
        List<Vaga> vagasDisponiveis = vagaRepository.findAll();
        
        // Busca as candidaturas deste candidato
        List<Candidatura> minhasCandidaturas = candidaturaRepository.findByCandidatoId(candidato.getId());

        model.addAttribute("candidato", candidato);
        model.addAttribute("vagasDisponiveis", vagasDisponiveis);
        model.addAttribute("minhasCandidaturas", minhasCandidaturas);

        return "dashboard-candidato";
    }

    @PostMapping("/candidatar")
    public String candidatar(@RequestParam Long vagaId, HttpSession session) {
        Object usuario = session.getAttribute("usuarioLogado");
        if (usuario == null || !(usuario instanceof Candidato)) {
            return "redirect:/login";
        }

        Candidato candidato = (Candidato) usuario;

        // Busca a vaga pelo ID
        Vaga vaga = vagaRepository.findById(vagaId).orElse(null);

        if (vaga != null) {
            // Verifica se já não se candidatou
            boolean jaCandidatou = candidaturaRepository.existsByCandidatoIdAndVagaId(candidato.getId(), vagaId);

            if (!jaCandidatou) {
                // Cria nova candidatura
                Candidatura candidatura = new Candidatura(candidato, vaga, java.time.LocalDate.now());
                candidaturaRepository.save(candidatura);
            }
        }

        return "redirect:/candidato/dashboard?msg=Candidatura realizada com sucesso!";
    }
}