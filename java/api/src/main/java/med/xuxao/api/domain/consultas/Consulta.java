package med.xuxao.api.domain.consultas;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.xuxao.api.domain.medico.Especialidade;
import med.xuxao.api.domain.medico.Medico;
import med.xuxao.api.domain.medico.statusEnum;
import med.xuxao.api.domain.paciente.Paciente;
import org.hibernate.annotations.Fetch;

import java.time.LocalDateTime;

@Table(name="consultas")
@Entity(name="Consulta")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of="id")
public class Consulta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="medico_id")
    private Medico medico;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="paciente_id")
    private Paciente paciente;
    private LocalDateTime data;
    @Enumerated(EnumType.STRING)
    private MotivoCancelamentoEnum motivoCancelamento;
    @Enumerated(EnumType.STRING)
    private statusEnum status;


    public Consulta(DadosConsulta dados){
        this.medico = dados.medico();
        this.paciente = dados.paciente();
        this.data = dados.data();
        this.status = statusEnum.ACTIVE;
        this.motivoCancelamento = null;
    }

    public void cancelarConsulta(MotivoCancelamentoEnum motivoCancelamento) {
        this.status = statusEnum.INACTIVE;
        this.motivoCancelamento = motivoCancelamento;
    }
}

