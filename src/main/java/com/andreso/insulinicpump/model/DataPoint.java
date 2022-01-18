package com.andreso.insulinicpump.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class DataPoint {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Integer timeStamp;
    private Integer glucoseLevel;

    protected DataPoint(){}

    public DataPoint(Integer timeStamp,Integer glucoseLevel){
        this.timeStamp = timeStamp;
        this.glucoseLevel = glucoseLevel;
    }

    @Override
    public String toString() {
        return "DataPoint{" +
                "id=" + id +
                ", timeStamp=" + timeStamp +
                ", glucoseLevel=" + glucoseLevel +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Integer timeStamp) {
        this.timeStamp = timeStamp;
    }

    public Integer getGlucoseLevel() {
        return glucoseLevel;
    }

    public void setGlucoseLevel(Integer glucoseLevel) {
        this.glucoseLevel = glucoseLevel;
    }
}
