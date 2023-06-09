package med.xuxao.api.domain.medico;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;

public interface MedicoRepository extends JpaRepository<Medico, Long> {
    Page<Medico> findAllByStatus(Pageable paginacao, statusEnum status);

    @Query("""
            SELECT m 
            
            FROM Medico m  
            
            WHERE m.status = 'ACTIVE'
                AND m.especialidade = :especialidade
                AND m.id not in(
                        SELECT c.medico.id
                        FROM Consulta c
                        WHERE
                        c.data = :data
                        AND c.status = 'ACTIVE'
                )
            ORDER BY rand()
            LIMIT 1
            """)
    Medico escolherMedicoAleatorioLivre(Especialidade especialidade, LocalDateTime data);
}
