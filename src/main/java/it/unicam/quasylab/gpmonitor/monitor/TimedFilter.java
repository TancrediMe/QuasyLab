package it.unicam.quasylab.gpmonitor.monitor;

/**
 * Questo è un {@link DefaultFilter} che, oltre alle operazioni di filtraggio classiche,
 * accetta valori solo se sono stati rilevati in un determinato {@link TimeInterval}
 */
public class TimedFilter extends DefaultFilter{

    private final TimeInterval interval;

    public TimedFilter(String ID,FilterAggregationRule aggregationRule, FilterRule filterRule, double[] filtervalue, Device filterDevice, TimeInterval interval) {
        super(ID,aggregationRule, filterRule, filtervalue, filterDevice);
        this.interval = interval;
    }

    @Override
    public boolean filter(DataLogElement element) {
        if(!element.getDevice().equals(getDevice())) return true;
        if (super.filter(element))
        {
            switch(getRule()){
                case EQUALS:    if (element.getTime()>= interval.getstart() && element.getTime()<= interval.getstart() )  return true;
                case LOWER:     if (element.getTime()< interval.getStop())  return true;
                case HIGHER:    if (element.getTime()> interval.getStop())  return true;
            }
        }
        return false;
    }

}
