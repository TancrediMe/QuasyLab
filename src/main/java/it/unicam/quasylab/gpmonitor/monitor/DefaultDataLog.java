package it.unicam.quasylab.gpmonitor.monitor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Questa classe rappresenta un {@link DataLog} per un certo tipo di dati
 */
public class DefaultDataLog implements DataLog {


    private final Map<Device,List<DataLogElement>> deviceListmap;
    private Filter filter;
    public DefaultDataLog() {
        this.deviceListmap = new HashMap<>();
    }

    @Override
    public final void add(Device device, double ... value) {
        if(!deviceListmap.containsKey(device))
            deviceListmap.put(device, new ArrayList<>());
        LogRow lr=new LogRow(System.currentTimeMillis(), device, value);
        if(filter==null||filter.filter(lr))
            deviceListmap.get(device).add(lr);
    }

    @Override
    public final List<DataLogElement> get(Device device) {
        return deviceListmap.get(device);
    }

    @Override
    public final List<DataLogElement> get(Device device, TimeInterval interval) {
        List<DataLogElement> devlog=deviceListmap.get(device);
        List<DataLogElement> updated=new ArrayList<>();
        for(DataLogElement row:devlog)
            if(row.getTime()>= interval.getstart()&&row.getTime()<= interval.getStop())
                updated.add(row);
        return updated;
    }

    @Override
    public final void clear() {
        deviceListmap.clear();
    }


    @Override
    public void setFilter(Filter f) {
        filter=f;
    }

    @Override
    public Filter getFilter() {
        return filter;
    }


}
