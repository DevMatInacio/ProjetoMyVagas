package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;
import com.example.demo.model.*;
import com.example.demo.repository.*;
import java.util.List;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/empresa")
public class DashboardEmpresaController {

    @Autowired
    private VagaRepository vagaRepository;
    
    @Autowired
    private EmpresaRepository empresaRepository;
    
    @Autowired
    private CandidaturaRepository candidaturaRepository;

    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        
        Object usuario = session.getAttribute("usuarioLogado");
        if (usuario == null || !(usuario instanceof Empresa)) {
            return "redirect:/login?redirect=/empresa/dashboard";
        }
        
        Empresa empresa = (Empresa) usuario;
        
        // Buscar empresa atualizada do banco
        Empresa empresaManaged = empresaRepository.findById(empresa.getId())
            .orElseThrow(() -> new RuntimeException("Empresa não encontrada"));
        
        // Busca vagas desta empresa
        List<Vaga> vagasEmpresa = vagaRepository.findByEmpresaId(empresaManaged.getId());
        
        // Busca candidaturas para as vagas desta empresa
        List<Candidatura> candidaturas = candidaturaRepository.findByVagaEmpresaId(empresaManaged.getId());
        
        model.addAttribute("empresa", empresaManaged);
        model.addAttribute("vagasEmpresa", vagasEmpresa);
        model.addAttribute("candidaturas", candidaturas);
        model.addAttribute("totalCandidaturas", candidaturas.size());
        
        return "dashboard-empresa";
    }

    @PostMapping("/publicar-vaga")
    public String publicarVaga(@RequestParam String titulo,
                              @RequestParam String descricao,
                              @RequestParam String local,
                              HttpSession session,
                              RedirectAttributes redirectAttributes) {
        
        try {
            Object usuario = session.getAttribute("usuarioLogado");
            if (usuario == null || !(usuario instanceof Empresa)) {
                return "redirect:/login";
            }
            
            Empresa empresa = (Empresa) usuario;
            
            // Buscar empresa do banco para garantir que é uma entidade managed
            Empresa empresaManaged = empresaRepository.findById(empresa.getId())
                .orElseThrow(() -> new RuntimeException("Empresa não encontrada"));
            
            // Validar dados
            if (titulo == null || titulo.trim().isEmpty()) {
                redirectAttributes.addFlashAttribute("erro", "Título é obrigatório");
                return "redirect:/empresa/dashboard";
            }
            
            // Criar e salvar vaga
            Vaga novaVaga = new Vaga();
            novaVaga.setTitulo(titulo.trim());
            novaVaga.setDescricao(descricao != null ? descricao.trim() : "");
            novaVaga.setLocal(local != null ? local.trim() : "");
            novaVaga.setEmpresa(empresaManaged);
            
            vagaRepository.save(novaVaga);
            
            redirectAttributes.addFlashAttribute("msg", "Vaga publicada com sucesso!");
            return "redirect:/empresa/dashboard";
            
        } catch (Exception e) {
            e.printStackTrace(); // Isso vai mostrar o erro no console
            redirectAttributes.addFlashAttribute("erro", "Erro ao publicar vaga: " + e.getMessage());
            return "redirect:/empresa/dashboard";
        }
    }

    @GetMapping("/vaga/{vagaId}/candidaturas")
    public String candidaturasVaga(@PathVariable Long vagaId, HttpSession session, Model model) {
        Object usuario = session.getAttribute("usuarioLogado");
        if (usuario == null || !(usuario instanceof Empresa)) {
            return "redirect:/login";
        }
        
        Empresa empresa = (Empresa) usuario;
        
        // Buscar empresa atualizada do banco
        Empresa empresaManaged = empresaRepository.findById(empresa.getId())
            .orElseThrow(() -> new RuntimeException("Empresa não encontrada"));
        
        // Busca a vaga e verifica se pertence à empresa
        Vaga vaga = vagaRepository.findById(vagaId).orElse(null);
        
        if (vaga == null || !vaga.getEmpresa().getId().equals(empresaManaged.getId())) {
            return "redirect:/empresa/dashboard?erro=Vaga não encontrada";
        }
        
        // Busca candidaturas para esta vaga
        List<Candidatura> candidaturasVaga = candidaturaRepository.findByVagaId(vagaId);
        
        model.addAttribute("vaga", vaga);
        model.addAttribute("candidaturas", candidaturasVaga);
        model.addAttribute("empresa", empresaManaged);
        
        return "candidaturas-empresa";
    }

    @GetMapping("/vaga/{vagaId}/deletar")
    public String deletarVaga(@PathVariable Long vagaId, HttpSession session, RedirectAttributes redirectAttributes) {
        Object usuario = session.getAttribute("usuarioLogado");
        if (usuario == null || !(usuario instanceof Empresa)) {
            return "redirect:/login";
        }
        
        Empresa empresa = (Empresa) usuario;
        
        // Buscar empresa atualizada do banco
        Empresa empresaManaged = empresaRepository.findById(empresa.getId())
            .orElseThrow(() -> new RuntimeException("Empresa não encontrada"));
        
        // Verifica se a vaga pertence à empresa
        Vaga vaga = vagaRepository.findById(vagaId).orElse(null);
        
        if (vaga != null && vaga.getEmpresa().getId().equals(empresaManaged.getId())) {
            try {
                // Remove candidaturas associadas à vaga
                List<Candidatura> candidaturas = candidaturaRepository.findByVagaId(vagaId);
                candidaturaRepository.deleteAll(candidaturas);
                
                // Remove a vaga
                vagaRepository.deleteById(vagaId);
                
                redirectAttributes.addFlashAttribute("msg", "Vaga removida com sucesso!");
            } catch (Exception e) {
                e.printStackTrace();
                redirectAttributes.addFlashAttribute("erro", "Erro ao remover vaga: " + e.getMessage());
            }
        } else {
            redirectAttributes.addFlashAttribute("erro", "Vaga não encontrada ou não pertence à sua empresa");
        }
        
        return "redirect:/empresa/dashboard";
    }
}