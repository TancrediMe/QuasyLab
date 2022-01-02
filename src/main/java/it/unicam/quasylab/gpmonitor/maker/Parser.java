package it.unicam.quasylab.gpmonitor.maker;

import it.unicam.quasylab.gpmonitor.monitor.DataCollector;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Legge un file nel quale sono salvate le informazioni su come istanziare un {@link DataCollector}
 */
public interface Parser {
    /**
     * Restituisce un {@link DataCollector} costruito in base alle informazioni lette da un file
     * @return {@link DataCollector}
     */
    DataCollector parse() throws IOException;

    /**
     * Restituisce il nome del file da cui le informazioni vengono lette.
     * @return filename
     */
    String getFileName();
}
