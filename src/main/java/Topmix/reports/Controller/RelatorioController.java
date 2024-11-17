package Topmix.reports.Controller;

import Topmix.reports.Enity.Producao;
import Topmix.reports.Service.ProducaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/relatorios")
public class RelatorioController {

    @Autowired
    private ProducaoService producaoService;

    @GetMapping
    public ResponseEntity<Map<String, Object>> gerarRelatorio(
            @RequestParam("inicio") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
            @RequestParam("fim") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fim) {

        List<Producao> producoes = producaoService.obterProducaoPorPeriodo(inicio, fim);
        BigDecimal totalValor = producaoService.calcularTotalValor(producoes);

        Map<String, Object> relatorio = new HashMap<>();
        relatorio.put("producoes", producoes);
        relatorio.put("totalValor", totalValor);

        return ResponseEntity.ok(relatorio);
    }
}