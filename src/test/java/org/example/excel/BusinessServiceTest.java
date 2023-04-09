package org.example.excel;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BusinessServiceTest {

    @Test
    public void readFileTest() throws Exception {
        BusinessService businessService = new BusinessService();
        businessService.process("src/test/resources/sample-file.xlsx");
        List<Person> people = businessService.getPeople();
        assertEquals(3, people.size());
        Person person = people.get(0);
        assertEquals("John", person.getName());
        assertEquals(31, person.getAge());
    }
}
