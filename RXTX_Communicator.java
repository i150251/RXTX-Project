/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rxtx.project.one;

import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;

/**
 *
 * @author DELL
 */
public class RXTX_Communicator implements SerialPortEventListener
{
    
    private static final int TIME_OUT = 2000; // Milliseconds to block while waiting for port open
    private static final int DATA_RATE = 9600; // Default bits per second for COM port.
    private InputStream input; // Buffered input stream from the port   
    private OutputStream output; // The output stream to the port
    CommPortIdentifier portId = null;
    SerialPort serialPort;
    
    public void RXTX_Communicator()
    {
        
    }
    public String connect(String portName)
    {
        
        Enumeration<?> portEnum;
        
        try 
        {
            portEnum = CommPortIdentifier.getPortIdentifiers();
        } 
        catch (Exception e) 
        {
            return e.getMessage();                          
        }

        // Iterate through list of serial ports.  Only continue if there is a match.
        while (portEnum.hasMoreElements()) 
        {
            CommPortIdentifier currPortId = (CommPortIdentifier) portEnum.nextElement();
            if (currPortId.getName().equals(portName)) 
            {
                portId = currPortId;
                System.out.println(portId.getName());
                break;
            }
        }

        if (portId == null) 
        {
            return "Could not find COM port.";
        }

        try 
        {            
            close(); // Close port if it was previously open 
            
            serialPort = (SerialPort) portId.open(this.getClass().getName(), TIME_OUT); // open serial port, and use class name for the appName.
            
            serialPort.setSerialPortParams(DATA_RATE, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE); // set port parameters
            // open the streams
            input = serialPort.getInputStream();
            output = serialPort.getOutputStream();

            // add event listeners
            serialPort.addEventListener(this);
            serialPort.notifyOnDataAvailable(true);
        } 
        catch (Exception e) 
        {
            // Return an error message if port can't be opened
            return e.toString();
        }
        return null; // No errors
    }
    
    @Override
    public void serialEvent(SerialPortEvent oEvent) 
    {
        if (oEvent.getEventType() == SerialPortEvent.DATA_AVAILABLE) 
        {
            // Write data to System.out
            try 
            {
                int available = input.available();
                byte chunk[] = new byte[available];
                input.read(chunk, 0, available);
                System.out.print(new String(chunk));
            } 
            catch (Exception e) 
            {
                System.err.println(e.toString());
            }
        }
        // Ignore all the other eventTypes, but you could consider the other ones..
    }
    public synchronized void sendData(byte[] array)
    {
        if (array != null && output != null) 
        {
            try 
            {
                output.write(array);
            } 
            catch (Exception e) 
            {
                e.printStackTrace();
            }
        }
    }
    public synchronized void close() 
    {
        if (serialPort != null) 
        {
            serialPort.removeEventListener();
            serialPort.close();
        }
    }
}
