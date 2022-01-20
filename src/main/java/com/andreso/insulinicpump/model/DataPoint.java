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
    private String timeStamp;
    private Integer glucoseLevel;
    private int derivative;

    protected DataPoint(){}

    public DataPoint(String timeStamp,Integer glucoseLevel,int derivative){
        this.timeStamp = timeStamp;
        this.glucoseLevel = glucoseLevel;
        this.derivative = derivative;
    }

    public int getDerivative() {
        return derivative;
    }

    public void setDerivative(int derivative) {
        this.derivative = derivative;
    }

    @Override
    public String toString() {
        return "DataPoint{" +
                "id=" + id +
                ", timeStamp='" + timeStamp + '\'' +
                ", glucoseLevel=" + glucoseLevel +
                ", derivative=" + derivative +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public Integer getGlucoseLevel() {
        return glucoseLevel;
    }

    public void setGlucoseLevel(Integer glucoseLevel) {
        this.glucoseLevel = glucoseLevel;
    }
}
