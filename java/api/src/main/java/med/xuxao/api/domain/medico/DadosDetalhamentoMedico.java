package med.xuxao.api.domain.medico;

import med.xuxao.api.domain.endereco.Endereco;

public record DadosDetalhamentoMedico(Long id, String nome, String email, String telefone, String crm, Especialidade especialidade, statusEnum status, Endereco endereco) {
    public DadosDetalhamentoMedico(Medico medico){
     this(medico.getId(), medico.getNome(), medico.getEmail(), medico.getTelefone(), medico.getCrm(),
             medico.getEspecialidade(), medico.getStatus(), medico.getEndereco());
    }
}
