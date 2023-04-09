package org.example.excel;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Person {
    private String name;
    private int age;
}
