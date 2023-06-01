package med.xuxao.api.domain.consultas.validacoes;

import jakarta.validation.ValidationException;
import med.xuxao.api.domain.consultas.ConsultaRepository;
import med.xuxao.api.domain.consultas.DadosAgendamentoConsulta;
import med.xuxao.api.domain.medico.statusEnum;
import med.xuxao.api.domain.paciente.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidacaoMedicoSegundoAgendamentoHora implements ValidacoesAgendamento{

    @Autowired
    private ConsultaRepository consultaRepository;

    @Override
    public void validacao(DadosAgendamentoConsulta dados) {

        if(dados.idMedico()==null){
            return;
        }

        var consultaMesmaHora = consultaRepository.existsByMedicoIdAndDataAndStatus(dados.idMedico(), dados.data(), statusEnum.ACTIVE);

        if(consultaMesmaHora){
            throw new ValidationException("O médico escolhido já possui atendimento nesta data e hora!");
        }
    }
}
