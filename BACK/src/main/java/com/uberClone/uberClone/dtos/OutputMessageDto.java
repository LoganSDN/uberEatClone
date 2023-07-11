package com.uberClone.uberClone.dtos;

public class OutputMessageDto {
    private String txt;

    public OutputMessageDto() {
    }

    public OutputMessageDto(String txt) {
        this.txt = txt;
    }

    public String getTxt() {
        return txt;
    }

    public void setTxt(String txt) {
        this.txt = txt;
    }
}
