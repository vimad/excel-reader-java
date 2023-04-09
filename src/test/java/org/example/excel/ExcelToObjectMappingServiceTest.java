package org.example.excel;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ExcelToObjectMappingServiceTest {

    @Test
    public void readFileTest() throws Exception {
        ExcelToObjectMappingService excelToObjectMappingService = new ExcelToObjectMappingService();
        List<Person> people = excelToObjectMappingService.readAndMap("src/test/resources/sample-file.xlsx");
        assertEquals(3, people.size());
        Person person = people.get(0);
        assertEquals("John", person.getName());
        assertEquals(31, person.getAge());
    }
}
