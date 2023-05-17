package med.xuxao.api.domain.consultas.validacoes;

import jakarta.validation.ValidationException;
import med.xuxao.api.domain.consultas.ConsultaRepository;
import med.xuxao.api.domain.consultas.DadosAgendamentoConsulta;
import med.xuxao.api.domain.medico.statusEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidacaoPacienteSegundoAgendamentoDia implements ValidacoesAgendamento{

    @Autowired
    private ConsultaRepository consultaRepository;

    @Override
    public void validacao(DadosAgendamentoConsulta dados) {

        var inicioAtendimentos = dados.data().withHour(7);
        var fimAtendimentos = dados.data().withHour(18);
        var consultaMesmoDia = consultaRepository.existsByPacienteIdAndDataBetweenAndStatus(dados.idPaciente(), inicioAtendimentos, fimAtendimentos, statusEnum.ACTIVE);

        if(consultaMesmoDia){
            throw new ValidationException("Não é possível agendar mais de uma consulta para um mesmo paciente no mesmo dia!");
        }
    }
}
