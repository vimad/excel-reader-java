package org.example.excel.spliterator;

import lombok.SneakyThrows;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.example.excel.reader.CellData;
import org.example.excel.reader.ExcelProcessorContext;

import java.io.FileInputStream;
import java.util.Spliterator;
import java.util.function.Consumer;

public class ApacheExcelReaderSpliterator<T> implements Spliterator<T> {

    private Spliterator<Row> rowSpliterator;
    private ExcelProcessorContext context;

    public ApacheExcelReaderSpliterator(ExcelProcessorContext context) {
        this.context = context;
        initIterator();
    }

    @SneakyThrows
    private void initIterator() {
        FileInputStream file = new FileInputStream(context.getFilePath());
        Workbook workbook = new XSSFWorkbook(file);
        Sheet sheet = workbook.getSheetAt(0);
        rowSpliterator = sheet.spliterator();
    }


    @Override
    public boolean tryAdvance(Consumer<? super T> action) {
        return rowSpliterator.tryAdvance(row -> rowConsumer(row, action));
    }

    private void rowConsumer(Row row, Consumer<? super T> action) {
        if (context.isSkipHeader() && row.getRowNum() == 0) return;
        for (Cell cell : row) {
            context.getCellConsumers().get(cell.getColumnIndex())
                    .accept(convertToCellData(cell));
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

    @Override
    public Spliterator<T> trySplit() {
        return null;
    }

    @Override
    public long estimateSize() {
        return rowSpliterator.estimateSize();
    }

    @Override
    public int characteristics() {
        return rowSpliterator.characteristics();
    }
}
