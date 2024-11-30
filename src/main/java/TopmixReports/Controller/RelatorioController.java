package TopmixReports.Controller;

import TopmixReports.Enity.Producao;
import TopmixReports.Exeption.ResourceNotFoundException;
import TopmixReports.Service.ProducaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.net.URI;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/relatorios")

public class RelatorioController {

    @Autowired
    private ProducaoService producaoService;

    @GetMapping
    public ResponseEntity<?> gerarRelatorio(
            @RequestParam(value = "inicio", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
            @RequestParam(value = "fim", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fim) {

        if (inicio == null || fim == null) {
            return ResponseEntity.badRequest().body("As datas de 'inicio' e 'fim' são obrigatórias.");
        }

        List<Producao> producoes = producaoService.obterProducaoPorPeriodo(inicio, fim);
        BigDecimal totalValor = producaoService.calcularTotalValor(producoes);

        Map<String, Object> relatorio = new HashMap<>();
        relatorio.put("producoes", producoes);
        relatorio.put("totalValor", totalValor);

        return ResponseEntity.ok(relatorio);


    }

    // Retorna todas as produções até um ano a partir de uma data inicial
    @GetMapping("todos")
    public ResponseEntity<List<Producao>> obterProducoesAteUmAno(
            @RequestParam("inicio") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio) {

        LocalDate fim = inicio.plusYears(1);
        List<Producao> producoes = producaoService.obterProducaoPorPeriodo(inicio, fim);
        return ResponseEntity.ok(producoes);

    }


    @PostMapping
    public ResponseEntity<Producao> criarProducao(@RequestBody Producao producao) {
        Producao producaoSalva = producaoService.salvarProducao(producao);
        return ResponseEntity.created(URI.create("/producoes/" + producaoSalva.getId()))
                .body(producaoSalva);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Producao> atualizarProducaoParcial(@PathVariable Long id, @RequestBody Producao producao) throws ResourceNotFoundException {
        Producao producaoAtualizada = producaoService.atualizarProducaoParcial(id, producao);
        return ResponseEntity.ok(producaoAtualizada);
    }






}
