package it.unicam.quasylab.gpmonitor.monitor;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DefaultTimeInterval implements TimeInterval{
    private final long start;
    private final long stop;

    public DefaultTimeInterval(String start, String stop) {
        try {
            this.start=  new SimpleDateFormat("dd/MM/yyyy").parse(start).getTime();
            this.stop=  new SimpleDateFormat("dd/MM/yyyy").parse(stop).getTime();
            if(this.start>=this.stop)
                throw new IllegalArgumentException("Start deve precedere Stop!");

        } catch (ParseException e) {
            throw new IllegalArgumentException("Formato data non riconosciuto");
        }
    }

    @Override
    public long getstart() {
        return start;
    }

    @Override
    public long getStop() {
        return stop;
    }
}
