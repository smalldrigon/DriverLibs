package com.grgbanking.huitong.driver_libs.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;

public class RuntimeStream extends Thread{
	InputStream inputStream;  
    String type;  
    OutputStream outStream;  
          
    RuntimeStream(InputStream is, String type) {  
        this(is, type, null);  
    }  
  
    RuntimeStream(InputStream is, String type, OutputStream redirect) {  
        this.inputStream = is;  
        this.type = type;  
        this.outStream = redirect;  
    }  
      
    public void run() {  
        InputStreamReader isr = null;  
        BufferedReader br = null;  
        PrintWriter pw = null;  
        try {  
            if (outStream != null)  
                pw = new PrintWriter(outStream);  
                  
            isr = new InputStreamReader(inputStream);  
            br = new BufferedReader(isr);  
            String line=null;  
            while ( (line = br.readLine()) != null) {  
                if (pw != null)  
                    pw.println(line);  
            }  
              
            if (pw != null)  
                pw.flush();  
        } catch (IOException ioe) {  
            ioe.printStackTrace();    
        } finally{  
        	
        	try {

        		if(isr != null)
        		{
        			isr.close();
        		}
				
        		if(br != null)
        		{
        			br.close();
        		}
				
        		if(pw != null)
        		{
        			pw.close(); 
        		}
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	
        }  
    }  
}
