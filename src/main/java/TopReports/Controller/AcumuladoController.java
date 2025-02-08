package TopReports.Controller;


import TopReports.Dto.AcumuladoDTO;
import TopReports.Enity.Acumulado;
import TopReports.Exeption.ResourceNotFoundException;
import TopReports.Service.AcumuladoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.net.URI;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/acumulados")
public class AcumuladoController {

    @Autowired
    private AcumuladoService acumuladoService;



    @GetMapping
    public ResponseEntity<?> gerarRelatorio(
            @RequestParam(value = "inicio", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
            @RequestParam(value = "fim", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fim) {

        if (inicio == null || fim == null) {
            return ResponseEntity.badRequest().body("As datas de 'inicio' e 'fim' são obrigatórias.");
        }

        List<AcumuladoDTO> acumulados = acumuladoService.buscarVendasPorPeriodo(inicio, fim);
        BigDecimal totalValor = acumuladoService.calcularTotalValor(acumulados);

        Map<String, Object> relatorio = new HashMap<>();
        relatorio.put("acumulados", acumulados);
        relatorio.put("totalValor", totalValor);

        return ResponseEntity.ok(relatorio);
    }



    @GetMapping("/vendas/dia")
    public ResponseEntity<List<AcumuladoDTO>> buscarVendasDoDia() {
        LocalDate hoje = LocalDate.now();
        List<AcumuladoDTO> vendasDoDia = acumuladoService.buscarVendasPorPeriodo(hoje, hoje);
        return ResponseEntity.ok(vendasDoDia);
    }

    @GetMapping("/vendas/mes")
    public ResponseEntity<List<AcumuladoDTO>> buscarVendasDoMes() {
        LocalDate inicioDoMes = LocalDate.now().withDayOfMonth(1);
        LocalDate fimDoMes = LocalDate.now().withDayOfMonth(LocalDate.now().lengthOfMonth());
        List<AcumuladoDTO> vendasDoMes = acumuladoService.buscarVendasPorPeriodo(inicioDoMes, fimDoMes);
        return ResponseEntity.ok(vendasDoMes);
    }



    @GetMapping("/todos")
    public ResponseEntity<List<AcumuladoDTO>> obterAcumuladosAteUmAno(
            @RequestParam("inicio") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio) {

        LocalDate fim = inicio.plusYears(1);
        List<AcumuladoDTO> acumulados = acumuladoService.buscarVendasPorPeriodo(inicio, fim);
        return ResponseEntity.ok(acumulados);
    }


    @PostMapping
    public ResponseEntity<AcumuladoDTO> criarAcumulado(@RequestBody @Valid AcumuladoDTO acumulado) {
        Acumulado novoAcumulado = acumuladoService.salvarAcumulado(acumuladoService.fromDTO(acumulado));
        return ResponseEntity.created(URI.create("/acumulados/" + novoAcumulado.getId()))
                .body(acumuladoService.toDTO(novoAcumulado));
    }


    @PatchMapping("/{id}")
    public ResponseEntity<AcumuladoDTO> atualizarAcumuladoParcial(
            @PathVariable Long id,
            @RequestBody AcumuladoDTO acumuladoParcial) throws ResourceNotFoundException {
        Acumulado atualizado = acumuladoService.atualizarParcial(id, acumuladoParcial);
        return ResponseEntity.ok(acumuladoService.toDTO(atualizado));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarAcumulado(@PathVariable Long id) {
        try {
            acumuladoService.deletarAcumulado(id);
        } catch (ResourceNotFoundException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.noContent().build();
    }
}
