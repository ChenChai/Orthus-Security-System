#include <SoftwareSerial.h> // serial command library, I think? see https://www.arduino.cc/en/Reference/SoftwareSerial for details

#define RX 2 // connected to TX of ESP8266
#define TX 1 // connected to RX of ESP8266


SoftwareSerial esp8266(RX,TX); // creates instance of SoftwareSerial object; we'll name it esp8266
// RX is the pin on which to recieve data
// TX is the pin on which to send data; check the documentation of specific arduino to see if it works


int ledPin = 13;

void setup() {
  pinMode(ledPin, OUTPUT); 

  // TODO: figure out exactly what this code does
  Serial.begin(9600); // set baud rate of Arduino Mega
  esp8266.begin(9600); // set baud rate of esp8266 
}

void loop() {
  
  esp8266.print("T");
  digitalWrite(ledPin,HIGH);
  //delay(2000);
  digitalWrite(ledPin, LOW);
  //delay(2000);

}
