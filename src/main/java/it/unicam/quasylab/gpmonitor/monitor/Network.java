package it.unicam.quasylab.gpmonitor.monitor;


/**
 * Gli oggetti di questa interfaccia implementano una connseeione verso una rete di sensori.
 * Contiene tutti i metodi per inizializzare una connessione,
 * notificare alla rete che si vogliono ottenere dati per un certo sensore
 * e leggere i dati trasmessi dalla rete.
 * Il compito degli oggetti che implementano questa interfaccia è quello di definire i metodi che si occupano della
 * comunicazione di rete. *
 * La {@link Network} deve quindi conoscere il protocollo trasmissivo della rete e il formato dei dati che può ricevere da essa.
 */
public interface Network {

    /**
     * Inizializza la connessione con la rete di sensori in base ai parametri forniti
     * @param parameters parametri di inizializzazione della rete.
     */
    void init(String ... parameters) throws Exception;

    /**
     * Notifica alla rete la volontà del sistema di ricevere i dati per un certo dispositivo.
     * @param deviceID ID del dispositivo.
     */
    void subscribe(String deviceID) throws Exception;

    /**
     * Riceve un dato dalla rete e lo incapsula in un {@link Packet}.
     * Questo metodo viene eseguito a ciclo continuo per verificare se ci sono nuovi dati.
     * @return {@link Packet} creato, {@code null} se non ci sono nuovi dati.
     */
    Packet receive();
}
