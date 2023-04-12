package med.xuxao.api.domain.medico;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import med.xuxao.api.domain.endereco.DadosEndereco;

public record DadosMedicoUpdate(
        @NotNull
        Long id,
        String nome,
        @Email
        String email,
        @Pattern(regexp = "\\d{11}")
        String telefone,
        @Valid
        DadosEndereco endereco) {
}
