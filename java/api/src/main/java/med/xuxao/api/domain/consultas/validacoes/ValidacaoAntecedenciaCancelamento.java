package med.xuxao.api.domain.consultas.validacoes;

import jakarta.validation.ValidationException;
import med.xuxao.api.domain.consultas.ConsultaRepository;
import med.xuxao.api.domain.consultas.DadosAgendamentoConsulta;
import med.xuxao.api.domain.consultas.DadosCancelamentoConsulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
public class ValidacaoAntecedenciaCancelamento implements ValidacoesCancelamento{

    @Autowired
    private ConsultaRepository repository;

    @Override
    public void validacao(DadosCancelamentoConsulta dados) {
        var consulta = repository.getReferenceById(dados.id());
        var datetimeAgendamento = consulta.getData();
        var  datetimeNow= LocalDateTime.now();
        var antecedencia = Duration.between(datetimeNow, datetimeAgendamento).toHours();

        if(antecedencia < 24){
            throw new ValidationException("Não é possível cancelar uma consulta com menos de 24 horas de Antecedência");
        }
    }
}
