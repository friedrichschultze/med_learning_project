package med.xuxao.api.domain.paciente;
import med.xuxao.api.domain.endereco.Endereco;
import med.xuxao.api.domain.medico.statusEnum;

public record DadosDetalhamentoPaciente(Long id, String nome, String email, String telefone, String cpf,
                                        statusEnum status, Endereco endereco) {
    public DadosDetalhamentoPaciente(Paciente paciente){
        this(paciente.getId(), paciente.getNome(), paciente.getEmail(), paciente.getTelefone(), paciente.getCpf(), paciente.getStatus(), paciente.getEndereco());
    }
}
