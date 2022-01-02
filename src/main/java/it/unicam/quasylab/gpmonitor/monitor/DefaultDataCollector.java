package it.unicam.quasylab.gpmonitor.monitor;


import java.util.Collection;
import java.util.HashSet;

/**
 * Il {@link DefaultDataCollector} è un {@link DataCollector} che lascia all'implementazione la possibilità di definire
 * il tipo di dati, il tipo di rete e il {@link Thread} di lettura (in base alla tipologia di rete implementata
 */
public class DefaultDataCollector implements DataCollector{
    private boolean read;
    private final Network network;
    private final Thread listener;
    protected Collection<Device> devices;
    private final DataLog datalog;

    public DefaultDataCollector(Network net, DataLog d, String ... networkParameters) {
        read=true;
        this.network = net;
        try {
            network.init(networkParameters);
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.listener =new Thread(this::listenThread);
        this.devices = new HashSet<>();
        this.datalog=d;
    }

    @Override
    public final void startListening() {
        read=true;
        listener.start();
    }

    @Override
    public void stopListening() {
        read=false;
    }

    @Override
    public Collection<Device> getDevices() {
        return devices;
    }

    @Override
    public void add(Device d) {
        subscribe(d);
        devices.add(d);
    }

    @Override
    public void remove(Device d) {
        devices.remove(d);
    }

    @Override
    public DataLog getDataLog() {
        return datalog;
    }



    /**
     * Implementare il thread di ascolto in base alla tipologia di rete utilizzata.
     * Al momento della lettura del valore richiamare -> {@code read(NetworkID,value);}
     */
    private void listenThread(){
        while (read) {
            read(network.receive());
        }
    }

    /**
     * Riceve un {@link Packet} dalla rete
     * @param p {@link Packet} ricevuto dalla rete
     */
    protected final void read(Packet p) {
        if(p==null) return;
        String networkID=p.getId();
        double [] value = p.getValue();
        Device d=null;
        for(Device dev:devices)
            if(dev.getNetworkID().equals(networkID))
                d=dev;
        if(d==null)
            return;
        datalog.add(d,value);
    }
    private void subscribe(Device device)
    {
        try {
            network.subscribe(device.getNetworkID());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
