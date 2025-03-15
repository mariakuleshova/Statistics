/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.io.*;
import org.apache.poi.xssf.usermodel.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.poi.ss.usermodel.*;

/**
 *
 * @author maria
 */

public class DataAction {
    private List<double[]> importedData = new ArrayList<>();
   
    public List<double[]> downloadData(String filePath) throws IOException {
    importedData.clear();
    try (FileInputStream file = new FileInputStream(filePath);
        XSSFWorkbook workbook = new XSSFWorkbook(file)) {
        XSSFSheet sheet = workbook.getSheetAt(0);
        if (sheet == null) throw new IOException("Лист не найден.");

        int maxCol = sheet.getRow(0).getLastCellNum();

        // Читаем данные по столбцам
        for (int colIdx = 0; colIdx < maxCol; colIdx++) {
            List<Double> columnData = new ArrayList<>();
            for (Row row : sheet) {
                Cell cell = row.getCell(colIdx);
                if (cell != null) {
                    cell.removeFormula();
                    if (cell.getCellType() == CellType.NUMERIC) {
                        columnData.add(cell.getNumericCellValue());
                }
                }   
            }
            if (!columnData.isEmpty()) {
                importedData.add(columnData.stream().mapToDouble(Double::doubleValue).toArray());
            }
        }
        workbook.close();
    }

    return importedData;
}
    
    
public void uploadData(String filePath, List<Map<String, Double>> stats, double[][] covMatrix) throws IOException {
     try (XSSFWorkbook workbook = new XSSFWorkbook()) {
        XSSFSheet sheet = workbook.createSheet("Результаты");

        // Заголовки для основных статистик
        int rowIdx = 0;
        XSSFRow headerRow = sheet.createRow(rowIdx++);
        headerRow.createCell(0).setCellValue("Показатель");
        for (int i = 0; i < stats.size(); i++) {
            headerRow.createCell(i + 1).setCellValue("Выборка " + (i + 1));
        }

        // Заполнение основных статистик
        for (String metric : stats.get(0).keySet()) {
            XSSFRow row = sheet.createRow(rowIdx++);
            row.createCell(0).setCellValue(metric);
            for (int sampleIdx = 0; sampleIdx < stats.size(); sampleIdx++) {
                Double value = stats.get(sampleIdx).get(metric);
                row.createCell(sampleIdx + 1).setCellValue(value != null ? value : 0.0);
            }
        }

        // Разделение
        rowIdx += 2;

        // Заголовок матрицы ковариации
        XSSFRow covHeaderRow = sheet.createRow(rowIdx++);
        covHeaderRow.createCell(0).setCellValue("Ковариационная матрица:");

        // Заголовки выборок для матрицы
        XSSFRow covSubHeaderRow = sheet.createRow(rowIdx++);
        covSubHeaderRow.createCell(0).setCellValue("");
        for (int i = 0; i < covMatrix.length; i++) {
            covSubHeaderRow.createCell(i + 1).setCellValue("Выборка " + (i + 1));
        }

        // Заполнение матрицы
        for (int i = 0; i < covMatrix.length; i++) {
            XSSFRow row = sheet.createRow(rowIdx++);
            row.createCell(0).setCellValue("Выборка " + (i + 1));
            for (int j = 0; j < covMatrix[i].length; j++) {
                row.createCell(j + 1).setCellValue(covMatrix[i][j]);
            }
        }

        try (FileOutputStream fos = new FileOutputStream(filePath)) {
            workbook.write(fos);
        }
        workbook.close();
    }  
}
}

