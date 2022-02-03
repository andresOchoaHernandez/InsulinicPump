package com.andreso.insulinicpump.view.controller;

import com.andreso.insulinicpump.device.MainControlLoop;
import com.andreso.insulinicpump.view.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.*;

@Controller
public class ViewControllerUi {

    @Autowired
    private DataPointsRepository dataset;

    @Autowired
    private DeviceDataRepository deviceData;

    @Autowired
    private BGBoundsRepository bounds;

    @Autowired
    private MainControlLoop mainControlLoop;

    @RequestMapping("/")
    public String chart(Model model){

        model.addAttribute("data",prepareData());

        Optional<BGBounds> entry = bounds.findById(bounds.count());
        if(entry.isPresent()){
            model.addAttribute("safeLowValue",entry.get().getSafeLowBound());
            model.addAttribute("safeHighValue",entry.get().getSafeHighBound());
        }

        Optional<DeviceData> lastRead = deviceData.findById(deviceData.count());
        if (lastRead.isPresent()){
            model.addAttribute("batteryLevel",lastRead.get().getBatteryLevel());
            model.addAttribute("insulinReservoir",lastRead.get().getInsulinReservoir());
            model.addAttribute("deviceStatus",lastRead.get().getDeviceStatus());
            model.addAttribute("graphDuration",lastRead.get().getGraphDuration());
            model.addAttribute("deliveredInsulin",lastRead.get().getDeliveredInsulin());
        }

        Optional<DataPoint> lastBloodGlucoseRead = dataset.findById(dataset.count());
        if(lastBloodGlucoseRead.isPresent()){
            model.addAttribute("lastBloodGlucoseRead",lastBloodGlucoseRead.get().getGlucoseLevel());
            model.addAttribute("derivative",lastBloodGlucoseRead.get().getDerivative());
        }

        return "chart";
    }

    private Map<String,List> prepareData(){

        Map<String,List> parsedDataset = new LinkedHashMap<>();

        LinkedList<Integer> data = new LinkedList<>();
        LinkedList<String> timeStamps = new LinkedList<>();

        for (DataPoint element: dataset.findAll()){
            data.add(element.getGlucoseLevel());
            timeStamps.add(element.getTimeStamp());
        }

        parsedDataset.put("data",data);
        parsedDataset.put("timeStamps",timeStamps);

        return parsedDataset;
    }

    @RequestMapping("/bolus")
    public String bolus(Model model){

        Optional<BGBounds> entry = bounds.findById(bounds.count());
        Optional<DataPoint> lastBloodGlucoseRead = dataset.findById(dataset.count());

        if(entry.isPresent() && lastBloodGlucoseRead.isPresent()){
            model.addAttribute("safeHighValue",entry.get().getSafeHighBound());
            model.addAttribute("lastBloodGlucoseRead",lastBloodGlucoseRead.get().getGlucoseLevel());
        }

        new Thread(() -> {
            mainControlLoop.bolus();
        }).start();

        return "bolus";
    }
}
