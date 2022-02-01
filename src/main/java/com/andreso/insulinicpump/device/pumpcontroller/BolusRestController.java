package com.andreso.insulinicpump.device.pumpcontroller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BolusRestController {

    @PostMapping("/calculateBolus")
    public void calculateBolus(
            @RequestParam(value="gramsOfCarbs") int gramsOfCarbs
    ){
        System.out.println("I'M HERE!");
    }
}
