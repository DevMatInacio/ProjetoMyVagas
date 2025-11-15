package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.example.demo.model.Candidatura;
import com.example.demo.repository.CandidaturaRepository;

@Controller
@RequestMapping("/empresa/candidatura")
public class CandidaturaStatusController {

    @Autowired
    private CandidaturaRepository candidaturaRepository;

    @PostMapping("/{candidaturaId}/selecionar")
    public String selecionarCandidato(@PathVariable Long candidaturaId, 
                                     @RequestParam Long vagaId) {
        Candidatura candidatura = candidaturaRepository.findById(candidaturaId).orElse(null);
        
        if (candidatura != null) {
            candidatura.setStatus("SELECIONADO");
            candidaturaRepository.save(candidatura);
        }
        
        return "redirect:/empresa/vaga/" + vagaId + "/candidaturas?msg=Candidato+selecionado+com+sucesso";
    }

    @PostMapping("/{candidaturaId}/rejeitar")
    public String rejeitarCandidato(@PathVariable Long candidaturaId,
                                   @RequestParam Long vagaId) {
        Candidatura candidatura = candidaturaRepository.findById(candidaturaId).orElse(null);
        
        if (candidatura != null) {
            candidatura.setStatus("REJEITADO");
            candidaturaRepository.save(candidatura);
        }
        
        return "redirect:/empresa/vaga/" + vagaId + "/candidaturas?msg=Candidato+rejeitado";
    }
}