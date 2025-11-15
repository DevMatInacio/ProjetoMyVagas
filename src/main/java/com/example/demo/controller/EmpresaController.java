package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.example.demo.model.Empresa;
import com.example.demo.repository.EmpresaRepository;
import java.util.List;

@Controller
@RequestMapping("/empresas")
public class EmpresaController {

    @Autowired
    private EmpresaRepository empresaRepository;

    @GetMapping
    public String listarEmpresas(Model model, @RequestParam(required = false) String msg) {
        List<Empresa> empresas = empresaRepository.findAll();
        model.addAttribute("empresas", empresas);
        model.addAttribute("msg", msg);
        return "empresa";
    }

    @PostMapping
    public String salvar(@RequestParam String nome,
                        @RequestParam String email,
                        @RequestParam String senha,
                        @RequestParam String nomeFantasia) {
        
        Empresa empresa = new Empresa(nome, email, senha, nomeFantasia);
        empresaRepository.save(empresa);
        return "redirect:/empresas?msg=Empresa cadastrada com sucesso!";
    }

    @GetMapping("/deletar/{id}")
    public String deletar(@PathVariable Long id) {
        empresaRepository.deleteById(id);
        return "redirect:/empresas?msg=Empresa removida com sucesso!";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        Empresa empresa = empresaRepository.findById(id).orElse(null);
        model.addAttribute("empresa", empresa);
        return "empresa";
    }
}