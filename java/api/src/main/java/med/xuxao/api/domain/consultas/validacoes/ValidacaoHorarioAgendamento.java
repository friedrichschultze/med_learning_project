package med.xuxao.api.domain.consultas.validacoes;

import jakarta.validation.ValidationException;
import med.xuxao.api.domain.consultas.DadosAgendamentoConsulta;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
@Component
public class ValidacaoHorarioAgendamento implements ValidacoesAgendamento{

    @Override
    public void validacao(DadosAgendamentoConsulta dados) {
        var domingo = dados.data().getDayOfWeek().equals(DayOfWeek.SUNDAY);
        var antesSete = dados.data().getHour() < 7;
        var depoisSeis = dados.data().getHour() > 18;

        if(domingo || antesSete || depoisSeis){
            throw new ValidationException("O horário de funcionamento é de Seg. a Sab. das 7 as 19h");
        }
    }
}
