int inputPin = 10;
int pirState = LOW;
int val = 0;

void setup() {
  pinMode(inputPin, INPUT);
}

void loop() {
  val = digitalRead(inputPin);
  if (val == HIGH) {
    // Do something while a person is in range
    if(pirState == LOW){
      // Do something when a person first enters
      pirState = HIGH;
    }
  }
  else {
    // Do something while a person is out of range 
    if (pirState == HIGH){
      // Do something when a person leaves the range
      pirState = LOW;
    }
  }
}
