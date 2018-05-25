#include <Dhcp.h>
#include <Dns.h>
#include <Ethernet.h>
#include <EthernetClient.h>
#include <EthernetServer.h>
#include <EthernetUdp.h>

/* RestClient simple GET request
 *
 * by Chris Continanza (csquared)
 */

//#include <Ethernet.h>
#include <SPI.h>
#include "RestClient.h"

//RestClient client = RestClient("api.github.com", 443);
//RestClient client = RestClient("192.168.1.50",5000);
//RestClient client = RestClient("http://httpbin.org");
RestClient client = RestClient("arduino-http-lib-test.herokuapp.com");

//Setup
void setup() {
    Serial.begin(115200);
    // Connect via DHCP
    Serial.println("connect to network");

//    byte mac[] = { 0xDE, 0xAD, 0xBE, 0xEF, 0xFE, 0xED };
//    //the IP address for the shield:
//    byte ip[] = { 192, 168, 1, 111 };
//    Ethernet.begin(mac,ip);

    client.dhcp();
/*
  // Can still fall back to manual config:
  byte mac[] = { 0xDE, 0xAD, 0xBE, 0xEF, 0xFE, 0xED };
  //the IP address for the shield:
  byte ip[] = { 192, 168, 2, 11 };
  Ethernet.begin(mac,ip);
*/
    Serial.println("Setup!");
}

String response;
void loop(){
    response = "";
    int statusCode = client.get("/get", &response);
    Serial.print("Status code from server: ");
    Serial.println(statusCode);
    Serial.print("Response body from server: ");
    Serial.println(response);
    delay(10000);
}