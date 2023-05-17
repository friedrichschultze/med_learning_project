package med.xuxao.api.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import med.xuxao.api.domain.consultas.*;
import med.xuxao.api.domain.medico.Especialidade;
import med.xuxao.api.domain.medico.statusEnum;
import med.xuxao.api.domain.paciente.DadosListagemPaciente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import org.yaml.snakeyaml.util.EnumUtils;

@RestController
@RequestMapping("consultas")
public class ConsultaController {

    @Autowired
    private AgendamentoConsultas agenda;
    @Autowired
    private ConsultaRepository repository;

    @PostMapping
    @Transactional
    public ResponseEntity agendar(@RequestBody @Valid DadosAgendamentoConsulta dadosAgendamento, UriComponentsBuilder uriBuilder){
        if(dadosAgendamento.especialidade() != null && !ObjectUtils.containsConstant(Especialidade.values(), dadosAgendamento.especialidade(), true)){
            throw new ValidationException("Favor escolher entre as especialidades disponíveis.");
        }
        var dadosDetalhamentoConsulta = agenda.agendar(dadosAgendamento);
        var uri = uriBuilder.path("/consulta/{id}").buildAndExpand(dadosDetalhamentoConsulta.id()).toUri();
        return ResponseEntity.created(uri).body(dadosDetalhamentoConsulta);
    }

    @GetMapping
    public ResponseEntity<Page<DadosDetalhamentoConsulta>> listar(@PageableDefault(size = 10, sort = {"data"}) Pageable paginacao){
        var page = repository.findAllByStatus(paginacao, statusEnum.ACTIVE).map(DadosDetalhamentoConsulta::new);
        return ResponseEntity.ok(page);
    }

    @DeleteMapping
    @Transactional
    public ResponseEntity deletar(@RequestBody @Valid DadosCancelamentoConsulta dadosCancelamento){
        if(!repository.existsById(dadosCancelamento.id())) {
            throw new ValidationException("Id informado não corresponde a nenhuma consulta existente");
        }
        if(!ObjectUtils.containsConstant(MotivoCancelamentoEnum.values(), dadosCancelamento.motivoCancelamento(), true)) {
            throw new ValidationException("Favor escolher entre os motivos:  PACIENTE_DESISTIU, MEDICO_CANCELOU ou OUTROS.");
        }
        agenda.cancelar(dadosCancelamento);
        return ResponseEntity.noContent().build();
    }
}
