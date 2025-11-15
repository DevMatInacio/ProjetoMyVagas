package com.example.demo.repository;

import com.example.demo.model.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmpresaRepository extends JpaRepository<Empresa, Long> {
    // Métodos específicos podem ser adicionados aqui se necessário
}