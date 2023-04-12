package med.xuxao.api.usuario;

import jakarta.validation.constraints.NotBlank;

public record DadosLogin(String login, String senha){
}
