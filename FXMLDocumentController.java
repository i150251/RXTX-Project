/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rxtx.project.one;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

/**
 *
 * @author DELL
 */
public class FXMLDocumentController implements Initializable 
{
    
    @FXML
    public Label label;
    @FXML
    public Label uav_speed;
    @FXML
    private Label uav_roll;
    @FXML
    private Label uav_pitch;
    @FXML
    public Label CC;

    public Analogue_Updater AU;
    public static UAV uav_Control = new UAV();
    public TextField PortField;
    RC_Controller RCController;
    
    //static fields required for all things.
    public static int command;
    public static int throttle;
    public static int balance;
    @FXML
    private void ActivateHandler(ActionEvent event) 
    {
       
        command = throttle = balance = 0;
        AU = new Analogue_Updater(); // analgoue values calculator thread.
        RCController = new RC_Controller();
        uav_speed.setText("--");
        uav_Control.setCurrentCommandGUILabel(CC);
        CC.setText("Listening");    
        RCController.setPortField(PortField.getText());
        if(RCController.connect()==null) // connect to arduino.
        {
            AU.Set_RCC(RCController);
        }
        uav_Control.Set_Analogue(AU);
        AU.set_UAVREF(uav_Control);
        AU.set_UAVSpeedLabel(uav_speed);
        AU.set_LRLabel(uav_roll);
        uav_speed.setText("NA");
        AU.start();
        PortField.setVisible(false);
    }
    
    @FXML
  public void handleKeyPressed(ActionEvent ke)
  {
      System.out.println(command + " " + throttle + " " + balance);
  }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        // TODO
    }    
    
}
