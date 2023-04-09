package org.example.excel;

import lombok.Builder;
import lombok.Getter;

import java.util.Map;
import java.util.function.Consumer;

@Getter
@Builder
public class ExcelProcessorContext {
    private String filePath;
    private Map<Integer, Consumer<CellData>> cellConsumer;
    private Runnable rowStart;
    private Runnable rowEnd;
    private boolean skipHeader;
}
