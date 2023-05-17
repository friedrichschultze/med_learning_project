package med.xuxao.api.domain.consultas;

import jakarta.validation.ValidationException;
import med.xuxao.api.domain.consultas.validacoes.ValidacoesAgendamento;
import med.xuxao.api.domain.consultas.validacoes.ValidacoesCancelamento;
import med.xuxao.api.domain.medico.Especialidade;
import med.xuxao.api.domain.medico.Medico;
import med.xuxao.api.domain.medico.MedicoRepository;
import med.xuxao.api.domain.paciente.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AgendamentoConsultas {
    @Autowired
    private ConsultaRepository repository;
    @Autowired
    private MedicoRepository medicoRepository;
    @Autowired
    private PacienteRepository pacienteRepository;
    @Autowired
    private List<ValidacoesAgendamento> validacoes;
    @Autowired
    private List<ValidacoesCancelamento> validacoesCancelamento;

    public DadosDetalhamentoConsulta agendar(DadosAgendamentoConsulta dadosAgendamento) {
        if(!pacienteRepository.existsById(dadosAgendamento.idPaciente())){
            throw new ValidationException("ID do paciente não encontrado");
        }

        if(dadosAgendamento.idMedico() != null && !medicoRepository.existsById(dadosAgendamento.idMedico())){
            throw new ValidationException("ID do médico não encontrado");
        }

        validacoes.forEach(v -> v.validacao(dadosAgendamento));

        var paciente = pacienteRepository.getReferenceById(dadosAgendamento.idPaciente());
        var medico = chooseMedico(dadosAgendamento);
        if(medico == null){
            throw new ValidationException("Nenhum médico disponível nesse horário");
        }
        var dadosConsulta = new DadosConsulta(paciente, medico, dadosAgendamento.data());
        var consulta = repository.save(new Consulta(dadosConsulta));
        return new DadosDetalhamentoConsulta(consulta);
    }

    private Medico chooseMedico(DadosAgendamentoConsulta dadosAgendamento) {
        if(dadosAgendamento.idMedico() != null){
            return medicoRepository.getReferenceById(dadosAgendamento.idMedico());
        }

        if(dadosAgendamento.especialidade() == null) {
            throw new ValidationException("Especialidade é obrigatória quando médico não for escolhido!");
        }

        var especialidadeEnum =Especialidade.valueOf(dadosAgendamento.especialidade());

        return medicoRepository.escolherMedicoAleatorioLivre(especialidadeEnum, dadosAgendamento.data());
    }

    public void cancelar(DadosCancelamentoConsulta dadosCancelamento) {
        if(!repository.existsById(dadosCancelamento.id())){
            throw new ValidationException("ID da consulta não encontrado");
        }

        validacoesCancelamento.forEach(v -> v.validacao(dadosCancelamento));

        var motivoCancelamentoEnum =MotivoCancelamentoEnum.valueOf(dadosCancelamento.motivoCancelamento());

        var consulta = repository.getReferenceById(dadosCancelamento.id());
        consulta.cancelarConsulta(motivoCancelamentoEnum);
    }
}
