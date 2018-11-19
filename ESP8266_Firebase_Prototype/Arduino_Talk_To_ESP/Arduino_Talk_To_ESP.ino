#include <SoftwareSerial.h> // serial command library, I think? see https://www.arduino.cc/en/Reference/SoftwareSerial for details

#define RX 2 // connected to TX of ESP8266
#define TX 1 // connected to RX of ESP8266


SoftwareSerial esp8266(RX,TX); // creates instance of SoftwareSerial object; we'll name it esp8266
// RX is the pin on which to recieve data
// TX is the pin on which to send data; check the documentation of specific arduino to see if it works

SoftwareSerial ESPSerial(14, 15);

int ledPin = 13;
int inputPin1 = 52;
int inputPin2 = 53;

int pirState = LOW;
int val1 = 0;
int val2 = 0;

void setup() {
  pinMode(ledPin, OUTPUT); 

  //ESPSerial.begin(115200);
  
  // TODO: figure out exactly what this code does
  Serial.begin(9600); // set baud rate of Arduino Mega
  Serial1.begin(9600);
  //esp8266.begin(115200); // set baud rate of esp8266 
  
  pinMode(ledPin, OUTPUT);
  pinMode(inputPin1, INPUT);
  pinMode(inputPin2, INPUT);
}

void loop() {
  
  val1 = digitalRead(inputPin1);
  val2 = digitalRead(inputPin2);

  if (val1 == HIGH || val2 == HIGH) {
    digitalWrite(ledPin, HIGH);
    Serial.println("Motion captured!");
    Serial.write("T");
    Serial1.write("Y");
    //Do something while a person is in range
    if(pirState == LOW){
      // Do something when a person first enters
      pirState = HIGH;
    }
  }
  else {
    digitalWrite(ledPin, LOW);
    // Do something while a person is out of range 
    if (pirState == HIGH){
      // Do something when a person leaves the range
      pirState = LOW;
    }
  }
  
}
