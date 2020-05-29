package ru.job4j.ciname.models;

import java.util.Objects;

public class Hall {
    private Integer id;
    private Integer row;
    private Integer col;
    private Boolean busy;

    public Hall(Integer id, Integer row, Integer col, Boolean busy) {
        this.id = id;
        this.row = row;
        this.col = col;
        this.busy = busy;
    }

    public Hall(Integer row, Integer col, Boolean busy) {
        this.row = row;
        this.col = col;
        this.busy = busy;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRow() {
        return row;
    }

    public void setRow(Integer row) {
        this.row = row;
    }

    public Integer getCol() {
        return col;
    }

    public void setCol(Integer col) {
        this.col = col;
    }

    public Boolean getBusy() {
        return busy;
    }

    public void setBusy(Boolean busy) {
        this.busy = busy;
    }

    @Override
    public String toString() {
        return "{" +
                "\"id\":" + id +
                ", \"row\":\"" + row + '\"' +
                ", \"col\":\"" + col + '\"' +
                ", \"busy\":\"" + busy + '\"' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Hall hall = (Hall) o;
        return Objects.equals(id, hall.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
