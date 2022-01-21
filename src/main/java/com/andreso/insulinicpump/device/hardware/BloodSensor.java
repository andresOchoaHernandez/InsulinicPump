package com.andreso.insulinicpump.device.hardware;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class BloodSensor {
    private class DataPoint{
        private final String timeStamp;
        private final String BG;

        public DataPoint(String timeStamp,String BG){
            this.timeStamp=timeStamp;
            this.BG = BG;
        }

        public String getTimeStamp() {
            return timeStamp;
        }

        public String getBG() {
            return BG;
        }

        @Override
        public String toString() {
            return "DataPoint{" +
                    "timeStamp='" + timeStamp + '\'' +
                    ", BG='" + BG + '\'' +
                    '}';
        }
    }
    private LinkedList<DataPoint> CGMData;
    private LinkedList<DataPoint> readData() throws FileNotFoundException{

        LinkedList<DataPoint> readData = new LinkedList<>();

        BufferedReader br = new BufferedReader(new FileReader("src\\main\\resources\\CGMData\\data.txt"));
        try{
            String line = br.readLine();

            while (line != null) {
                readData.add(new DataPoint(line.split(",")[0],line.split(",")[1]));
                line = br.readLine();
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }


        return readData;
    }

    public BloodSensor(){
        try{
            this.CGMData = readData();
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
        }

    }
    public String[] getMeasurement(){

        try {
            DataPoint measurement = CGMData.removeFirst();
            String[] result = {measurement.getTimeStamp(), measurement.getBG()};
            return result;
        }
        catch (NoSuchElementException e){
            return null;
        }
    }
}
