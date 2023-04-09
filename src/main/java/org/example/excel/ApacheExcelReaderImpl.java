package org.example.excel;

import lombok.SneakyThrows;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;

public class ApacheExcelReaderImpl implements ExcelReader {

    @Override
    @SneakyThrows
    public void readFile(ExcelProcessorContext context) {
        FileInputStream file = new FileInputStream(context.getFilePath());
        Workbook workbook = new XSSFWorkbook(file);
        Sheet sheet = workbook.getSheetAt(0);

        Runnable rowStart = context.getRowStart();
        Runnable rowEnd = context.getRowEnd();
        for (Row row : sheet) {
            if (context.isSkipHeader() && row.getRowNum() == 0) continue;
            rowStart.run();
            for (Cell cell : row) {
                context.getCellConsumer().get(cell.getColumnIndex()).accept(convertToCellData(cell));
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
