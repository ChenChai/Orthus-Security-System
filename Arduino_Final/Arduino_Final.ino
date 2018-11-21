#include <SoftwareSerial.h>

#define RX 2
#define TX 1

int input1Pin = 52;
int input2Pin = 53;
int ledPin = LOW;
int pirState = LOW;
int val1 = 0;
int val2 = 0;
char ON = '1';

SoftwareSerial esp8266(RX, TX);


void setup() {
  pinMode(ledPin, OUTPUT);
  pinMode(input2Pin; INPUT);
  pinMode(input2Pin; INPUT);
  Serial.begin(9600);
  esp8266.begin(9600);
}

void loop() {
  recvData;
  if(ON == '1'){
    digitalWrite(ledPin, HIGH); 
    val1 = digitalRead(inputPin1);
    val2 = digitalRead(inputPin2);
    if (val1 == HIGH || val2 == HIGH){
      if(pirState == LOW){
        pirState = HIGH;
        esp8266.print('1');
      }
    }
    else {
      if(pirState == HIGH){
        pirState = LOW;
        esp8266.print('0');
      }
    }
  }
  else{
    digitalWrite(ledPin, LOW);
    }
}


void recvData() {
 if (esp8266.available() > 0) {
  ON = Serial.read();
 } 
}
