package it.unicam.quasylab.gpmonitor;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;

public class DefaultFilter implements Filter {
    private final Collection<Filter> filters;
    private final FilterAggregationRule aggregationRule;
    private final FilterRule filterRule;
    private final double[] filtervalue;
    private final Device filterDevice;

    public DefaultFilter(FilterAggregationRule aggregationRule, FilterRule filterRule, double[] filtervalue, Device filterDevice) {
        this.aggregationRule = aggregationRule;
        this.filterRule = filterRule;
        this.filtervalue = filtervalue;
        this.filterDevice = filterDevice;
        filters = new HashSet<>();
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
                    switch (filterRule) {
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
            switch (aggregationRule) {
                case OR:
                    if (result)
                        return true;
                    else if (filters.isEmpty())
                        return false;
                case AND:
                    if (!result)
                        return false;
                    else if (filters.isEmpty())
                        return true;
            }
        }
        for(Filter f:filters)
            switch (aggregationRule)
            {
                case OR:
                    if(f.filter(element))
                        return true;
                    else result=false;
                case AND:
                    if (!f.filter(element))
                        return false;
                    else result=true;
            }
        return result;
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

}
