package org.example.excel;

import lombok.SneakyThrows;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

public class ExcelToObjectMappingService {


    @SneakyThrows
    public List<Person> readAndMap(String fileLocation){
        FileInputStream file = new FileInputStream(fileLocation);
        Workbook workbook = new XSSFWorkbook(file);
        Sheet sheet = workbook.getSheetAt(0);
        List<Person> people = new ArrayList<>();
        for (Row row : sheet) {
            if (row.getRowNum() == 0) continue;
            Person.PersonBuilder personBuilder = Person.builder();
            for (Cell cell : row) {
                if (cell.getColumnIndex() == 0) {
                    personBuilder.name(cell.getStringCellValue());
                } else if (cell.getColumnIndex() == 1) {
                    personBuilder.age((int)cell.getNumericCellValue());
                }
            }
            people.add(personBuilder.build());
        }
        return people;
    }

}
