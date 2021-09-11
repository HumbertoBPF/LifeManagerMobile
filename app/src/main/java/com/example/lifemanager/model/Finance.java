package com.example.lifemanager.model;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.example.lifemanager.enums.Sector;
import com.example.lifemanager.enums.TypeFinance;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Calendar;

@Entity
public class Finance {

    @PrimaryKey(autoGenerate = true)
    private Long id;
    private String name;
    private Calendar date;
    private Integer month;
    private Integer year;
    private BigDecimal value;
    private Sector sector;
    private TypeFinance typeFinance;

    public Finance() {
    }

    @Ignore
    public Finance(String name, Calendar date, Integer month, Integer year, BigDecimal value, Sector sector,
                   TypeFinance typeFinance) {
        this.name = name;
        this.date = date;
        this.month = month;
        this.year = year;
        this.value = value;
        this.sector = sector;
        this.typeFinance = typeFinance;
    }

    @Ignore
    public Finance(Long id, String name, Calendar date, Integer month, Integer year, BigDecimal value, Sector sector,
                   TypeFinance typeFinance) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.month = month;
        this.year = year;
        this.value = value;
        this.sector = sector;
        this.typeFinance = typeFinance;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Calendar getDate() {
        return date;
    }
    public void setDate(Calendar date) {
        this.date = date;
    }

    public Integer getMonth() {
        return month;
    }
    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getYear() {
        return year;
    }
    public void setYear(Integer year) {
        this.year = year;
    }

    public BigDecimal getValue() {
        return value;
    }
    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public Sector getSector() {
        return sector;
    }
    public void setSector(Sector sector) {
        this.sector = sector;
    }

    public TypeFinance getTypeFinance() {
        return typeFinance;
    }
    public void setTypeFinance(TypeFinance typeFinance) {
        this.typeFinance = typeFinance;
    }

}
