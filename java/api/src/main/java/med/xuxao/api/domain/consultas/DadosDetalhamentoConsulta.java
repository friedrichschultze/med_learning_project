package med.xuxao.api.domain.consultas;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import med.xuxao.api.domain.medico.DadosDetalhamentoMedico;
import med.xuxao.api.domain.medico.Especialidade;

import java.time.LocalDateTime;

public record DadosDetalhamentoConsulta(Long id, Long idMedico, Long idPaciente, LocalDateTime data) {
    public DadosDetalhamentoConsulta(Consulta consulta){
        this(consulta.getId(), consulta.getMedico().getId(), consulta.getPaciente().getId(), consulta.getData());
    }
}
