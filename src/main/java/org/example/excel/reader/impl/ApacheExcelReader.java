package org.example.excel.reader.impl;

import lombok.SneakyThrows;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.example.excel.reader.CellData;
import org.example.excel.reader.ExcelProcessorContext;
import org.example.excel.reader.ExcelReader;

import java.io.FileInputStream;

public class ApacheExcelReader implements ExcelReader {

    @Override
    @SneakyThrows
    public void readFile(ExcelProcessorContext context) {
        FileInputStream file = new FileInputStream(context.getFilePath());
        Workbook workbook = new XSSFWorkbook(file);
        Sheet sheet = workbook.getSheetAt(0);

        Runnable rowStart = context.getStartRowTrigger();
        Runnable rowEnd = context.getEndRowTrigger();
        for (Row row : sheet) {
            if (context.isSkipHeader() && row.getRowNum() == 0) continue;
            rowStart.run();
            for (Cell cell : row) {
                context.getCellConsumers().get(cell.getColumnIndex()).accept(convertToCellData(cell));
            }
            rowEnd.run();
        }
    }

    private CellData convertToCellData(Cell cell) {
        CellType cellType = cell.getCellType();
        if (cellType == CellType.NUMERIC) {
            return new CellData(cell.getNumericCellValue());
        } else if (cellType == CellType.STRING) {
            return new CellData(cell.getStringCellValue());
        }
        return null;
    }




}
