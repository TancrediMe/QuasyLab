package it.unicam.quasylab.gpmonitor.monitor;

/**
 * Rappresenta un {@link DataLogElement}, ovvero una riga del {@link DataLog}
 * contenente il valore di un {@link Device} e ha la responsabilità di sapere quando è stata creata.
 * La sua data di creazione verrà presa come riferimento del valore.
 */
public final class LogRow implements DataLogElement {
        private final long time;
        private final double [] value;
        private final Device device;

        public LogRow(long time, Device device, double... value) {
            this.time = time;
            this.device = device;
            this.value = value;
        }

        @Override
        public final long getTime() {
            return time;
        }

        @Override
        public final double[] getValue() {
            return value;
        }

    @Override
    public Device getDevice() {
        return device;
    }
}

