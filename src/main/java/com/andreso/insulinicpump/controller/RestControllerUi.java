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
            @RequestParam(value = "timeStamp") String timeStamp,
            @RequestParam(value = "glucoseLevel") Integer glucoseLevel,
            @RequestParam(value = "derivative") int derivative
            ){
        dataset.save(new DataPoint(timeStamp,glucoseLevel,derivative));
    }

    @PostMapping("/insertDeviceData")
    public void insertDeviceData(
            @RequestParam(value = "batteryLevel") Integer batteryLevel,
            @RequestParam(value = "insulinReservoir") Integer insulinReservoir,
            @RequestParam(value = "graphDuration") String graphDuration,
            @RequestParam(value = "deviceStatus") String deviceStatus,
            @RequestParam(value = "deliveredInsulin") Float deliveredInsulin
    ){
        deviceData.save(new DeviceData(batteryLevel, insulinReservoir, graphDuration,deviceStatus,deliveredInsulin));
    }
}
