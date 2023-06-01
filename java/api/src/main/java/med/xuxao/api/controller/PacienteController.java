package med.xuxao.api.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.xuxao.api.domain.medico.statusEnum;
import med.xuxao.api.domain.paciente.DadosListagemPaciente;
import med.xuxao.api.domain.paciente.Paciente;
import med.xuxao.api.domain.paciente.PacienteRepository;
import med.xuxao.api.domain.paciente.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/pacientes")
public class PacienteController {

    @Autowired
    private PacienteRepository repository;
    @PostMapping
    @Transactional
    public ResponseEntity cadastrar(@Valid @RequestBody DadosPaciente dados, UriComponentsBuilder uriBuilder){
        var paciente = repository.save(new Paciente(dados));
        var uri = uriBuilder.path("/pacientes/{id}").buildAndExpand(paciente.getId()).toUri();
        return ResponseEntity.created(uri).body(new DadosDetalhamentoPaciente(paciente));
    }

    @GetMapping
    public ResponseEntity<Page<DadosListagemPaciente>> listar(@PageableDefault(size = 10, sort = {"nome"}) Pageable paginacao){
        var page = repository.findAllByStatus(paginacao, statusEnum.ACTIVE).map(DadosListagemPaciente::new);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity listarById(@PathVariable Long id){
        var paciente = repository.getReferenceById(id);
        return ResponseEntity.ok(new DadosDetalhamentoPaciente(paciente));
    }

    @PutMapping
    @Transactional
    public ResponseEntity atualiza(@Valid @RequestBody DadosPacienteUpdate dados){
        var paciente = repository.getReferenceById(dados.id());
        paciente.atualizarInformacoes(dados);
        return ResponseEntity.ok(new DadosDetalhamentoPaciente(paciente));
    }

    @DeleteMapping("/{delete_id}")
    @Transactional
    public ResponseEntity deletar(@PathVariable Long delete_id){
        var paciente = repository.getReferenceById(delete_id);
        paciente.deleteInfo();
        return ResponseEntity.noContent().build();
    }
}
