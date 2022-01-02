package it.unicam.quasylab.gpmonitor.monitor;

import java.util.List;

/**
 * Le classi che implementano questa interfaccia istanziano oggetti che hanno il compito di rappresentare uno storico dei
 * valori letti dalla rete di sensori, diviso per dispositivo.
 * Quando un valore viene aggiunto allo storico, il {@link Filter} ha il compito di decidere se salvare il valore o scartarlo
 */
public interface DataLog {
    /**
     * Memorizza lo stato del {@link Device} inserito aggiungendolo allo storico
     * @param device il {@link Device} di riferimento
     */
    void add(Device device, double ... value);

    /**
     * Restituisce lo storico relativo a un {@link Device}
     * @param device il {@link Device} di riferimento
     * @return storico dei dati
     */
    List<DataLogElement> get(Device device);

    /**
     * Restituisce lo storico relativo a un {@link Device} entro un dato {@link TimeInterval}
     * @param device il {@link Device} di riferimento
     * @param interval il {@link TimeInterval} di riferimento
     * @return storico dei dati
     */
    List<DataLogElement> get(Device device, TimeInterval interval);

    /**
     * Cancella l'intero contenuto del {@link DataLog}
     */
    void clear();

    /**
     * Imposta il {@link Filter} che questo {@link DataCollector} andrà ad utilizzare
     * @param f {@link Filter}
     */
    void setFilter(Filter f);

    /**
     * Restituisce il {@link Filter} usato da questo {@link DataCollector}
     */
    Filter getFilter();
}
