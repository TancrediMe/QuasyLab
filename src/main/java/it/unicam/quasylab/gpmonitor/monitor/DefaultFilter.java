package it.unicam.quasylab.gpmonitor.monitor;

import java.util.Collection;
import java.util.HashSet;

public class DefaultFilter implements Filter {
    private final Collection<Filter> filters;
    private final FilterAggregationRule aggregationRule;
    private final FilterRule filterRule;
    private final double[] filtervalue;
    private final Device filterDevice;
    private final String ID;

    public DefaultFilter(String ID,FilterAggregationRule aggregationRule, FilterRule filterRule, double[] filtervalue, Device filterDevice) {
        this.aggregationRule = aggregationRule;
        this.filterRule = filterRule;
        this.filtervalue = filtervalue;
        this.filterDevice = filterDevice;
        filters = new HashSet<>();
        this.ID = ID;
    }

    @Override
    public boolean filter(DataLogElement element) {
        double [] value = element.getValue();
        Device device=element.getDevice();
        boolean result=true;
        if(value.length!=getValue().length)
            return false;
        if(device.equals(filterDevice)) {
            for (int i = 0; i < value.length; i++)
                if (result)
                    switch (getRule()) {
                        case HIGHER:
                            result = value[i] > getValue()[i];
                            break;
                        case LOWER:
                            result = value[i] < getValue()[i];
                            break;
                        case EQUALS:
                            result = value[i] == getValue()[i];
                            break;
                    }
            switch (getFilterAggregationRule()) {
                case OR:
                    if (result)
                        return true;
                    else if (filters.isEmpty())
                        return false;
                    break;
                case AND:
                    if (!result)
                        return false;
                    else if (filters.isEmpty())
                        return true;
                    break;
            }
        }
        else return true;

        for(Filter f:filters)
            switch (getFilterAggregationRule())
            {
                case OR:
                    if(f.filter(element))
                        return true;
                    break;
                case AND:
                    if (!f.filter(element))
                        return false;
                    break;
            }
        return result;
    }

    @Override
    public String getID() {
        return ID;
    }

    @Override
    public void add(Filter filter) {
        if(filter!=this)
            filters.add(filter);
        else throw new IllegalArgumentException("Non puoi aggiungere un filtro a sé stesso!");
    }

    public void add(Filter ... filters)
    {
        for(Filter f:filters)
            add(f);
    }

    @Override
    public void remove(Filter filter) {
        for(Filter f:filters)
            f.remove(filter);
        filters.remove(filter);
    }

    @Override
    public FilterAggregationRule getFilterAggregationRule() {
        return aggregationRule;
    }

    @Override
    public FilterRule getRule() {
        return filterRule;
    }

    @Override
    public Device getDevice() {
        return filterDevice;
    }
    @Override
    public double[] getValue() {
        return filtervalue;
    }

    @Override
    public Collection<Filter> getSubFilters() {
        return filters;
    }

}
