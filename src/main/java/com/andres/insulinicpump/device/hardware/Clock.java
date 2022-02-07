package com.andres.insulinicpump.device.hardware;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class Clock extends DeviceComponent{

    /* mocked time */
    private long fiveMinutesOffSet = 300000;
    private Timestamp previousTimeStamp;
    private boolean firstTimeStamp = true;
    /*============*/

    @Override
    public boolean selfTest() {
        return this.isDeviceWorkingProperly;
    }

    public String getCurrentTimeStamp(){

        /* Normally this method just returns the string of the current timeStamp  */

        if (firstTimeStamp){
            Timestamp currentTimeStamp = new Timestamp(System.currentTimeMillis());
            previousTimeStamp = currentTimeStamp;
            firstTimeStamp = false;

            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(currentTimeStamp);
        }

        String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Timestamp(previousTimeStamp.getTime() + fiveMinutesOffSet));

        previousTimeStamp = new Timestamp(previousTimeStamp.getTime() + fiveMinutesOffSet);

        return timeStamp;
    }
}