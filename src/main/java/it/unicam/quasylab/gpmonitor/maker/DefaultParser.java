package it.unicam.quasylab.gpmonitor.maker;

import it.unicam.quasylab.gpmonitor.monitor.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collection;

public abstract class DefaultParser implements Parser{
    private String fileName;

    public DefaultParser(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public DataCollector parse(){
        try {
            Collection<Device> devices = parseDevices();
            DataCollector collector = buildDataCollector(parseNetwork(), buildDataLog(), parseNetworkInitParameters());
            for (Device d : devices)
                collector.add(d);
            collector.getDataLog().setFilter(parseFilters());
            return collector;
        }
        catch (Exception e)  {e.printStackTrace();}
        return null;
    }

    @Override
    public String getFileName() {
        return fileName;
    }

    /**
     * Recupera dal file tutte le informazioni relative alla {@link Network}
     * @return {@link Network}
     */
    protected abstract Network parseNetwork() throws ParserConfigurationException, IOException, SAXException;

    /**
     * Recupera dal file i parametri di inizializzazione della rete
     * @return init params
     */
    protected abstract String[] parseNetworkInitParameters();

    /**
     * Recupera dal file tutte le informazioni relative ai {@link Device}
     * @return {@link Device}
     */
    protected abstract Collection<Device> parseDevices();

    /**
     * Recupera dal file tutte le informazioni relative ai {@link Filter}
     * @return {@link Filter}
     */
    protected abstract Filter parseFilters();


    /**
     * Istanzia un {@link DataCollector}
     * @param net {@link Network} su cui opera
     * @param d {@link DataLog} sul quale salva i valori in lettura
     * @param networkParameters {@link String} parametri di inizializzazione rete
     * @return {@link DataCollector}
     */
    protected abstract DataCollector buildDataCollector(Network net, DataLog d, String ... networkParameters);

    /**
     *  Istanzia un {@link DataLog} vuoto
     * @return {@link DataLog}
     */
    protected abstract DataLog buildDataLog();

}
