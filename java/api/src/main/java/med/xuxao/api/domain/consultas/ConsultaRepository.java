package med.xuxao.api.domain.consultas;

import med.xuxao.api.domain.medico.statusEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Range;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface ConsultaRepository extends JpaRepository<Consulta, Long> {

    Page<Consulta> findAllByStatus(Pageable paginacao, statusEnum statusEnum);

    Boolean existsByMedicoIdAndDataAndStatus(Long aLong, LocalDateTime data, statusEnum statusEnum);

    Boolean existsByPacienteIdAndDataBetweenAndStatus(Long aLong, LocalDateTime inicioAtendimentos, LocalDateTime fimAtendimentos, statusEnum statusEnum);
}
