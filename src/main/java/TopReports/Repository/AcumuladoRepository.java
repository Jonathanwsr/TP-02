package TopReports.Repository;

import TopReports.Enity.Acumulado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AcumuladoRepository extends JpaRepository<Acumulado, Long> {
        List<Acumulado> findByDataBetween(LocalDate inicio, LocalDate fim);
}