// This program will allow the ESP8266 to listen to input from the Arduino Mega and connect to wifi.

#include <ESP8266WiFi.h>
#include <FirebaseArduino.h>
#include <SoftwareSerial.h>

// set this to be your UID, found in the app!
#define USER_UID "ghOWrsWTzDbly9oIccIwPwMDdvu2"
#define SENSOR_NAME "Chen's Dorm"

#define WIFI_SSID "uw-wifi-setup-no-encryption"
#define WIFI_PASSWORD ""

// get the secret from account settings > service accounts > database secrets in Firebase Console.
#define DATABASE_URL "orthusapp.firebaseio.com"
#define DATABASE_SECRET "xazt0htFMJIBNwbdrh4dYZru2ctvVkAisx1mjnbM"



int receivedInt;
boolean newData = false;
boolean activityDetected = false;
boolean isInitialized = false;
String userNode;

int i = 0;

void setup(){
  WiFi.begin(WIFI_SSID, WIFI_PASSWORD);
  Serial.begin(9600);

  String uid = USER_UID;
  userNode = "users/" + uid;
  
  // get the secret from account settings > service accounts > database secrets in Firebase Console.
  Firebase.begin(DATABASE_URL,DATABASE_SECRET);

  // sends a message to app to tell user sensor has been initialized
  
} 
void loop(){
  
  // sometimes, it takes multiple calls to write.
  if(!isInitialized) initializeSensor();
  
  


  /*
  sendAlert();
  delay(1000000);

  if(activityDetected){
    Firebase.setInt(userNode + "/alert", 1);
  } else {
    Firebase.setInt(userNode + "/alert", 0);
  }
  
  recvData();


  if(newData){
    Firebase.setInt("testValue", 1);
    delay(1000);
  }
*/
}

void initializeSensor(){
  String sensorName = SENSOR_NAME;
  Firebase.setString(userNode + "/alerts/initial", "Initialized: " + sensorName);
  if(Firebase.success()){
    isInitialized = true;  
  } else {
    isInitialized = false;
  }
  
}

// TODO Handle empty node when pushing
void sendAlert() {

  String uid = USER_UID;
  String node = "users/" + uid + "/alerts";
  String sensorName = SENSOR_NAME;
  Firebase.pushString(node, "Activity: " + sensorName);
  
}

void recvData() {
 if (Serial.available() > 0) {
  newData = true;
 } else {
  newData = false;
 }
}
