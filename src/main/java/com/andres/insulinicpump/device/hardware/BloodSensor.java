package com.andres.insulinicpump.device.hardware;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

public class BloodSensor  extends DeviceComponent{

    /*  =========================== mocked data =========================== */
    private class MockedBloodSensorData{
        private LinkedList<Integer> dataPoints = new LinkedList<>();

        private void readData() throws FileNotFoundException{

            BufferedReader br = new BufferedReader(new FileReader("src\\main\\resources\\CGMData\\data.txt"));
            try{
                String line = br.readLine();
                while (line != null) {
                    dataPoints.add(Integer.parseInt(line));
                    line = br.readLine();
                }
            }
            catch (IOException e){
                e.printStackTrace();
            }
        }

        public MockedBloodSensorData(){
            try{
                readData();
            }
            catch(Exception e ){
                e.printStackTrace();
            }
        }

        public int getDatapoint(){
            return dataPoints.removeFirst();
        }

    }
    /*  ================================================================== */

    private MockedBloodSensorData mockedData;

    private int readBloodGlucoseLevel;

    public BloodSensor(){
        mockedData = new MockedBloodSensorData();

        readBloodGlucoseLevel = 0;
    }

    public int getMeasurement(){
        readBloodGlucoseLevel = mockedData.getDatapoint();
        return readBloodGlucoseLevel;
    }

    @Override
    public boolean selfTest() {
        return this.isDeviceWorkingProperly;
    }
}
