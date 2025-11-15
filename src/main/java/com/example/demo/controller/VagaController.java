package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.example.demo.model.Empresa;
import com.example.demo.model.Vaga;
import com.example.demo.repository.EmpresaRepository;
import com.example.demo.repository.VagaRepository;
import java.util.List;

@Controller
@RequestMapping("/vagas")
public class VagaController {

    @Autowired
    private VagaRepository vagaRepository;
    
    @Autowired
    private EmpresaRepository empresaRepository;

    @GetMapping
    public String listarVagas(Model model, @RequestParam(required = false) String msg) {
        List<Vaga> vagas = vagaRepository.findAll();
        List<Empresa> empresas = empresaRepository.findAll();
        
        model.addAttribute("vagas", vagas);
        model.addAttribute("msg", msg);
        model.addAttribute("empresas", empresas);
        return "vaga";
    }

    @PostMapping
    public String salvar(@RequestParam String titulo, 
                        @RequestParam String descricao, 
                        @RequestParam String local,
                        @RequestParam Long empresaId) {

        Empresa empresa = empresaRepository.findById(empresaId).orElse(null);

        if (empresa != null) {
            Vaga vaga = new Vaga(titulo, descricao, local, empresa);
            vagaRepository.save(vaga);
            empresa.publicarVaga(vaga);
        }

        return "redirect:/vagas?msg=Vaga publicada com sucesso!";
    }

    @GetMapping("/deletar/{id}")
    public String deletar(@PathVariable Long id) {
        vagaRepository.deleteById(id);
        return "redirect:/vagas?msg=Vaga removida com sucesso!";
    }

    public static List<Vaga> getVagas() {
        // Este método será removido depois - usado temporariamente para compatibilidade
        return null;
    }
}