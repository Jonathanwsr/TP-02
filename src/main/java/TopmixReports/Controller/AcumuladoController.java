package TopmixReports.Controller;


import TopmixReports.Dto.AcumuladoDTO;
import TopmixReports.Enity.Acumulado;
import TopmixReports.Service.AcumuladoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/acumulados")

public class AcumuladoController {


    private final AcumuladoService acumuladoService;

    public AcumuladoController(AcumuladoService acumuladoService) {
        this.acumuladoService = acumuladoService;
    }
    @GetMapping("/diarias")
    public List<AcumuladoDTO> buscarVendasDiarias() {
        return acumuladoService.buscarVendasDiarias();
    }

    @GetMapping("/semanais")
    public List<AcumuladoDTO> buscarVendasSemanais() {
        return acumuladoService.buscarVendasSemanais();
    }

    @GetMapping("/mensais")
    public List<AcumuladoDTO> buscarVendasMensais() {
        return acumuladoService.buscarVendasMensais();
    }

    @GetMapping("/periodo")
    public List<AcumuladoDTO> buscarVendasPorPeriodo(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fim) {
        return acumuladoService.buscarVendasPorPeriodo(inicio, fim);
    }

    @GetMapping("/somar")
    public BigDecimal somarVendas(@RequestParam LocalDate inicio, @RequestParam LocalDate fim) {
        List<AcumuladoDTO> vendas = acumuladoService.buscarVendasPorPeriodo(inicio, fim);
        return acumuladoService.somarValorDasVendas(vendas);
    }

    @GetMapping("/contar")
    public long contarVendas(@RequestParam LocalDate inicio, @RequestParam LocalDate fim) {
        List<AcumuladoDTO> vendas = acumuladoService.buscarVendasPorPeriodo(inicio, fim);
        return acumuladoService.contarQuantidadeDeVendas(vendas);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<AcumuladoDTO> atualizarParcial(
            @PathVariable Long id,
            @RequestBody AcumuladoDTO acumuladoParcial) {
        Acumulado atualizado = acumuladoService.atualizarParcial(id, acumuladoParcial);
        AcumuladoDTO dto = toDTO(atualizado); // Converte a entidade para DTO
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<?> salvarAcumulado(@RequestBody @Valid AcumuladoDTO acumulado, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(result.getAllErrors());
        }
        Acumulado entidade = fromDTO(acumulado);
        Acumulado salvo = acumuladoService.salvarAcumulado(entidade);
        AcumuladoDTO dto = toDTO(salvo);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarAcumulado(@PathVariable Long id) {
        acumuladoService.deletarAcumulado(id);
        return ResponseEntity.noContent().build();
    }


    private AcumuladoDTO toDTO(Acumulado acumulado) {
        return new AcumuladoDTO(
                acumulado.getVenda(),
                acumulado.getTipo(),
                acumulado.getValor(),
                acumulado.getData()
        );
    }

    private Acumulado fromDTO(AcumuladoDTO dto) {
        Acumulado acumulado = new Acumulado();
        acumulado.setVenda(dto.getVenda());
        acumulado.setTipo(dto.getTipo());
        acumulado.setValor(dto.getValor());
        acumulado.setData(dto.getData());
        return acumulado;
    }
}

