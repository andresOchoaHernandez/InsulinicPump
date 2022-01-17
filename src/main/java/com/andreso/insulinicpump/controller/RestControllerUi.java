package com.andreso.insulinicpump.controller;

import com.andreso.insulinicpump.model.DataPoint;
import com.andreso.insulinicpump.model.DataPointsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestControllerUi {

    @Autowired
    private DataPointsRepository dataset;

    @PostMapping("/insertDataPoint")
    public void insertDataPoint(
            @RequestParam(value = "timeStamp",required = true) Integer timeStamp,
            @RequestParam(value = "glucoseLevel",required = true) Integer glucoseLevel
            ){
        dataset.save(new DataPoint(timeStamp,glucoseLevel));
    }

}
