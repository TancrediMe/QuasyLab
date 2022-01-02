package it.unicam.quasylab.gpmonitor.sensornetworks;

import it.unicam.quasylab.gpmonitor.monitor.Network;
import it.unicam.quasylab.gpmonitor.monitor.Packet;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class MQTTNetwork implements Network {
    Queue<Packet> packets;
    MqttClient client;
    String regex;
    public MQTTNetwork() {
        this.packets = new LinkedList<>();
    }

    @Override
    public void init(String... parameters) throws MqttException {
        MemoryPersistence persistence = new MemoryPersistence();
            client=new MqttClient(parameters[0],parameters[1],persistence);
        MqttConnectOptions connOpts = new MqttConnectOptions();
        connOpts.setUserName(parameters[2]);
        connOpts.setPassword(parameters[3].toCharArray());
        connOpts.setCleanSession(true);
        regex=parameters[4];
        client.setCallback(new MqttCallback() {
            @Override
            public void connectionLost(Throwable throwable) {
                throw new IllegalStateException("Disconnesso!");
            }

            @Override
            public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {
                String msg = Arrays.toString(mqttMessage.getPayload());
                String[] values = msg.split(regex);
                double[] doubleValues=new double[values.length];
                for(int i=0;i< values.length;i++)
                    doubleValues[i]=Double.parseDouble(values[i]);
                packets.add(new Packet(s,doubleValues));
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

            }
        });
        client.connect(connOpts);
    }

    @Override
    public void subscribe(String deviceID) throws MqttException {
        client.subscribe(deviceID);
    }

    @Override
    public Packet receive() {
        return packets.poll();
    }

}
