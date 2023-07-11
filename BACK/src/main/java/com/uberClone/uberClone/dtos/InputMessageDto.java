package com.uberClone.uberClone.dtos;

public class InputMessageDto {
    private String txt;

    public InputMessageDto() {
    }

    public InputMessageDto(String txt) {
        this.txt = txt;
    }

    public String getTxt() {
        return txt;
    }

    public void setTxt(String txt) {
        this.txt = txt;
    }
}
