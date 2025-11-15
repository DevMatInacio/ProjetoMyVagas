package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;
import com.example.demo.model.*;
import com.example.demo.repository.*;
import org.springframework.beans.factory.annotation.Autowired;

@Controller
public class AuthController {

    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private CandidatoRepository candidatoRepository;
    
    @Autowired
    private EmpresaRepository empresaRepository;

    @GetMapping("/login")
    public String loginPage(@RequestParam(required = false) String redirect, 
                           @RequestParam(required = false) String erro,
                           @RequestParam(required = false) String msg,
                           Model model) {
        model.addAttribute("redirect", redirect);
        model.addAttribute("erro", erro);
        model.addAttribute("msg", msg);
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String email, 
                       @RequestParam String senha, 
                       HttpSession session,
                       @RequestParam(required = false) String redirect) {
        
        Usuario usuario = usuarioRepository.findByEmail(email).orElse(null);
        
        if (usuario != null && usuario.getSenha().equals(senha)) {
            session.setAttribute("usuarioLogado", usuario);
            session.setAttribute("tipoUsuario", usuario instanceof Candidato ? "CANDIDATO" : "EMPRESA");
            
            if (redirect != null && !redirect.isEmpty()) {
                return "redirect:" + redirect;
            }
            
            if (usuario instanceof Candidato) {
                return "redirect:/candidato/dashboard";
            } else {
                return "redirect:/empresa/dashboard";
            }
        }
        
        return "redirect:/login?erro=Credenciais invalidas";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login?logout=true";
    }

    @GetMapping("/cadastro")
    public String cadastroPage(@RequestParam(required = false) String tipo, 
                              @RequestParam(required = false) String erro,
                              @RequestParam(required = false) String msg,
                              Model model) {
        model.addAttribute("tipo", tipo != null ? tipo : "candidato");
        model.addAttribute("erro", erro);
        model.addAttribute("msg", msg);
        return "cadastro";
    }

    @PostMapping("/cadastro/candidato")
    public String cadastrarCandidato(@RequestParam String nome,
                                    @RequestParam String email,
                                    @RequestParam String senha,
                                    @RequestParam String curriculo,
                                    @RequestParam String telefone,
                                    @RequestParam String experiencia) {
        
        if (usuarioRepository.existsByEmail(email)) {
            return "redirect:/cadastro?tipo=candidato&erro=Email ja cadastrado";
        }
        
        Candidato novoCandidato = new Candidato(nome, email, senha, curriculo, telefone, experiencia);
        candidatoRepository.save(novoCandidato);
        
        return "redirect:/login?msg=Candidato cadastrado com sucesso! Faca login.";
    }

    @PostMapping("/cadastro/empresa")
    public String cadastrarEmpresa(@RequestParam String nome,
                                  @RequestParam String email,
                                  @RequestParam String senha,
                                  @RequestParam String nomeFantasia) {
        
        if (usuarioRepository.existsByEmail(email)) {
            return "redirect:/cadastro?tipo=empresa&erro=Email ja cadastrado";
        }
        
        Empresa novaEmpresa = new Empresa(nome, email, senha, nomeFantasia);
        empresaRepository.save(novaEmpresa);
        
        return "redirect:/login?msg=Empresa cadastrada com sucesso! Faca login.";
    }
}