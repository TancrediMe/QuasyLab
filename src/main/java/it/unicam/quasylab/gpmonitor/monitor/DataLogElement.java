package it.unicam.quasylab.gpmonitor.monitor;

/**
 * Un {@link DataLogElement} è una riga del {@link DataLog}
 */
public interface DataLogElement {
    /**
     * Restituisce il momento in cui è stato letto il valore in formato UNIX_TIME_STAMP
     *
     * @return momento della lettura
     */
    long getTime();

    /**
     * Restituisce il valore
     *
     * @return value
     */
    double[] getValue();

    /**
     * Restituisce il {@link Device} di riferimento dei valori inseriti
     *
     * @return {@link Device}
     */
    Device getDevice();
}
