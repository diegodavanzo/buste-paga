package com.bustepaga.buste_paga.service;

import com.bustepaga.buste_paga.model.Dipendente;
import com.bustepaga.buste_paga.repository.DipendenteRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DipendenteService {

    private final DipendenteRepository dipendenteRepository;

    public DipendenteService(DipendenteRepository dipendenteRepository) {
        this.dipendenteRepository = dipendenteRepository;
    }

    public Optional<Dipendente> findByEmail(String email) {
        return dipendenteRepository.findByEmail(email);
    }
}
