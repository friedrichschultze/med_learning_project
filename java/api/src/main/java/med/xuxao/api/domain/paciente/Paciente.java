package med.xuxao.api.domain.paciente;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.xuxao.api.domain.endereco.Endereco;
import med.xuxao.api.domain.medico.statusEnum;

@Table(name="pacientes")
@Entity(name="paciente")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Paciente {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long Id;
        @Enumerated(EnumType.STRING)
        private statusEnum status;
        private String nome;
        private String email;
        private String telefone;
        private String cpf;
        @Embedded
        private Endereco endereco;

    public Paciente(DadosPaciente dados) {
            this.status = statusEnum.ACTIVE;
            this.nome = dados.nome();
            this.email = dados.email();
            this.telefone = dados.telefone();
            this.cpf = dados.cpf();
            this.endereco = new Endereco(dados.endereco());
    }

        public void atualizarInformacoes(DadosPacienteUpdate dados) {
                if (dados.nome() != null) {
                        this.nome = dados.nome();
                }
                if (dados.telefone() != null) {
                        this.telefone = dados.telefone();
                }
                if (dados.email() != null) {
                        this.email = dados.email();
                }
                if (dados.endereco() != null) {
                        this.endereco.atualizarInformacoes(dados.endereco());
                }
        }


        public void deleteInfo() {this.status = statusEnum.INACTIVE;}
}
