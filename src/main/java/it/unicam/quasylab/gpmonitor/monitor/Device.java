package it.unicam.quasylab.gpmonitor.monitor;

/**
 * Le classi che implementano questa interfaccia istanziano oggetti che rappresentano dispositivi fisici all'interno della rete di sensori
 * Ogni {@link Device} è un sensore.
 */
public interface Device {
    /**
     * Il Name del {@link Device} è una stringa che rappresenta tutte le informazioni utili per riconoscere il dispositivo nel file di Log finale.
     * @return Name
     */
    String getName();


    /**
     * Restituisce le informazioni identificative che potrebbero essere necessarie ad identificare il dispositivo in base
     * alle informazioni ricevute dalla rete.
     * Gli oggetti che avranno la responsabilità di aggiornare un {@link Device } in base ai valori ottenuti avranno bisogno di questa informazione
     * @return Informazioni di rete
     */
    String getNetworkID();
}

