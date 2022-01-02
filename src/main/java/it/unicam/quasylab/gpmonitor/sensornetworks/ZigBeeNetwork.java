package it.unicam.quasylab.gpmonitor.sensornetworks;


import com.digi.xbee.api.RemoteXBeeDevice;
import com.digi.xbee.api.models.XBee64BitAddress;
import com.digi.xbee.api.models.XBeeMessage;
import it.unicam.quasylab.gpmonitor.monitor.Network;
import it.unicam.quasylab.gpmonitor.monitor.Packet;

import com.digi.xbee.api.XBeeDevice;
import com.digi.xbee.api.exceptions.XBeeException;

import java.util.HashMap;
import java.util.Map;

public class ZigBeeNetwork implements Network {
    Map<RemoteXBeeDevice,String> devs = new HashMap<>();
    XBeeDevice dev;
    String regex;
    @Override
    public void init(String... parameters) throws XBeeException {
        dev=new XBeeDevice(parameters[0],Integer.parseInt(parameters[1]));
        dev.open();
        regex=parameters[2];
    }

    @Override
    public void subscribe(String deviceID) throws XBeeException {
        RemoteXBeeDevice rdev = new RemoteXBeeDevice(dev,new XBee64BitAddress(deviceID));
        devs.put(rdev,deviceID);
        rdev.readDeviceInfo();
    }

    @Override
    public Packet receive() {
        XBeeMessage msg = dev.readData();
        RemoteXBeeDevice d =msg.getDevice();
        String deviceID=devs.get(d);
        if(deviceID==null||deviceID.equals(""))
            return null;
        String payload = msg.getDataString();
        if(payload==null||payload=="")
            return null;
        String[] values = payload.split(regex);
        double[] doubleValues=new double[values.length];
        for(int i=0;i< values.length;i++)
            doubleValues[i]=Double.parseDouble(values[i]);
        return (new Packet(deviceID,doubleValues));
    }

}
