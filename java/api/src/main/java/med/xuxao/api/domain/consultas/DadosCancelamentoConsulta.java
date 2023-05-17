package med.xuxao.api.domain.consultas;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;

public record DadosCancelamentoConsulta(
        @NotNull
        Long id,
        @NotNull
        String motivoCancelamento) {
}
