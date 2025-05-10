package br.com.grupo16pi.agendadigital.service;

import br.com.grupo16pi.agendadigital.model.Profissional;
import br.com.grupo16pi.agendadigital.repository.ProfissionalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service // Indica que esta classe é um serviço do Spring.
public class ProfissionalService {

    @Autowired
    private ProfissionalRepository profissionalRepository; // Lida com o acesso aos dados de Profissional.

    public List<Profissional> findAll() { // Busca todos os profissionais.
        return profissionalRepository.findAll();
    }

    public Optional<Profissional> findById(Long id) { // Busca um profissional pelo ID.
        return profissionalRepository.findById(id);
    }

    public Profissional save(Profissional profissional) { // Salva ou atualiza um profissional.
        return profissionalRepository.save(profissional);
    }

    public void deleteById(Long id) { // Deleta um profissional pelo ID.
        profissionalRepository.deleteById(id);
    }
    public List<Profissional> findByNome(String nome) {
        return profissionalRepository.findByNomeContainingIgnoreCase(nome);
    }
}