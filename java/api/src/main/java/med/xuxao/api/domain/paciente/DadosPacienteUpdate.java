package med.xuxao.api.domain.paciente;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import med.xuxao.api.domain.endereco.DadosEndereco;

public record DadosPacienteUpdate(
        @NotNull Long id,
        String nome,

        @Email String email,

        String telefone,

        @Valid DadosEndereco endereco) {
}
