package med.xuxao.api.domain.consultas.validacoes;

import jakarta.validation.ValidationException;
import med.xuxao.api.domain.consultas.DadosAgendamentoConsulta;
import med.xuxao.api.domain.medico.statusEnum;
import med.xuxao.api.domain.paciente.Paciente;
import med.xuxao.api.domain.paciente.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
public class ValidacaoPacienteAtivoAgendamento implements ValidacoesAgendamento{

    @Autowired
    private PacienteRepository pacienteRepository;

    @Override
    public void validacao(DadosAgendamentoConsulta dados) {

        var paciente = pacienteRepository.getReferenceById(dados.idPaciente());

        if(paciente.getStatus().equals(statusEnum.INACTIVE)){
            throw new ValidationException("Não é possível agendar consultas com pacientes inativos!");
        }
    }
}
