package com.andres.insulinicpump.device.pumpcontroller;

import com.andres.insulinicpump.device.MainControlLoop;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BolusRestController {

    @Autowired
    private MainControlLoop mainControlLoop;

    @PostMapping("/calculateBolus")
    public void calculateBolus(
            @RequestParam(value="gramsOfCarbs") int gramsOfCarbs
    ){
            mainControlLoop.setGramsOfCarbs(gramsOfCarbs);
            mainControlLoop.setWaitForRestControllerData(false);
    }
}
