package org.example.excel;

public class CellData {

    private Object value;

    public CellData(Object value) {
        this.value = value;
    }

    public String getValueAsString() {
        return (String) value;
    }

    public int getValueAsInt() {
        return Double.valueOf(value.toString()).intValue();
    }
}
