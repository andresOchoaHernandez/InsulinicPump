package com.andreso.insulinicpump.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class BGBounds {

    @Id
    private Long id=1l;
    private int safeLowBound;
    private int safeHighBound;

    protected BGBounds(){}

    public BGBounds(int safeLowBound, int safeHighBound) {
        this.safeLowBound = safeLowBound;
        this.safeHighBound = safeHighBound;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getSafeLowBound() {
        return safeLowBound;
    }

    public void setSafeLowBound(int safeLowBound) {
        this.safeLowBound = safeLowBound;
    }

    public int getSafeHighBound() {
        return safeHighBound;
    }

    public void setSafeHighBound(int safeHighBound) {
        this.safeHighBound = safeHighBound;
    }
}
