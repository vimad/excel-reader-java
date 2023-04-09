package org.example.excel;

import lombok.SneakyThrows;
import org.apache.poi.ss.usermodel.Cell;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class BusinessService {

    private Person.PersonBuilder personBuilder;
    private List<Person> people = new ArrayList<>();

    public List<Person> getPeople() {
        return people;
    }

    @SneakyThrows
    public void process(String filePath) {
        Map<Integer, Consumer<Cell>> cellConsumers = new HashMap<>();
        cellConsumers.put(0, this::setName);
        cellConsumers.put(1, this::setAge);
        ExcelProcessorContext context = ExcelProcessorContext.builder()
                .filePath(filePath)
                .rowStart(this::startRow)
                .rowEnd(this::endRow)
                .cellConsumer(cellConsumers)
                .skipHeader(true)
                .build();
        ExcelReader excelReader = new ApacheExcelReaderImpl();
        excelReader.readFile(context);

    }

    private void setName(Cell cell) {
        personBuilder.name(cell.getStringCellValue());
    }

    private void setAge(Cell cell) {
        personBuilder.age((int) cell.getNumericCellValue());
    }

    private void startRow() {
        personBuilder = new Person.PersonBuilder();
    }

    private void endRow() {
        people.add(personBuilder.build());
    }


}
