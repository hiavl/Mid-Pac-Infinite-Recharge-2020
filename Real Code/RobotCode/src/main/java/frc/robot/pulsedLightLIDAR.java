package frc.robot;

import java.util.TimerTask;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.I2C.Port;


public class pulsedLightLIDAR { 
                                
    private I2C i2c;
    byte[] distance;
    private java.util.Timer updater;
    byte[] low;
    byte[] high;


    //Check register addresses if work with PWM
    private final int LIDAR_ADDR = 0x00;
    private final int LIDAR_CONFIG_REGISTER = 0x1e;
    private final int LIDAR_DISTANCE_REGISTER = 0x10;


    public pulsedLightLIDAR() {
        i2c = new I2C(Port.kMXP, LIDAR_ADDR);
        distance = new byte[2];
        updater = new java.util.Timer();
    }


    /**
     * Internally return Distance in cm
     * 
     * @return distance in cm
     */
    int getDistance() { 
                               
        return (int) Integer.toUnsignedLong(distance[0] << 8) + Byte.toUnsignedInt(distance[1]);
    }
  

    /**
     * Return Distance in Inches
     * 
     * @return distance in inches
     */
    public double getDistanceIn() {
        return (double) getDistance() * 0.393701; 
    }


   
    public void start() {
        updater.scheduleAtFixedRate(new LIDARUpdater(), 0, 100);
    }


    
    public void stop() {
        updater.cancel();
        updater = new java.util.Timer();
    }


    /**
     * Read from the sensor and update the internal "distance" variable with the result.
     */
    private void update() {
        i2c.write(0x00, 0x04); // Initiate measurement
        Timer.delay(0.04); // Delay for measurement to be taken
        i2c.read(0x0e, 2, distance); // Read in measurement
        Timer.delay(0.005); // Delay to prevent over polling
        i2c.read(0x0f, LIDAR_ADDR, low);
        i2c.read(0x10, LIDAR_ADDR, high);
    }

    byte[] receiveLow() {
        return low;
    }
    byte[] receiveHigh(){
        return high;
    }

    /**
     * Timer task to keep distance updated
     *
     */
    private class LIDARUpdater extends TimerTask {
        public void run() {
            while (true) {
                update();
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
