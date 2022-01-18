package com.andreso.insulinicpump.controller;

import com.andreso.insulinicpump.model.DataPoint;
import com.andreso.insulinicpump.model.DataPointsRepository;
import com.andreso.insulinicpump.model.DeviceData;
import com.andreso.insulinicpump.model.DeviceDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.*;

@Controller
public class ViewController {

    @Autowired
    private DataPointsRepository dataset;

    @Autowired
    private DeviceDataRepository deviceData;

    @RequestMapping("/")
    public String chart(Model model){

        model.addAttribute("data",prepareData());

        Optional<DeviceData> lastRead = deviceData.findById(deviceData.count());
        if (lastRead.isPresent()){
            model.addAttribute("batteryLevel",lastRead.get().getBatteryLevel());
            model.addAttribute("insulinReservoir",lastRead.get().getBatteryLevel());
            model.addAttribute("deviceStatus",lastRead.get().getDeviceStatus());
            model.addAttribute("graphDuration",lastRead.get().getBatteryLevel());
        }

        Optional<DataPoint> lastBloodGlucoseRead = dataset.findById(dataset.count());
        if(lastBloodGlucoseRead.isPresent()){
            model.addAttribute("lastBloodGlucoseRead",lastBloodGlucoseRead.get().getGlucoseLevel());
        }

        return "chart";
    }

    private LinkedList<Map<String,Integer>> prepareData(){
        LinkedList<Map<String,Integer>> parsedDataset = new LinkedList<>();
        for (DataPoint element: dataset.findAll()){
            Map<String,Integer> dataPoint = new HashMap<>();
            dataPoint.put("x",element.getTimeStamp());
            dataPoint.put("y",element.getGlucoseLevel());
            parsedDataset.add(dataPoint);
        }
        return parsedDataset;
    }
}
