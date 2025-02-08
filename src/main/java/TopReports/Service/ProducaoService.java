package TopReports.Service;

import TopReports.Enity.Producao;
import TopReports.Exeption.ResourceNotFoundException;
import TopReports.Repository.ProducaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
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

    public Producao salvarProducao(Producao producao) {
        return producaoRepository.save(producao);
    }


    public Producao atualizarProducaoParcial(Long id, Producao producao) throws ResourceNotFoundException {
        Producao producaoExistente = producaoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produção não encontrada: " + id));

        // Update only the fields that have values in the request body
        if (producao.getProduto() != null) {
            producaoExistente.setProduto(producao.getProduto());
        }
        if (producao.getQuantidade() != null) {
            producaoExistente.setQuantidade(producao.getQuantidade());
        }
        if (producao.getValor() != null) {
            producaoExistente.setValor(producao.getValor());
        }
        if (producao.getData() != null) {
            producaoExistente.setData(producao.getData());
        }

        return producaoRepository.save(producaoExistente);
    }


    public ByteArrayOutputStream gerarRelatorioExcel(List<Producao> producoes) {
        return null;
    }
}