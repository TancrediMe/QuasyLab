package it.unicam.quasylab.gpmonitor.sensornetworks;

import it.unicam.quasylab.gpmonitor.monitor.Network;
import it.unicam.quasylab.gpmonitor.monitor.Packet;

import java.util.ArrayList;

public class NetworkEmulator implements Network {
    private final ArrayList<String> devices;
    private double min,max;

    public NetworkEmulator() {
        this.devices = new ArrayList<>();
    }

    @Override
    public void init(String... parameters) {
        StringBuilder s = new StringBuilder("Network configuration INITIALIZED:");
        s.append(parameters[0]);
        for(int i=1;i<parameters.length;i++)
            s.append(", ").append(parameters[i]);
        System.out.println(s.toString());
        min=Double.parseDouble(parameters[0]);
        max=Double.parseDouble(parameters[1]);
        if(min>max) throw new IllegalArgumentException("MIN NON PUO' ESSERE MAGGIORE DI MAX");
    }

    @Override
    public void subscribe(String deviceID) {
        devices.add(deviceID);
        System.out.println("Network will send data for "+deviceID);
    }

    @Override
    public Packet receive() {
        sleep(rnd(2000,300));
        Packet p= new Packet(devices.get(rnd(devices.size(),0)),rnd(max,min),rnd(max,min),rnd(max,min));
        StringBuilder s = new StringBuilder("Device: "+p.getId()+" SENDS: [");
        s.append(p.getValue()[0]);
        for(int i=1;i<p.getValue().length;i++){
            s.append(", "+p.getValue()[i]);
        }
        s.append("]");
        System.out.println(s);
        return p;
    }


    private int rnd(double max, double min){
        return (int) ((Math.random()*(max-min))+min);
    }

    private void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
