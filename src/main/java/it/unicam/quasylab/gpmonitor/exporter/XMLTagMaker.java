package it.unicam.quasylab.gpmonitor.exporter;

import java.util.Stack;

public class XMLTagMaker {
    Stack<String> waitingTags;
    StringBuilder exportTotal;

    public XMLTagMaker() {
        waitingTags=new Stack<>();
        exportTotal=new StringBuilder();
    }
    public void openTag(String TAG){
        exportTotal.append("<").append(TAG).append(">");
        waitingTags.add("</"+TAG+">");
    }
    public void closeTag(){
        if(!waitingTags.isEmpty())
        exportTotal.append(waitingTags.pop());
    }
    public void write(String value){
        exportTotal.append(value);
    }
    public String getText(){
        while(!waitingTags.isEmpty())
            closeTag();
        return exportTotal.toString();
    }
}
