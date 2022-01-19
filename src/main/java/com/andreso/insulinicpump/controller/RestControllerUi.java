package com.andreso.insulinicpump.controller;

import com.andreso.insulinicpump.model.DataPoint;
import com.andreso.insulinicpump.model.DataPointsRepository;
import com.andreso.insulinicpump.model.DeviceData;
import com.andreso.insulinicpump.model.DeviceDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestControllerUi {

    @Autowired
    private DataPointsRepository dataset;

    @Autowired
    private DeviceDataRepository deviceData;

    @PostMapping("/insertDataPoint")
    public void insertDataPoint(
            @RequestParam(value = "timeStamp",required = true) String timeStamp,
            @RequestParam(value = "glucoseLevel",required = true) Integer glucoseLevel
            ){
        dataset.save(new DataPoint(timeStamp,glucoseLevel));
    }

    @PostMapping("/insertDeviceData")
    public void insertDeviceData(
            @RequestParam(value = "batteryLevel",required = true) Integer batteryLevel,
            @RequestParam(value = "insulinReservoir",required = true) Integer insulinReservoir,
            @RequestParam(value = "graphDuration",required = true) Integer graphDuration,
            @RequestParam(value = "deviceStatus",required = true) String deviceStatus
    ){
        deviceData.save(new DeviceData(batteryLevel, insulinReservoir, graphDuration,deviceStatus));
    }
}
