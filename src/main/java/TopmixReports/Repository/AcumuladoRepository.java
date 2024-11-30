package TopmixReports.Repository;

import TopmixReports.Dto.AcumuladoDTO;
import TopmixReports.Enity.Acumulado;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface AcumuladoRepository extends JpaRepository<Acumulado, Long> {

        List<AcumuladoDTO> findAllByData(LocalDate data);
        List<AcumuladoDTO> findAllByDataBetween(LocalDate inicio, LocalDate fim);
}