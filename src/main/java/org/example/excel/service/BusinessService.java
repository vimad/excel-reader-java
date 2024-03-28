package org.example.excel.service;

import lombok.SneakyThrows;
import org.example.excel.model.Person;
import org.example.excel.reader.CellData;
import org.example.excel.reader.ExcelProcessorContext;
import org.example.excel.reader.ExcelReader;
import org.example.excel.reader.impl.FastExcelReader;

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
        ExcelProcessorContext context = getExcelProcessorContext(filePath);
//        ExcelReader excelReader = new ApacheExcelReader();
        ExcelReader excelReader = new FastExcelReader();
        excelReader.readFile(context);
    }

    private ExcelProcessorContext getExcelProcessorContext(String filePath) {
        Map<Integer, Consumer<CellData>> cellConsumers = getCellConsumerMap();
        return ExcelProcessorContext.builder()
                .filePath(filePath)
                .startRowTrigger(this::startRow)
                .endRowTrigger(this::processRow)
                .cellConsumers(cellConsumers)
                .skipHeader(true)
                .build();
    }

    private Map<Integer, Consumer<CellData>> getCellConsumerMap() {
        Map<Integer, Consumer<CellData>> cellConsumers = new HashMap<>();
        cellConsumers.put(0, this::setName);
        cellConsumers.put(1, this::setAge);
        return cellConsumers;
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

    private void processRow() {
        Person person = personBuilder.build();
        people.add(person);
    }


}
