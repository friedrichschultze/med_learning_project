package med.xuxao.api.domain.consultas.validacoes;

import jakarta.validation.ValidationException;
import med.xuxao.api.domain.consultas.DadosAgendamentoConsulta;
import med.xuxao.api.domain.medico.MedicoRepository;
import med.xuxao.api.domain.medico.statusEnum;
import med.xuxao.api.domain.paciente.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidacaoMedicoAtivoAgendamento implements ValidacoesAgendamento{

    @Autowired
    private MedicoRepository medicoRepository;

    @Override
    public void validacao(DadosAgendamentoConsulta dados) {

        if(dados.idMedico()==null){
            return;
        }

        var medico = medicoRepository.getReferenceById(dados.idMedico());

        if(medico.getStatus().equals(statusEnum.INACTIVE)){
            throw new ValidationException("Não é possível agendar consultas com médicos inativos!");
        }
    }
}
