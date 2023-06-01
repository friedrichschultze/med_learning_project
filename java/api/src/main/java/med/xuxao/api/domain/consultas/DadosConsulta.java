package med.xuxao.api.domain.consultas;

import jakarta.validation.constraints.NotBlank;
import med.xuxao.api.domain.medico.Medico;
import med.xuxao.api.domain.paciente.Paciente;

import java.time.LocalDateTime;

public record DadosConsulta(
        @NotBlank Paciente paciente,
        @NotBlank Medico medico,
        @NotBlank LocalDateTime data
        ) {
}
