// This program listens to input from the connected arduino and sends alerts to Firebase.

#include <ESP8266WiFi.h>
#include <FirebaseArduino.h>
#include <SoftwareSerial.h>

// set this to be your unique user UID, found in the app after you make an account!
#define USER_UID "ghOWrsWTzDbly9oIccIwPwMDdvu2"

// set this to be the name of your sensor!
#define SENSOR_NAME "Chen's Dorm"

// set this to be your wifi credentials. Doesn't work on Enterprise WPA2. Leave password empty if no password.
//#define WIFI_SSID "uw-wifi-setup-no-encryption"
#define WIFI_SSID "HPENVY"
#define WIFI_PASSWORD "wbnu5405"

// Orthus firebase URL and secret. Using the secret is very insecure, but there's no way around it easily.
#define DATABASE_URL "orthusapp.firebaseio.com"
#define DATABASE_SECRET "xazt0htFMJIBNwbdrh4dYZru2ctvVkAisx1mjnbM"


boolean newData = false;
boolean isInitialized = false;
boolean activityDetected = false;

int cooldown = 0;
// firebase json address of the user's node
String userNode;
String sensorName;




void setup() {
  // connect to wifi and firebase.
  WiFi.begin(WIFI_SSID,WIFI_PASSWORD);
  Firebase.begin(DATABASE_URL,DATABASE_SECRET);

  // set to the baud rate of the arduino;
  Serial.begin(115200);

  // only easy way to concatenate strings
  String uid = USER_UID;
  userNode = "users/" + uid;

  sensorName = SENSOR_NAME;

}

void loop() {
  
  // sometimes, the first write to Firebase doesn't register, so we keep looping until it is successful.
  initializeSensor();
  
  // listen for any new inputs from the Arduino
  recvData();

  // if new data has been received, handle it and try to send an alert.
  // what this code allows is a single alert for each "block" of time a person has been in the room for. If the sensor doesn't detect a lapse in movement, it won't send out another alert.
  if (newData == true) {
      if (cooldown <= 0 && Firebase.getBool(userNode + "/armed")) {
        Firebase.pushString(userNode + "/alerts", "Activity: " + sensorName);
      }

      cooldown = 10;
      // cooldown activates.
      
      Firebase.setInt(userNode + "/alert", 1);
      
      delay(2000);
      newData = false;
      Serial.flush();
      
  } else if (cooldown <= 0) {
    Firebase.setInt(userNode + "/alert", 0);
  }

  cooldown--;
}

void initializeSensor(){
  Firebase.setString(userNode + "/alerts/initial", "Initialized: " + sensorName);

}

void recvData() {
 if (Serial.available() > 0) {
  newData = true;
  // flush buffer
  while(Serial.available() > 0) {
    Serial.read();
  }

 } else {
  newData = false;
 }
}
