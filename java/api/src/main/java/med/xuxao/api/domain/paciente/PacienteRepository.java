package med.xuxao.api.domain.paciente;

import med.xuxao.api.domain.medico.statusEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PacienteRepository extends JpaRepository<Paciente, Long> {

    Page<Paciente> findAllByStatus(Pageable paginacao, statusEnum status);
}
