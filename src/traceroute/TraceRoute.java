package traceroute;

import java.io.*;
import java.lang.*;
import java.net.*;
import java.util.ArrayList;

public class TraceRoute {
    ArrayList<String> output = null;

    public ArrayList<String> start(String url){
        output = new ArrayList<String>();
        BufferedReader in = null;

        try{
            Runtime runtime = Runtime.getRuntime();
            Process process = runtime.exec("traceroute " + url);

            String line;
            in = new BufferedReader(new InputStreamReader(process.getInputStream()));

            if(in == null) System.out.println("Could not connect");


            while((line=in.readLine()) != null){
                output.add(line);
            }
            in.close();
        }catch (IOException e){

        }
        return  output;
    }

}
