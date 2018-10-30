// This program will allow the ESP8266 to listen to input from the Arduino Mega and connect to wifi.

#include <ESP8266WiFi.h>
#include <FirebaseArduino.h>
/*
const char *ssid = "HPENVY";
const char *password = "wbnu5405";
*/

void setup(){
  WiFi.begin("HPENVY", "wbnu5405");
  Serial.begin(115200);
  Serial.print("test!");
  Serial.print(WiFi.localIP());
  
} 
void loop(){
}
 
