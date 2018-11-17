// This program will allow the ESP8266 to listen to input from the Arduino Mega and connect to wifi.

#include <ESP8266WiFi.h>
#include <FirebaseArduino.h>
#include <SoftwareSerial.h>
/*
const char *ssid = "HPENVY";
const char *password = "wbnu5405";
*/

int receivedInt;
boolean newData = false;
int i = 0;

void setup(){
  WiFi.begin("uw-wifi-setup-no-encryption","");
  //WiFi.begin("HPENVY", "wbnu5405");
  Serial.begin(9600);

  //Serial.print(WiFi.localIP());
  // get the secret from account settings > service accounts > database secrets in Firebase Console.
  Firebase.begin("orthusapp.firebaseio.com","xazt0htFMJIBNwbdrh4dYZru2ctvVkAisx1mjnbM");
  
} 
void loop(){
  recvData();
  // This creates a new child node.
  Firebase.pushInt("testValue", 2);
  
  
  if(newData){
    Firebase.setInt("testValue", 1);
    /*if(Firebase.failed()){
  
      Serial.println(Firebase.error());
    } else{
      Serial.println("Set 1 succeeded!");
    }*/
    delay(1000);
  }
  /*else {
    Firebase.setInt("testValue", 0);
       if(Firebase.failed()){
  
      Serial.println(Firebase.error());
    } else{
      Serial.println("Set 0 succeeded!");
    }
    delay(1000);
  }*/
}

void recvData() {
 if (Serial.available() > 0) {
  newData = true;
 }
}
