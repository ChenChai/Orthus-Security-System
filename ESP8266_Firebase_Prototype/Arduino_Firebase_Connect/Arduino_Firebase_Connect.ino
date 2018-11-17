
#include <ESP8266WiFi.h>

#include <ArduinoJson.h>
#include <FirebaseArduino.h> // note: need to run ArduinoJson on 5.13.2 to work.



#include <SoftwareSerial.h> // serial command library, I think? see https://www.arduino.cc/en/Reference/SoftwareSerial for details

#define RX 10 // connected to TX of ESP8266
#define TX 11 // connected to RX of ESP8266


SoftwareSerial esp8266(RX,TX); // creates instance of SoftwareSerial object; we'll name it esp8266
// RX is the pin on which to recieve data
// TX is the pin on which to send data; check the documentation of specific arduino to see if it works


#define WIFI_SSID "HPENVY" // test hotspot, change to personal wifi
#define WIFI_PASSWORD "wbnu5405"

// we'll just have this here to ensure the Arduino Mega can still do stuff
int ledPin = 13;

void setup() {
  pinMode(ledPin, OUTPUT); 

  // TODO: figure out exactly what this code does
  Serial.begin(9600); // set baud rate of Arduino Mega
  esp8266.begin(115200); // set baud rate of esp8266 
  
  // transmit data to the esp8266 command line
  esp8266.println("AT");
  esp8266.println("AT+CWMODE=1"); // sets ESP8266 to station mode, instead of AP mode.
  esp8266.println("AT+CWJAP=\"HPENVY\",\"wbnu5405\""); // need the escape characters as the command line needs quotes in the command.
}

void loop() {
  
  digitalWrite(ledPin,HIGH);
  delay(2000);
  digitalWrite(ledPin, LOW);
  delay(2000);

}
