#include <Wire.h>                // Library for I2C
#include <Adafruit_LiquidCrystal.h> // Library for I2C LCD display

const byte LcdAddress = 0x20;     // LCD display address
Adafruit_LiquidCrystal lcd(LcdAddress);

const int ledPin = 13;            // LED pin 13
int receivedPacketCounter = 0;
int receivedPayload = 0;

void setup() {
  pinMode(ledPin, OUTPUT);         // Configure LED pin
  Wire.begin(9);                  // Set slave device address
  Wire.onReceive(receivePacket);  // Handler for incoming data
  Serial.begin(9600);             // Initialize Serial communication
  lcd.begin(16, 2);               // Initialize 16x2 LCD display
  lcd.setBacklight(HIGH);         // Turn on LCD backlight
}

void loop() {
  // LED control logic based on received data
  if (receivedPayload > 88) {
    digitalWrite(ledPin, HIGH);
  } else {
    digitalWrite(ledPin, LOW);
  }
  
  // Clear the screen with a delay
  lcd.clear(); // Clear the screen
  delay(100);  // Delay for update

  // Display data on LCD
  lcd.setCursor(0, 0); // Set cursor at the beginning of the first line
  lcd.print("Counter: ");  // Print text
  lcd.print(receivedPacketCounter);  // Print packet counter value
  
  lcd.setCursor(0, 1);  // Set cursor on the second line
  lcd.print("Payload: ");  // Print text
  lcd.print(receivedPayload);  // Print payload value
  
  delay(2000); // Delay for visual effect
}

void receivePacket(int bytes) {
  if (bytes == 11) { // Packet length is always 11 bytes
    char startIndicator = Wire.read();
    int packetCounter = 0;
    Wire.readBytes((byte*)&packetCounter, sizeof(packetCounter)); // Read integer (2 bytes)
    char senderID = Wire.read();
    char receiverID = Wire.read();
    char packetType = Wire.read();
    int payload = 0;
    Wire.readBytes((byte*)&payload, sizeof(payload)); // Read integer (2 bytes)
    int checksum = 0;
    Wire.readBytes((byte*)&checksum, sizeof(checksum)); // Read integer (2 bytes)
    char endIndicator = Wire.read();
    
    // Validate checksum
    int calculatedChecksum = startIndicator + packetCounter + senderID + receiverID + packetType + payload + endIndicator;
    if (startIndicator == '<' && endIndicator == '>' && checksum == calculatedChecksum) {
      receivedPacketCounter = packetCounter;
      receivedPayload = payload;
      // Output to Serial for debugging
      Serial.print("Packet Received: ");
      Serial.print("Start Indicator=");
      Serial.print(startIndicator);
      Serial.print(", Counter=");
      Serial.print(packetCounter);
      Serial.print(", Sender ID=");
      Serial.print(senderID);
      Serial.print(", Receiver ID=");
      Serial.print(receiverID);
      Serial.print(", Packet Type=");
      Serial.print(packetType);
      Serial.print(", Payload=");
      Serial.print(payload);
      Serial.print(", Checksum=");
      Serial.print(checksum);
      Serial.print(", End Indicator=");
      Serial.println(endIndicator);
    } else {
      Serial.println("Error: Invalid packet (checksum or format).");
    }
  } else {
    Serial.println("Error: Packet length is not 11 bytes.");
  }
}
