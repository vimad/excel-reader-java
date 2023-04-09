package org.example.excel.reader;

import lombok.Builder;
import lombok.Getter;
import org.example.excel.reader.CellData;

import java.util.Map;
import java.util.function.Consumer;

@Getter
@Builder
public class ExcelProcessorContext {
    private String filePath;
    private Map<Integer, Consumer<CellData>> cellConsumers;
    private Runnable startRowTrigger;
    private Runnable endRowTrigger;
    private boolean skipHeader;
}
