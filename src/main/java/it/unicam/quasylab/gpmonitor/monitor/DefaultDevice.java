package it.unicam.quasylab.gpmonitor.monitor;

import java.util.Objects;

/**
 * Un {@link Device} generico che lascia all'implementazione il compito di definire
 * il tipo di dato all'interno del dispositivo e il valore neutro per quel tipo di dato.
 */
public class DefaultDevice implements Device{
    private final String name;
    private final String networkID;

    public DefaultDevice(String name, String networkID) {
        if(name==null || networkID==null) throw new NullPointerException("I dati del dispositivo sono stati impostati a @null!");
        this.name = name;
        this.networkID = networkID;
    }

    @Override
    public final String getName() {
        return name;
    }


    @Override
    public final String getNetworkID() {
        return networkID;
    }


    /**
     * 2 {@link Device} sono uguali se hanno lo stesso NetworkID
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DefaultDevice)) return false;
        DefaultDevice that = (DefaultDevice) o;
        return getNetworkID().equals(that.getNetworkID());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getNetworkID());
    }


}

