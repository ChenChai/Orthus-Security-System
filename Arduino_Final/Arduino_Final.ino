int inputPin1 = 52;
int inputPin2 = 53;
int ledPin = 13;
int pirState = LOW;
int val1 = 0;
int val2 = 0;

void setup() {
  pinMode(ledPin, OUTPUT);
  pinMode(inputPin1, INPUT);
  pinMode(inputPin2, INPUT);
  Serial.begin(9600);
  Serial1.begin(9600);
}

void loop() {
  val1 = digitalRead(inputPin1);
  val2 = digitalRead(inputPin2);
  //digitalWrite(ledPin, HIGH);
  if (val1 == HIGH && val2 == HIGH) {
    digitalWrite(ledPin, HIGH);
    
    Serial.write(1);
    Serial1.write(1);
    delay(1000);
    
    Serial.println("Motion captured!");
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
