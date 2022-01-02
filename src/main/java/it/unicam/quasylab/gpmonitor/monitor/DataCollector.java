package it.unicam.quasylab.gpmonitor.monitor;

import java.util.Collection;

/**
 * Gli oggetti di tipo {@link DataCollector} hanno il compito di comunicare con la rete fisica di sensori per leggere i valori che essi trasmettono.
 * In base alle informazioni ricevute dalla {@link Network}, aggiorna il {@link DataLog} con i dati relativi al {@link Device} corrispondente
 */
public interface DataCollector {
    /**
     * Avvia la lettura dei valori dalla rete in un processo indipendente dall'esecuzione del resto del programma
     */
    void startListening();

    /**
     * Ferma la lettura dei valori dalla rete
     */
    void stopListening();
    /**
     * Restituisce la lista completa dei {@link Device}
     * @return {@link Collection} di {@link Device}
     */
    Collection<Device> getDevices();

    /**
     * Aggiunge un {@link Device}
     */
    void add(Device d) ;

    /**
     * Rimuove un {@link Device}
     * @param d
     */
    void remove (Device d);

    /**
     * Restituisce il {@link DataLog} nel quale questo {@link DataCollector} salva i valori ricevuti dalla rete
     * @return
     */
    DataLog getDataLog ();



}
