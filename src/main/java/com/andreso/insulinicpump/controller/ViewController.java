package com.andreso.insulinicpump.controller;

import com.andreso.insulinicpump.model.DataPoint;
import com.andreso.insulinicpump.model.DataPointsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.*;

@Controller
public class ViewController {

    @Autowired
    private DataPointsRepository dataset;

    @RequestMapping("/")
    public String chart(Model model){
        model.addAttribute("data",parseData());
        return "chart";
    }

    private LinkedList<Map<String,Integer>> parseData(){
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
