package it.unicam.quasylab.gpmonitor.monitor;

/**
 * Al fine di semplificare l'input da parte dell'utente finale, il {@link TimeInterval} costituisce la rappresentazione di un intervallo di tempo, utile per estrarre uno storico più mirato presso il {@link DataLog}.
 * Questo intervallo deve avere un inizio e una fine che rappresentano 2 momenti nel tempo (la fine deve essere successiva nel tempo rispetto all'inizio).
 */
public interface TimeInterval {
    /**
     * Restituisce l'inizio  dell'intervallo in formato UNIX_TIME_STAMP
     * @return inizio
     */
    long getstart();

    /**
     * Restituisce la fine  dell'intervallo in formato UNIX_TIME_STAMP
     * @return fine
     */
    long getStop();
}
