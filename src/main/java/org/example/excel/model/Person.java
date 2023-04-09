package org.example.excel.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Person {
    private String name;
    private int age;

    public static class PersonBuilder {
        private String name;
        private int age;

        public PersonBuilder age(int age) {
            this.age = age;
            return this;
        }

        public PersonBuilder name(String name) {
            this.name = name;
            return this;
        }

        public Person build() {
            return new Person(name, age);
        }
    }
}
