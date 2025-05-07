package br.com.grupo16pi.agendadigital.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.grupo16pi.agendadigital.DTOs.MatrizAgendaUpdateDTO;
import br.com.grupo16pi.agendadigital.model.Especialidade;
import br.com.grupo16pi.agendadigital.model.MatrizAgenda;
import br.com.grupo16pi.agendadigital.repository.EspecialidadeRepository;
import br.com.grupo16pi.agendadigital.repository.MatrizAgendaRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service // Indica que esta classe é um serviço do Spring.
public class MatrizAgendaService {

    @Autowired
    private MatrizAgendaRepository matrizAgendaRepository; // Lida com o acesso aos dados de MatrizAgenda.

    @Autowired
    private EspecialidadeRepository especialidadeRepository; // Lida com o acesso aos dados de Especialidade.

    public List<MatrizAgenda> findAll() { // Busca todas as matrizes de agenda.
        return matrizAgendaRepository.findAll();
    }

    public Optional<MatrizAgenda> findById(Long id) { // Busca uma matriz de agenda pelo ID.
        return matrizAgendaRepository.findById(id);
    }

    public MatrizAgenda save(MatrizAgenda matrizAgenda) { // Salva ou atualiza uma matriz de agenda.
        return matrizAgendaRepository.save(matrizAgenda);
    }

    public void deleteById(Long id) { // Deleta uma matriz de agenda pelo ID.
        matrizAgendaRepository.deleteById(id);
    }
    
    @Transactional
    public MatrizAgenda updatePartial(Long id, MatrizAgendaUpdateDTO dto) {
    MatrizAgenda matriz = matrizAgendaRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("MatrizAgenda não encontrada"));

    if (dto.getEspecialidadeId() != null) {
        Especialidade esp = especialidadeRepository.findById(dto.getEspecialidadeId())
            .orElseThrow(() -> new EntityNotFoundException("Especialidade não encontrada"));
        matriz.setEspecialidade(esp);
    }
    if (dto.getDiaSemana() != null) matriz.setDiaSemana(dto.getDiaSemana());
    if (dto.getHorarioInicio() != null) matriz.setHorarioInicio(dto.getHorarioInicio());
    if (dto.getHorarioFim() != null) matriz.setHorarioFim(dto.getHorarioFim());
    if (dto.getDisponivel() != null) matriz.setDisponivel(dto.getDisponivel());

    return matrizAgendaRepository.save(matriz);
    }   
}