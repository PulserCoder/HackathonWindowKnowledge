package ru.hackteam.window_of_knowledge.services;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.io.FileWriter;
import java.io.IOException;

@Service
public class ExcelServiceImpl {

    public String saveDataToBd(MultipartFile avatar, String startCell, String endCell) {
        StringBuilder resultText = new StringBuilder();  // Используем StringBuilder для накопления данных

        // Указываем путь для сохранения текста
        String textFilePath = "output.txt";

        try (InputStream fis = avatar.getInputStream();
             Workbook workbook = new XSSFWorkbook(fis);
             FileWriter writer = new FileWriter(textFilePath)) {

            // Получаем первый лист Excel
            Sheet sheet = workbook.getSheetAt(0);

            // Проверяем, были ли указаны начальная и конечная ячейки
            if (startCell != null && !startCell.isEmpty() && endCell != null && !endCell.isEmpty()) {
                // Преобразуем строки вида "E3", "G4" в индексы
                CellReference startCellRef = new CellReference(startCell);
                CellReference endCellRef = new CellReference(endCell);

                int startRow = startCellRef.getRow();
                int startCol = startCellRef.getCol();
                int endRow = endCellRef.getRow();
                int endCol = endCellRef.getCol();

                // Итерация по строкам и столбцам в указанном диапазоне
                for (int rowIndex = startRow; rowIndex <= endRow; rowIndex++) {
                    Row row = sheet.getRow(rowIndex);
                    if (row != null) { // Проверяем на null, если строка пуста
                        for (int colIndex = startCol; colIndex <= endCol; colIndex++) {
                            Cell cell = row.getCell(colIndex);
                            if (cell != null) { // Проверяем, если ячейка не null
                                String cellValue = getCellValueAsString(cell);
                                if (!cellValue.isEmpty()) {
                                    resultText.append(cellValue).append("\t");
                                }
                            }
                        }
                        writer.write("\n");  // Переход на новую строку
                    }
                }
            } else {
                // Если ячейки не указаны, обрабатываем весь файл
                for (Row row : sheet) {
                    for (Cell cell : row) {
                        if (cell != null) {
                            String cellValue = getCellValueAsString(cell);
                            if (!cellValue.isEmpty()) {
                                resultText.append(cellValue).append("\t");
                            }
                        }
                    }
                    writer.write("\n");  // Переход на новую строку
                }
            }

            System.out.println("Excel файл успешно преобразован в текст!");
            return resultText.toString();

        } catch (IOException e) {
            e.printStackTrace();
            return "Ошибка при обработке файла!";
        }
    }

    // Вынесенный метод для преобразования значения ячейки в строку
    private static String getCellValueAsString(Cell cell) {
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getDateCellValue().toString();
                } else {
                    return String.valueOf(cell.getNumericCellValue());
                }
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                return cell.getCellFormula();
            default:
                return "";
        }
    }

    // Преобразование строки вида "E3" в индекс строки и столбца
    public static class CellReference {
        private final int row;
        private final int col;

        public CellReference(String cellRef) {
            // Разделяем строку на букву (столбец) и цифры (строка)
            String columnPart = cellRef.replaceAll("[^A-Za-z]", "");
            String rowPart = cellRef.replaceAll("[^0-9]", "");

            // Преобразуем буквы в индекс столбца (A -> 0, B -> 1, ..., Z -> 25)
            col = convertColStringToIndex(columnPart);
            // Строка в Excel - это просто число (считается с 1), поэтому уменьшаем на 1
            row = Integer.parseInt(rowPart) - 1;
        }

        public int getRow() {
            return row;
        }

        public int getCol() {
            return col;
        }

        // Преобразуем буквы в индекс столбца (A = 0, B = 1, ...)
        private int convertColStringToIndex(String colString) {
            int colIndex = 0;
            for (int i = 0; i < colString.length(); i++) {
                colIndex = colIndex * 26 + (colString.charAt(i) - 'A' + 1);
            }
            return colIndex - 1;  // Индексация с 0
        }
    }
}
