/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rxtx.project.one;

import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.scene.control.Label;

/**
 *
 * @author DELL
 */
public class Analogue_Updater extends Thread
{
    public int Bit_Decision = 0; //see UAV.java for this variable meaning. values will be 1,2,5,6,8,10.
    UAV UAV_Reference;
    Label UAV_SpeedLabel;
    Label LR_Label;
    public static int CurrentSpeed = 0;
    public static int CurrentLR = 50;
    public static boolean isSecureFlyActivated = false;
    RC_Controller RCC_Reference;
    
    Boolean AllReleased=true;
    public void set_UAVSpeedLabel(Label l)
    {
        UAV_SpeedLabel = l;
    }
    public void Set_RCC(RC_Controller R)
    {
        RCC_Reference = R;
    }
    public void set_LRLabel(Label l)
    {
        LR_Label = l;
    }
    public void set_UAVREF(UAV u)
    {
        UAV_Reference = u;
    }
    @Override
    public void run()
    {
        while(true)
        {
            try {
                Thread.sleep(15);
            } catch (InterruptedException ex) {
                Logger.getLogger(Analogue_Updater.class.getName()).log(Level.SEVERE, null, ex);
            }
            //System.out.println(UAV.BitDecision);
            if(UAV.BitDecision!=0)
            {
                AllReleased = false; //for the logic of 
                
                //System.out.print(CurrentSpeed);
                if(UAV.BitDecision==1 && CurrentSpeed<100)
                {
                    CurrentSpeed++;
                    //System.out.println(CurrentSpeed);
                  
                }
                else if(UAV.BitDecision==2 && CurrentSpeed>0)
                {
                    CurrentSpeed--;
                }
                else if(UAV.BitDecision==8 && CurrentLR<100)
                {
                    CurrentLR++;
                }
                else if(UAV.BitDecision==4 && CurrentLR>0)
                {
                    CurrentLR--;
                }
                
                //seperate combinational commands value calculating.
                if(UAV.BitDecision==9 && CurrentLR<100)
                {
                    CurrentLR++; 
                }
                if(UAV.BitDecision==9 && CurrentSpeed<100)
                {
                    CurrentSpeed++; 
                }
                if(UAV.BitDecision==10 && CurrentLR<100)
                {
                    CurrentLR++; 
                }
                if(UAV.BitDecision==10 && CurrentSpeed>0)
                {
                    CurrentSpeed--; 
                }
                if(UAV.BitDecision==5 && CurrentLR>0 )
                {
                    CurrentLR--;
                }
                if(UAV.BitDecision==5 && CurrentSpeed<100 )
                {
                    CurrentSpeed++;
                }
                if(UAV.BitDecision==6 && CurrentLR>0)
                {
                    CurrentLR--; 
                }
                if(UAV.BitDecision==6 && CurrentSpeed>0)
                {
                    CurrentSpeed--; 
                }
                
                FXMLDocumentController.balance = CurrentLR;
                FXMLDocumentController.throttle = CurrentSpeed;
                //System.out.println("Sending : "+UAV.BitDecision+","+CurrentSpeed+","+CurrentLR);
                RCC_Reference.sendCommand(10, UAV.BitDecision, CurrentSpeed, CurrentLR);
                Platform.runLater(new Runnable() 
                {
                    @Override
                    public void run() 
                    {
                        UAV_SpeedLabel.setText(""+CurrentSpeed);
                        LR_Label.setText(""+CurrentLR);
                        
                    }
                });
            }
            else if(AllReleased==false)
            {
                //System.out.println("Sending : "+UAV.BitDecision+","+CurrentSpeed+","+CurrentLR);
                RCC_Reference.sendCommand(10, 0, CurrentSpeed, CurrentLR);
                AllReleased = true;
            }
            else if(isSecureFlyActivated==true)
            {
                if(CurrentLR==50)
                {
                    isSecureFlyActivated=false;
                }
                if(CurrentLR>50)
                {
                    CurrentLR--;
                }
                else if(CurrentLR<50)
                {
                    CurrentLR++;
                }
                //System.out.println("Sending : "+UAV.BitDecision+","+CurrentSpeed+","+CurrentLR);
                RCC_Reference.sendCommand(10, UAV.BitDecision, CurrentSpeed, CurrentLR);
                Platform.runLater(new Runnable() 
                {
                    @Override
                    public void run() 
                    {
                        LR_Label.setText(""+CurrentLR);                       
                    }
                });
            }
        }
    }
}
