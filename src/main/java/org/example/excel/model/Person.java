package org.example.excel.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class Person {
    private String name;
    private int age;

    // This is to make lambok generated builder public
    public static class PersonBuilder {
        public PersonBuilder() {}
    }


}
