package Topmix.reports.Repository;

import Topmix.reports.Enity.Producao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ProducaoRepository extends JpaRepository<Producao, Long> {
    @Query("SELECT p FROM Producao p WHERE p.data BETWEEN :inicio AND :fim")
    List<Producao> findByPeriodo(@Param("inicio") LocalDate inicio, @Param("fim") LocalDate fim);


    List<Producao> findByDataBetween(LocalDate inicio, LocalDate fim);
}
