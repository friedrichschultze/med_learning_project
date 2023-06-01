package med.xuxao.api.domain.medico;

public record DadosListagemMedico(Long id, statusEnum status, String nome, String email, String crm, Especialidade especialidade) {
    public DadosListagemMedico(Medico medico){
        this(medico.getId(), medico.getStatus(), medico.getNome(), medico.getEmail(), medico.getCrm(), medico.getEspecialidade());
    }
}
