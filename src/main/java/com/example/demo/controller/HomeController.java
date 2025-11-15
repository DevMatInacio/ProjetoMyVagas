package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.example.demo.model.Vaga;
import com.example.demo.repository.VagaRepository;
import java.util.List;

@Controller
public class HomeController {
    
    @Autowired
    private VagaRepository vagaRepository;
    
    @GetMapping("/")
    public String index(Model model) {
        List<Vaga> vagas = vagaRepository.findAll();
        model.addAttribute("vagas", vagas);
        return "index";
    }
}