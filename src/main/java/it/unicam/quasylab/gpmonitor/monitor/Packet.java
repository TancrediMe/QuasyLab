package it.unicam.quasylab.gpmonitor.monitor;

/**
 * Il {@link Packet} rappresenta un pacchetto contenente le informazioni ricevute dalla rete.
 */
public final class Packet {
    private final String id;
    private final double[] value;


    public Packet(String id, double ... value) {
        this.id = id;
        this.value = value;
    }

    /**
     * Informazioni che identificano il dispositivo sulla rete.
     * @return {@link String}
     */
    public final String getId() {
        return id;
    }

    /**
     * Valore del dispositivo letto dalla rete.
     * @return valore.
     */
    public final double[]  getValue() {
        return value;
    }

}
