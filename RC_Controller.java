/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rxtx.project.one;

import javafx.scene.control.TextField;

/**
 *
 * @author DELL
 */
public class RC_Controller 
{
    public int command;
    public int throttle;
    public int balance;
    RXTX_Communicator RXTXCom;
    private final byte DRIVE_CMD = 10;
    public String portFieldValue;
    public void setPortField(String TF)
    {
        portFieldValue = TF;
    }
    
    public RXTX_Communicator Return_RXTXComm()
    {
        return RXTXCom;
    }
    public RC_Controller()
    {
        RXTXCom = new RXTX_Communicator();
    }
     public String connect() 
    {
        return RXTXCom.connect(getPortName());
    }
    public String getPortName() 
    {
        return portFieldValue;
    }
    
    public void sendCommand(int commandId, int data1, int data2, int data3) 
    {
        // Command is of format ID, DATA1, DATA2,DATA3, CHECKSUM
        System.out.println("Sending : "+data1);
        byte CommandId,Data1,Data2,Data3;
        
        CommandId = (byte)(10);
        Data1 = (byte)(data1);
        Data2 = (byte)(100);
        //Data3 = (byte)(data3);
        byte[] byteArray = new byte[4];
        byteArray[0] = CommandId;
        byteArray[1] = Data1;
        byteArray[2] = Data2;
        //byteArray[3] = Data3;
        byteArray[3] = (byte) (CommandId + Data1 + Data2 );
        RXTXCom.sendData(byteArray);
    }
    
     // Send drive command to serial port, speed specified
    public synchronized void sendDirectionCommand(byte keyState, byte Throttle_Speed, byte LR_Speed) 
    {
        sendCommand(DRIVE_CMD, keyState, Throttle_Speed,LR_Speed);
        System.out.println("Sending Command: " + keyState);
    }
}
