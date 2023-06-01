package med.xuxao.api.domain.consultas;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import med.xuxao.api.domain.medico.Especialidade;

import java.time.LocalDateTime;

public record DadosAgendamentoConsulta(
        Long idMedico,
        @NotNull
        Long idPaciente,

        @NotNull
        @Future
        LocalDateTime data,
        String especialidade) {
}
