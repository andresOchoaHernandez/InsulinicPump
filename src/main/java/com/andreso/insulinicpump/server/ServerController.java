package com.andreso.insulinicpump.server;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.*;

@Controller
public class ServerController {


    @RequestMapping("/")
    public String chart(Model model){
        model.addAttribute("data",getData());
        return "chart";
    }

    private LinkedList<Map<String,Integer>> getData(){
        LinkedList<Map<String,Integer>> dataset = new LinkedList<>();

        for(Integer i = 1;i<=10;i++){
            Map<String,Integer> dataPoint = new HashMap<>();
            dataPoint.put("x",i);
            dataPoint.put("y",i);
            dataset.add(dataPoint);
        }

        return dataset;
    }
}
