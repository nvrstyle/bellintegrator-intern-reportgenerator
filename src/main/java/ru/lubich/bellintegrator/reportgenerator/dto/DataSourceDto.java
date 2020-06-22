package ru.lubich.bellintegrator.reportgenerator.dto;

public class DataSourceDto {

    private String number;
    private String date;
    private String fullName;

    public DataSourceDto(String number, String date, String fullName) {
        this.number = number;
        this.date = date;
        this.fullName = fullName;
    }

    public DataSourceDto() {}

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
