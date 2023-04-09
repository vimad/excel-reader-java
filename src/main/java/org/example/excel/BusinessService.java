package org.example.excel;

import lombok.SneakyThrows;

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
        Map<Integer, Consumer<CellData>> cellConsumers = new HashMap<>();
        cellConsumers.put(0, this::setName);
        cellConsumers.put(1, this::setAge);
        ExcelProcessorContext context = ExcelProcessorContext.builder()
                .filePath(filePath)
                .rowStart(this::startRow)
                .rowEnd(this::endRow)
                .cellConsumer(cellConsumers)
                .skipHeader(true)
                .build();
//        ExcelReader excelReader = new ApacheExcelReaderImpl();
        ExcelReader excelReader = new FastExcelReaderImpl();
        excelReader.readFile(context);

    }

    private void setName(CellData cell) {
        personBuilder.name(cell.getValueAsString());
    }

    private void setAge(CellData cell) {
        personBuilder.age(cell.getValueAsInt());
    }

    private void startRow() {
        personBuilder = new Person.PersonBuilder();
    }

    private void endRow() {
        people.add(personBuilder.build());
    }


}
