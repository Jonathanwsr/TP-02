package Topmix.reports.Service;


import Topmix.reports.Enity.Producao;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class ExportacaoService {


    public ByteArrayOutputStream gerarRelatorioExcel(List<Producao> producoes) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Relatório de Produção");

        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Produto");
        headerRow.createCell(1).setCellValue("Quantidade");
        headerRow.createCell(2).setCellValue("Valor");
        headerRow.createCell(3).setCellValue("Data");

        int rowNum = 1;
        for (Producao producao : producoes) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(producao.getProduto());
            row.createCell(1).setCellValue(producao.getQuantidade());
            row.createCell(2).setCellValue(producao.getValor().doubleValue());
            row.createCell(3).setCellValue(producao.getData().toString());
        }

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        workbook.close();
        return outputStream;
    }
}

