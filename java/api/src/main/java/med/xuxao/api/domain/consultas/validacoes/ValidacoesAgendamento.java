package med.xuxao.api.domain.consultas.validacoes;

import med.xuxao.api.domain.consultas.DadosAgendamentoConsulta;
import org.springframework.stereotype.Component;

public interface ValidacoesAgendamento {
    public void validacao(DadosAgendamentoConsulta dados);
}
