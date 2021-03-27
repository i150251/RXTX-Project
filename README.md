# RXTX-Project
A project based on RXTX Communication with arduino for controlling remote controlled RC cars. the code present in it is very easy to understand so you may find it easy to extract knowledge from it.

This project was built for controlling a remote controlled RC car through a PC/Laptop by help of arduino.
The basic concept is as follows:

1- Open the RC car controller and connect it to arduino through proper wiring. 
2- once arduino and RC car controller get attached, connect the arduino to laptop through USB.
3- Now we will use the RXTX Java library in our project to communicate java code with Arduino. 
4- We have added listeners for keys such as left, right, up or down. Once a button is pressed, a function gets triggered and sends signal to arduino through RXTX library.
lets say I press left, the left signal is transmitted to arduino and code is written in arduino to detect left and send current to the specific place so that left signal gets
raised by the controller.
