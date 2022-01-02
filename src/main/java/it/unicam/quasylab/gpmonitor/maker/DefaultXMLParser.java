package it.unicam.quasylab.gpmonitor.maker;

import it.unicam.quasylab.gpmonitor.monitor.*;
import it.unicam.quasylab.gpmonitor.sensornetworks.MQTTNetwork;
import it.unicam.quasylab.gpmonitor.sensornetworks.NetworkEmulator;
import it.unicam.quasylab.gpmonitor.sensornetworks.ZigBeeNetwork;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import static it.unicam.quasylab.gpmonitor.monitor.FilterAggregationRule.AND;
import static it.unicam.quasylab.gpmonitor.monitor.FilterAggregationRule.OR;


public class DefaultXMLParser extends DefaultParser{
    Set<Device> devices;
    HashMap<String, Filter> filtersMap;
    String fileContent;

    public DefaultXMLParser(String filename) throws IOException {
        super(filename);
        this.devices = new HashSet<>();
        this.filtersMap = new HashMap<>();
        BufferedReader br = new BufferedReader(new FileReader(getFileName()));
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
        fileContent = sb.toString();
    }

    @Override
    protected Network parseNetwork() {
        String networkBlock=parseTAG("Network", fileContent);
        String NetworkType=parseTAG("Type",networkBlock);
        switch (NetworkType){
            case "Emu":
                return new NetworkEmulator();
            case "MQTT":
                return new MQTTNetwork();
            case"ZigBee":
               return new ZigBeeNetwork();
        }
        return null;
    }

    @Override
    protected String[] parseNetworkInitParameters() {
        return parseMultiTag("param",parseTAG("init",parseTAG("Network", fileContent)));
    }

    @Override
    protected Collection<Device> parseDevices() {
        String[] devicesBlocks = parseMultiTag("Device", fileContent);
        for (String devicesBlock : devicesBlocks) {
            devices.add(new DefaultDevice(parseTAG("Name", devicesBlock), parseTAG("NetID", devicesBlock)));
        }
        return devices;
    }

    @Override
    protected Filter parseFilters() {

        String[] filtersBlocks = parseMultiTag("Filter", fileContent);
        for(String filterBlock:filtersBlocks) {
            String id = parseTAG("ID", filterBlock);
            String[] compositeValue=parseTAG("filterValue", filterBlock).split(",");
            double [] filterValue=new double[compositeValue.length];
            for(int i=0;i<compositeValue.length;i++)
                filterValue[i]=Double.parseDouble(compositeValue[i]);
            Device filterDevice=null;
            for(Device d : devices)
                if(d.getName().equals(parseTAG("FilterDevice", filterBlock)))
                    filterDevice=d;
            String father="";
            try{  father=parseTAG("father", filterBlock);   }catch (Exception e){}
            String aggregationRule = parseTAG("aggregationRule", filterBlock);
            FilterAggregationRule ar;
            switch(aggregationRule){
                case "AND":ar=AND;break;
                case "OR":ar=OR;break;
                default:throw new IllegalArgumentException("REGOLA FILTRO NON RICONOSCIUTA!");
            }
            String filterRule=parseTAG("filterRule", filterBlock);
            FilterRule r=null;
            switch (filterRule) {
                case "HIGHER":r=FilterRule.HIGHER;break;
                case "LOWER":r=FilterRule.LOWER;break;
                case "EQUALS":r=FilterRule.EQUALS;break;
                default:throw new IllegalArgumentException("REGOLA FILTRO NON RICONOSCIUTA!");
            }
            Filter f;
            if(filterBlock.contains("TimeInterval"))
            {
                String timeBlock= parseTAG("TimeInterval", filterBlock);
                String start= parseTAG("start", timeBlock);
                String end= parseTAG("end", timeBlock);
                TimeInterval time = new DefaultTimeInterval(start,end);
                f=new TimedFilter(id,ar,r,filterValue,filterDevice,time);
            }
            else{
                f=new DefaultFilter(id,ar,r,filterValue,filterDevice);
            }
            filtersMap.put(father,f);
        }
        for(Map.Entry<String,Filter> e:filtersMap.entrySet())
        {
            if(!e.getKey().equals(""))
                for(Filter f:filtersMap.values())
                    if(f.getID().equals(e.getKey()))
                        f.add(e.getValue());
        }
        return filtersMap.get("");
    }

    @Override
    protected DataCollector buildDataCollector(Network net, DataLog d, String... networkParameters) {
        return new DefaultDataCollector(net,d,networkParameters);
    }

    @Override
    protected DataLog buildDataLog() {
        return new DefaultDataLog();
    }

    private String parseTAG(String tag, String text){
        String result = text.split("</"+tag+">")[0];
        result = result.split("<"+tag+">")[1];
        return result;
    }

    private String[] parseMultiTag(String tag, String text){
        String[] result =text.split("<"+tag+">");
        for(int i=0; i<result.length;i++)
            result[i]=result[i].split("</"+tag+">")[0];
        String finalResult[]=new String[result.length-1];
        for(int i=1; i<result.length;i++)
            finalResult[i-1]=result[i];
        return finalResult;
    }
}
