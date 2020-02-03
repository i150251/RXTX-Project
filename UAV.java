/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rxtx.project.one;

import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
/**
 *
 * @author DELL
 */
public class UAV 
{
    public static int BitDecision;
    // variables showing the numbers assigned to actions.
    public int throttle_increase;
    public int throttle_decrease;
    public int left;
    public int right;
    public int CURRENT_COMMAND; // Current command for Byte Value.
    public static int CURRENT_SPEED; //Current Throttle speed.
    public static int CURRENT_LR; //variable for maintaining left right position.
    
    //booleans for checking CURRENT_COMMAND received a action first time and setting to true.
    public boolean TI;
    public boolean TD;
    public boolean L;
    public boolean R;
    
    Analogue_Updater AU_Reference;
    
    //command given label in GUI.
    public static Label CurrentCommand;
    public UAV()
    {
        BitDecision = 0;
        throttle_decrease = 2;
        throttle_increase = 1;
        left = 4;
        right = 8;
        CURRENT_COMMAND = 0;
        CURRENT_SPEED = 0;
        CURRENT_LR = 50; // from 50 to 0 is left and from 50 to 100 is right.
    }
 
    public void Set_Analogue(Analogue_Updater AU)
    {
        AU_Reference = AU;
    }
    public void setCurrentCommandGUILabel(Label L)
    {
        CurrentCommand = L;
    }
    public int get_CurrentCommand()
    {
        return CURRENT_COMMAND;
    }
    public void releasedDecrement_CurrentCommand(int c)
    {
        if(c==1)
        {
            TI=false;
            CURRENT_COMMAND-=c;
        }
        else if(c==2)
        {
            TD=false;
            CURRENT_COMMAND-=c;
        }
        else if(c==4)
        {
            L=false;
            CURRENT_COMMAND-=c;
        }
        else if(c==8)
        {
            R=false;
            CURRENT_COMMAND-=c;
        }
        Update_CurrentCommandGUI(CURRENT_COMMAND);
    }
    public void set_CurrentCommand(int C)
    {
        if(C==1 && TI==false)
        {
            TI=true;
            CURRENT_COMMAND += C;
        }
        else if(C==2 && TD==false)
        {
            TD=true;
            CURRENT_COMMAND += C;
        }
        else if (C==4 && L==false)
        {
            L=true;
            CURRENT_COMMAND += C;
        }
        else if(C==8 && R==false)
        {
            R=true;
            CURRENT_COMMAND += C;
        }
        Update_CurrentCommandGUI(CURRENT_COMMAND);
    }
    
    public void Update_CurrentCommandGUI(int CC)
    {
   
        BitDecision = CC;
        FXMLDocumentController.command = CC;
        //System.out.println(BitDecision);
        Platform.runLater(new Runnable() 
        {
            @Override
            public void run() 
            {
                
                if(CC==throttle_increase)
                {
                    CurrentCommand.setText("Throttle Increasing and CC : "+CC);
                }
                else if(CC==throttle_decrease)
                {
                    CurrentCommand.setText("Throttle Decreasing and CC : "+CC);
                }
                else if(CC==left)
                {
                    CurrentCommand.setText("Left and CC : "+CC);
                }
                else if(CC==right)
                {
                    CurrentCommand.setText("Right and CC : "+CC);
                }
                else if(CC==0) // no command
                {
                    CurrentCommand.setText("Idle and CC : "+CC);
                }
                else if(CC==9) // right + speed up
                {
                    CurrentCommand.setText("Throttle Increasing + right and CC : "+CC);
                }
                else if(CC==5) // left + speed up
                {
                    CurrentCommand.setText("Throttle Increasing + left and CC : "+CC);
                }
                else if(CC==10) // right + speed down
                {
                    CurrentCommand.setText("Throttle Decreasing + right and CC : "+CC);
                }
                else if(CC==6) // left + speed down
                {
                    CurrentCommand.setText("Throttle Decreasing + left and CC : "+CC);
                }
            }
        });
    }
    
    public void KeyPressedHandler(KeyEvent event)
    {
        switch (event.getCode()) 
        {
            case UP:
                set_CurrentCommand(throttle_increase);
                //System.out.println("UP PRESSED : CC : "+CURRENT_COMMAND);
                break;
            case DOWN:
                set_CurrentCommand(throttle_decrease);
                //System.out.println("DOWN PRESSED : CC : "+CURRENT_COMMAND);
                break;
            case LEFT:
                set_CurrentCommand(left);
                //System.out.println("LEFT PRESSED : CC : "+CURRENT_COMMAND);
                break;
            case RIGHT:
                set_CurrentCommand(right);
                //System.out.println("RIGHT PRESSED : CC : "+CURRENT_COMMAND);
                break;
            case C:
                Analogue_Updater.isSecureFlyActivated = true;
        }
    }
    
    public void KeyReleasedHandler(KeyEvent event)
    {
        switch (event.getCode()) 
        {
            case UP:
                releasedDecrement_CurrentCommand(throttle_increase);
                //System.out.println("UP RELEASED : CC : "+CURRENT_COMMAND);
                TI=false;   
                break;
            case DOWN:
                releasedDecrement_CurrentCommand(throttle_decrease);
                //System.out.println("DOWN RELEASED : CC : "+CURRENT_COMMAND);
                TD=false ;
                break;
            case LEFT:
                releasedDecrement_CurrentCommand(left);
                //System.out.println("LEFT RELEASED : CC : "+CURRENT_COMMAND);
                L=false ;
                break;
            case RIGHT:
                releasedDecrement_CurrentCommand(right);
                //System.out.println("RIGHT RELEASED : CC : "+CURRENT_COMMAND);
                R=false ;
                break;
        }
    }
}
