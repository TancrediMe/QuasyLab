package it.unicam.quasylab.gpmonitor.exporter;

import it.unicam.quasylab.gpmonitor.monitor.DataCollector;
import it.unicam.quasylab.gpmonitor.monitor.DataLogElement;
import it.unicam.quasylab.gpmonitor.monitor.Device;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class XMLExporter {
    private DataCollector dataCollector;
    private XMLTagMaker maker;

    public XMLExporter(DataCollector dataCollector) {
        this.dataCollector = dataCollector;
        maker=new XMLTagMaker();
    }

    public void export(String fileName){
        maker.openTag("Export");
        for(Device dev:dataCollector.getDevices())
        {
            maker.openTag("Device");
            maker.openTag("Name");
            maker.write(dev.getName());
            maker.closeTag();
            maker.openTag("Values");
            for(DataLogElement e : dataCollector.getDataLog().get(dev))
            {
                maker.openTag("v");
                maker.openTag("Time");
                GregorianCalendar time = new GregorianCalendar();
                time.setTimeInMillis(e.getTime());
                maker.write(
                        time.get(Calendar.DAY_OF_MONTH)+"/"+
                                time.get(Calendar.MONTH)+"/"+
                                time.get(Calendar.YEAR)+" "+
                                time.get(Calendar.HOUR_OF_DAY)+":"+
                                time.get(Calendar.MINUTE)+":"+
                                time.get(Calendar.SECOND)+","+
                                time.get(Calendar.MILLISECOND)
                );
                maker.closeTag();
                maker.openTag("Value");
                for(int i=0;i<e.getValue().length;i++)
                {
                    if(i>0)
                        maker.write(","+e.getValue()[i]);
                    else
                        maker.write(Double.toString(e.getValue()[i]));
                }
                maker.closeTag();
                maker.closeTag();
            }
            maker.closeTag();
            maker.closeTag();
        }
        maker.closeTag();
        try {
            save(maker.getText(),fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private static void save(String text, String fileName) throws IOException {
        PrintWriter writer = new PrintWriter(fileName, StandardCharsets.UTF_8);
        writer.print(text);
        writer.close();
    }

}
