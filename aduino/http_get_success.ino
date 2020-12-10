//#include <LiquidCrystal_I2C.h>
#include <ESP8266WiFi.h>
#include <ESP8266HTTPClient.h>
//#include <Wire.h>

//LiquidCrystal_I2C lcd(0x27, 16, 2);

const char *ssid = "";
const char *password = "";
const char* host = "";

int ledPin1 = 13;      // D6번 LED 
int ledPin2 = 12;      // D7번 LED 
int inputPin = 14;     // D5번 센서 신호핀
int pirState = LOW;   // 센서 초기상태는 움직임이 없음을 가정
int val = 0;          // 센서 신호의 판별을 위한 변수


void setup() {
  Serial.begin(115200);
  delay(10);
  pinMode(ledPin1, OUTPUT);    // 1번 LED를 출력으로 설정
  pinMode(ledPin2, OUTPUT);    // 2번 LED를 출력으로 설정
  pinMode(inputPin, INPUT);    // 센서 Input 설정

  // lcd변수 초기화
  //lcd.init();
  //lcd.backlight();
  
  // We start by connecting to a WiFi network
  Serial.println();
  Serial.println();
  Serial.print("Connecting to ");
  Serial.println(ssid);
  
  WiFi.begin(ssid, password);
  
  while (WiFi.status() != WL_CONNECTED) {
    delay(500);
    Serial.print(".");
  }

  Serial.println("");
  Serial.println("WiFi connected");  
  Serial.println("IP address: ");
  Serial.println(WiFi.localIP());
}

int value = 0;

void loop() {
  val = digitalRead(inputPin);         // 센서 신호값을 읽어와서 val에 저장

  //lcd.clear();
  //lcd.setCursor(0, 0);
  //lcd.print("Hankyung Libary");
    
  if (val == HIGH) {                   // 센서 신호값이 HIGH면(인체 감지가 되면)    
    digitalWrite(ledPin1, HIGH);       // 1번 LED ON
    digitalWrite(ledPin2, LOW);        // 2번 LED OFF
    if (pirState == LOW){                         
         Serial.println("Welcome! seat down");    // 시리얼 모니터 출력
         pirState = HIGH;
         //lcd.setCursor(0, 1);
         //lcd.print("seet Available");
         sensor_on();
    }
   }
   else {                             // 센서 신호값이 LOW면(인체감지가 없으면)
    digitalWrite(ledPin1, LOW);       // 1번 LED OFF
    digitalWrite(ledPin2, HIGH);      // 2번 LED ON
    if (pirState == HIGH){                
        Serial.println("Good Bye~ seat up");   // 시리얼 모니터 출력
        pirState = LOW;
        //lcd.setCursor(0, 1);
        //lcd.print("seet Unavailable");
        sensor_off();
    }
  }
  
  delay(100);
}

void sensor_on(){
  delay(1000);
  ++value;

  Serial.print("connecting to ");
  Serial.println(host);
  
  // Use WiFiClient class to create TCP connections
  WiFiClient client;
  const int httpPort = 80;
  if (!client.connect(host, httpPort)) {
    Serial.println("connection failed");
    return;
  }
  
  // We now create a URI for the request
  String url = "/iot/piron";
//  url += streamId;
//  url += "?private_key=";
//  url += privateKey;
//  url += "&value=";
//  url += value;
  
  Serial.print("Requesting URL: ");
  Serial.println(url);
  
  // This will send the request to the server
  client.print(String("GET ") + url + " HTTP/1.1\r\n" +
               "Host: " + host + "\r\n" + 
               "Connection: close\r\n\r\n");
  int timeout = millis() + 5000;
  while (client.available() == 0) {
    if (timeout - millis() < 0) {
      Serial.println(">>> Client Timeout !");
      client.stop();
      return;
    }
  }
  // Read all the lines of the reply from server and print them to Serial
  while(client.available()){
    String line = client.readStringUntil('\r');
    Serial.print(line);
  }
  Serial.println();
  Serial.println("closing connection");

  delay(1000*30);
}


void sensor_off(){
  delay(1000);
  ++value;

  Serial.print("connecting to ");
  Serial.println(host);
  
  // Use WiFiClient class to create TCP connections
  WiFiClient client;
  const int httpPort = 80;
  if (!client.connect(host, httpPort)) {
    Serial.println("connection failed");
    return;
  }
  
  // We now create a URI for the request
  String url = "/iot/piroff";
//  url += streamId;
//  url += "?private_key=";
//  url += privateKey;
//  url += "&value=";
//  url += value;
  
  Serial.print("Requesting URL: ");
  Serial.println(url);
  
  // This will send the request to the server
  client.print(String("GET ") + url + " HTTP/1.1\r\n" +
               "Host: " + host + "\r\n" + 
               "Connection: close\r\n\r\n");
  int timeout = millis() + 5000;
  while (client.available() == 0) {
    if (timeout - millis() < 0) {
      Serial.println(">>> Client Timeout !");
      client.stop();
      return;
    }
  }
  // Read all the lines of the reply from server and print them to Serial
  while(client.available()){
    String line = client.readStringUntil('\r');
    Serial.print(line);
  }
  Serial.println();
  Serial.println("closing connection");
}
