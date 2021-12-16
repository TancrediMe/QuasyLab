package it.unicam.quasylab.gpmonitor;

/**
 * Questo è un {@link DefaultFilter} che, oltre alle operazioni di filtraggio classiche,
 * accetta valori solo se sono stati rilevati in un determinato {@link TimeInterval}
 */
public class TimedFilter extends DefaultFilter{

    private final TimeInterval interval;

    public TimedFilter(FilterAggregationRule aggregationRule, FilterRule filterRule, double[] filtervalue, Device filterDevice, TimeInterval interval) {
        super(aggregationRule, filterRule, filtervalue, filterDevice);
        this.interval = interval;
    }

    @Override
    public boolean filter(DataLogElement element) {
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
