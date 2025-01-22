#include <Wire.h> // Library for I2C

const int slaveAddress = 9; // Slave address
int packetCounter = 0;      // Packet counter
int analogValue = 0;

void setup() {
  Wire.begin();             // Initialize I2C as master
  Serial.begin(9600);       // Initialize Serial for debugging
}

void loop() {
  analogValue = analogRead(A0); // Read value from analog sensor (e.g., potentiometer)
  analogValue /= 4;             // Scale it to range 0-255
  sendPacket(analogValue);      // Send packet to slave
  delay(1000);                  // 1-second delay
}

void sendPacket(int payload) {
  char startIndicator = '<';
  char endIndicator = '>';
  char senderID = 'M'; // Master
  char receiverID = 'S'; // Slave
  char packetType = 'D'; // Data
  // Calculate checksum, summing all numeric values
  int checksum = startIndicator + packetCounter + senderID + receiverID + packetType + payload + endIndicator;
  
  // Send the packet via I2C
  Wire.beginTransmission(slaveAddress);
  Wire.write(startIndicator);
  Wire.write((byte*)&packetCounter, sizeof(packetCounter)); // Send the packet counter (2 bytes)
  Wire.write(senderID);
  Wire.write(receiverID);
  Wire.write(packetType);
  Wire.write((byte*)&payload, sizeof(payload)); // Send the payload (2 bytes)
  Wire.write((byte*)&checksum, sizeof(checksum)); // Send checksum (2 bytes)
  Wire.write(endIndicator);
  Wire.endTransmission();
  
  // Debug output to Serial
  Serial.print("Sent Packet: ");
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
  
  packetCounter++; // Increment the packet counter
}
