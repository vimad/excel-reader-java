package org.example.excel;

import lombok.Builder;
import lombok.Getter;
import org.apache.poi.ss.usermodel.Cell;

import java.util.Map;
import java.util.function.Consumer;

@Getter
@Builder
public class ExcelProcessorContext {
    private String filePath;
    private Map<Integer, Consumer<Cell>> cellConsumer;
    private Runnable rowStart;
    private Runnable rowEnd;
    private boolean skipHeader;
}
