package traceroute;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.io.*;
import java.lang.*;
import java.net.*;
import java.util.ArrayList;

public class TraceRoute {
    private final String os = System.getProperty("os.name").toLowerCase();

    private ArrayList<String> output = null;

    private SimpleIntegerProperty hop;
    private SimpleStringProperty ip;
    private SimpleStringProperty hostname;
    private SimpleDoubleProperty time;

    public TraceRoute(){}

    public TraceRoute(int hop, String ip, String hostname, double time){
        this.hop = new SimpleIntegerProperty(hop);
        this.ip = new SimpleStringProperty(ip);
        this.hostname = new SimpleStringProperty(hostname);
        this.time = new SimpleDoubleProperty(time);
    }

    public void setHop(int hop){ this.hop.set(hop); }

    public void setIp(String ip){ this.ip.set(ip); }

    public void setHostname(String hostname){ this.hostname.set(hostname); }
    
    public void setTime(double time){ this.time.set(time); }

    public int getHop(){ return this.hop.get(); }

    public String getIp(){ return this.ip.get(); }

    public String getHostname(){ return this.hostname.get(); }
    
    public double getTime(){ return this.time.get(); }

    public ArrayList<String> start(String url){
        output = new ArrayList<String>();
        BufferedReader in = null;

        try{
            Runtime runtime = Runtime.getRuntime();
            Process process;
            if(os.contains("win")) process = runtime.exec("tracert " + url + " -m 99");
            else  process = runtime.exec("traceroute " + url + " -m 99");

            String line;
            in = new BufferedReader(new InputStreamReader(process.getInputStream()));

            if(in == null) System.out.println("Could not connect");

            while((line=in.readLine()) != null){
                output.add(line);
            }
            in.close();
        }catch (IOException e){
            System.out.println(e.toString());
        }
        return  output;
    }

}
