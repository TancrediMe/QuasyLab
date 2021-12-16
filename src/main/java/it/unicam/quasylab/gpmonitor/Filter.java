package it.unicam.quasylab.gpmonitor;

/**
 * Un {@link Filter} è un oggetto che ha il compito di filtrare un valore ricevuto da un {@link Device} in base ad
 * un valore di riferimento per quel {@link Device} e ad una {@link FilterRule}
 * In caso di esito positivo dell'azione di filtraggio inserirà i dati in un {@link DataLog}.
 * Un {@link Filter} può essere composto solo da se stesso o da più {@link Filter} che, in base al criterio
 * stabilito da una {@link FilterAggregationRule}, vengono richiamati o meno per filtrare il valore ricevuto
 */
public interface Filter {
    /**
     * Filtra il valore del {@link Device} ricevuto.
     * Se il {@link Device} ricevuto ha uno stato che supera i controlli del {@link Filter}
     * viene salvato nel {@link DataLog}.
     * @param device il device da filtrare
     * @return {@code true} se è idoneo al salvataggio
     */
    boolean filter(DataLogElement element);

    /**
     * Aggiunge un altro {@link Filter} a questo {@link Filter}
     * @param filter il {@link Filter} da aggiungere
     * @throws IllegalArgumentException se il filtro inserito è il filtro stesso.
     *                                  Questa regola viene aggiunta per non generare un loop infinito
     *                                  in fase di filtraggio
     *
     */
    void add(Filter filter);

    /**
     * Rimuove un {@link Filter} da questo {@link Filter}
     * @param filter il {@link Filter} da rimuovere
     */
    void remove(Filter filter);

    /**
     * Restituisce la {@link FilterAggregationRule} relativa a questo {@link Filter}
     */
    FilterAggregationRule getFilterAggregationRule();

    /**
     * Restituisce la regola di filtraggio in base alla quale i valori superano o meno il controllo
     * @return {@link FilterRule}
     */
    FilterRule getRule();

    /**
     * Restituisce il {@link Device} relativo a questo {@link Filter}
     * @return {@link Device}
     */
    Device getDevice();

    /**
     * Restituisce il valore relativo a questo {@link Filter}
     * @return {@code double}
     */
    double[] getValue();

}
