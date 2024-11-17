package Topmix.reports.Service;

import Topmix.reports.Enity.Producao;
import Topmix.reports.Repository.ProducaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class ProducaoService {

    @Autowired
    private ProducaoRepository producaoRepository;


    public List<Producao> obterProducaoPorPeriodo(LocalDate inicio, LocalDate fim) {
        return producaoRepository.findByPeriodo(inicio, fim);
    }

    public BigDecimal calcularTotalValor(List<Producao> producoes) {
        return producoes.stream()
                .map(Producao::getValor)
                .reduce(BigDecimal.ZERO, BigDecimal::add);




    }
}