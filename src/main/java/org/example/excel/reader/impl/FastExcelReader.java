package org.example.excel.reader.impl;

import lombok.SneakyThrows;
import org.dhatim.fastexcel.reader.Cell;
import org.dhatim.fastexcel.reader.ReadableWorkbook;
import org.dhatim.fastexcel.reader.Row;
import org.dhatim.fastexcel.reader.Sheet;
import org.example.excel.reader.CellData;
import org.example.excel.reader.ExcelProcessorContext;
import org.example.excel.reader.ExcelReader;

import java.io.File;
import java.util.Iterator;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class FastExcelReader implements ExcelReader {

    @Override
    @SneakyThrows
    public void readFile(ExcelProcessorContext context) {
        File f = new File(context.getFilePath());

        try (ReadableWorkbook wb = new ReadableWorkbook(f)) {

            Sheet sheet = wb.getFirstSheet();

            try (Stream<Row> rows = sheet.openStream()) {

                Iterator<Row> it = rows.iterator();
                while (it.hasNext()) {
                    Row row = it.next();
                    if (context.isSkipHeader() && row.getRowNum() == 1) continue;
                    context.getStartRowTrigger().run();
                    row.stream().forEach(cell -> handleCell(cell, context.getCellConsumers()));
                    context.getEndRowTrigger().run();
                }

            }
        }
    }

    private void handleCell(Cell cell, Map<Integer, Consumer<CellData>> cellConsumer) {
        cellConsumer.get(cell.getColumnIndex()).accept(convertToCellData(cell));
    }

    private CellData convertToCellData(Cell cell) {
        return new CellData(cell.getValue());
    }




}
