/*
    HTTP over TLS (HTTPS) example sketch

    This example demonstrates how to use
    WiFiClientSecure class to access HTTPS API.
    We fetch and display the status of
    esp8266/Arduino project continuous integration
    build.

    Limitations:
      only RSA certificates
      no support of Perfect Forward Secrecy (PFS)
      TLSv1.2 is supported since version 2.4.0-rc1

    Created by Ivan Grokhotkov, 2015.
    This example is in public domain.

    https://growgreenvlc.webs.upv.es/conexionget.php?SERIE=1&TEMPERATURA=23&HUMEDAD=90&SENSACION=0
*/

#include <ESP8266WiFi.h>
#include <WiFiClientSecure.h>

const char* ssid = "Red de MENSAMATIC";
const char* password = "06v94_mm";
const char* host = "fcm.googleapis.com";
const int httpsPort = 443;

String url = "/fcm/send";

WiFiClientSecure client;

int connectToWifi() {
    Serial.begin(9600);
    Serial.println();
    Serial.print("connecting to ");
    Serial.println(ssid);
    WiFi.mode(WIFI_STA);
    WiFi.begin(ssid, password);
    while (WiFi.status() != WL_CONNECTED) {
        delay(500);
        Serial.print(".");
    }
    Serial.println("");
    Serial.println("WiFi connected");
    Serial.println("IP address: ");
    Serial.println(WiFi.localIP());

    // Use WiFiClientSecure class to create TLS connection
    WiFiClientSecure client;
    Serial.print("connecting to ");
    Serial.println(host);
    if (!client.connect(host, httpsPort)) {
        Serial.println("connection failed");
        return -1;
    }
    return 1;
}

void requestPOST(String postData) {
    client.connect(host, httpsPort);
    Serial.print("requesting URL: ");
    Serial.println(url);

    Serial.println("connected");
    client.println("POST " + url + " HTTP/1.1");
    client.println("Host: fcm.googleapis.com");
    client.println("User-Agent: Arduino/1.0");
    client.println("Connection: close");
    client.println("Content-Type: application/json");
    client.println("Authorization: key=AAAA4Cj4sl4:APA91bG1zDZOjPQL4b5H1MiWi-GgB9UIhdGCLsv2J0q2EZrL8C6Eh0kmZW1OxX3qSUrFoOXgBxpgYypVXnN0vYlqR_VSms7-A40SqOHBFM-vMRrFbc7uFbRSB3tKRBaICKdMhiWlT7IB");
    client.print("Content-Length: ");
    client.println(postData.length());
    client.println();
    client.println(postData);

    Serial.println("request sent");
    while (client.connected()) {
        String line = client.readStringUntil('\n');
        if (line == "\r") {
            Serial.println("headers received");
            break;
        }
    }

    String line = client.readStringUntil('\n');

    bool checkResponse = false;
    String expectedResponse = "";
    if (checkResponse == true) {
        if (line.startsWith(expectedResponse)) {
            Serial.println("esp8266/Arduino CI successfull!");
        } else {
            Serial.println("esp8266/Arduino CI has failed");
        }
    }
    Serial.println("reply was:");
    Serial.println("==========");
    Serial.println(line);
    Serial.println("==========");
    Serial.println("closing connection");
}

void setup() {

    int retVal = connectToWifi();

    if (retVal > 0) {
        String postData = "{\"to\":\"/topics/key_asser\",\"priority\":\"high\",\"notification\":{\"body\":\"This is a Firebase Cloud Messaging Topic Message!\",\"title\":\"Arduino - FCM Message\"}}";

        requestPOST(postData);
    }
    Serial.println("End program");
}

void loop() {
}