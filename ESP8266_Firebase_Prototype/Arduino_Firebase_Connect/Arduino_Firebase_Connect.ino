// This program will allow the ESP8266 to listen to input from the Arduino Mega and connect to wifi.

#include <ESP8266WiFi.h>
#include <FirebaseArduino.h>
/*
const char *ssid = "HPENVY";
const char *password = "wbnu5405";
*/

int i = 0;

void setup(){
  WiFi.begin("HPENVY", "wbnu5405");
  Serial.begin(115200);

  Serial.print(WiFi.localIP());
  // get the secret from account settings > service accounts > database secrets in Firebase Console.
  Firebase.begin("fir-vertical-prototype.firebaseio.com","MbcYHrAArRq9WhAX4VXOm6UDupZ3Qz39yIkfAyTS");
  
} 
void loop(){
  
  Firebase.setInt("testValue", 1);
  if(Firebase.failed()){

    Serial.println(Firebase.error());
  } else{
    Serial.println("Set 1 succeeded!");
  }
  delay(1000);

  Firebase.setInt("testValue", 0);
  if(Firebase.failed()){

    Serial.println(Firebase.error());
  } else{
    Serial.println("Set 0 succeeded!");
  }
  delay(1000);
}
 
