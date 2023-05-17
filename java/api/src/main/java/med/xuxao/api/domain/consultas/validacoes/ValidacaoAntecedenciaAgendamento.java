package med.xuxao.api.domain.consultas.validacoes;

import jakarta.validation.ValidationException;
import med.xuxao.api.domain.consultas.DadosAgendamentoConsulta;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDateTime;

@Component
public class ValidacaoAntecedenciaAgendamento implements ValidacoesAgendamento{

    @Override
    public void validacao(DadosAgendamentoConsulta dados) {
        var datetimeAgendamento = dados.data();
        var  datetimeNow= LocalDateTime.now();
        var antecedencia = Duration.between(datetimeNow, datetimeAgendamento).toMinutes();

        if(antecedencia < 30){
            throw new ValidationException("Não é possível agendar com menos de 30 minutos de Antecedência");
        }
    }
}
