package med.xuxao.api.domain.medico;

import jakarta.persistence.Embedded;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import med.xuxao.api.domain.consultas.Consulta;
import med.xuxao.api.domain.consultas.DadosConsulta;
import med.xuxao.api.domain.endereco.DadosEndereco;
import med.xuxao.api.domain.endereco.Endereco;
import med.xuxao.api.domain.paciente.DadosPaciente;
import med.xuxao.api.domain.paciente.Paciente;
import med.xuxao.api.domain.paciente.PacienteRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class MedicoRepositoryTest {

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private TestEntityManager em;

    @Test
    @DisplayName("Deveria devolver null se o único Médico não está disponível")
    void escolherMedicoAleatorioLivreCaso1() {
        var proximaSegundaAs10 = LocalDate.now().
                with(DayOfWeek.MONDAY).
                atTime(10,00);

        var medico = em.persist(cadastrarMedico("Jorjona", "jorjona@medico.com", "11111", Especialidade.GINECOLOGIA));
        var paciente = em.persist(cadastrarPaciente("Janja", "janja@lula.com", "39787898866"));
        var consulta = em.persist(cadastrarConsulta(medico, paciente, proximaSegundaAs10));

        var medicoLivre = medicoRepository.escolherMedicoAleatorioLivre(Especialidade.GINECOLOGIA, proximaSegundaAs10);
        assertThat(medicoLivre).isNull();
    }

    @Test
    @DisplayName("Deveria devolver o médio caso esteja disponível")
    void escolherMedicoAleatorioLivreCaso2() {
        var proximaSegundaAs10 = LocalDate.now().
                with(DayOfWeek.MONDAY).
                atTime(10,00);

        var medico = em.persist(cadastrarMedico("Jorjona", "jorjona@medico.com", "11111", Especialidade.GINECOLOGIA));

        var medicoLivre = medicoRepository.escolherMedicoAleatorioLivre(Especialidade.GINECOLOGIA, proximaSegundaAs10);
        assertThat(medicoLivre).isEqualTo(medico);
    }

    private Medico cadastrarMedico(String nome, String email, String crm, Especialidade especialidade){
        return new Medico(dadosMedico(nome, email, crm, especialidade));
    }

    private DadosMedico dadosMedico(String nome, String email, String crm, Especialidade especialidade){
        var telefone = "11999999999";
        var endereco = new DadosEndereco(
                "Rua Dos Algoritimos",
                "Casa Rosa",
                "022222245",
                "99",
                "",
                "Sao Pedro",
                "RJ"
        );
        return new DadosMedico(nome, email, telefone, crm, especialidade, endereco);
    }

    private Paciente cadastrarPaciente(String nome, String email, String cpf){
        return new Paciente(dadosPaciente(nome, email, cpf));
    }

    private DadosPaciente dadosPaciente(String nome, String email, String cpf){
        var telefone = "11999999999";
        var endereco = new DadosEndereco(
                "Rua Dos Algoritimos",
                "Casa Rosa",
                "022222245",
                "99",
                "",
                "Sao Pedro",
                "RJ"
        );
        return new DadosPaciente(nome, email, telefone, cpf, endereco);
    }

    private Consulta cadastrarConsulta(Medico medico, Paciente paciente, LocalDateTime dataHora){
        return new Consulta(dadosConsulta(medico, paciente, dataHora));
    }

    private DadosConsulta dadosConsulta(Medico medico, Paciente paciente, LocalDateTime dataHora){
        return new DadosConsulta(paciente, medico, dataHora);
    }

}