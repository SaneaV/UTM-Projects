// Named constants for LED pin and serial baud rate
const int LedPin = 13; // Pin to which the LED is connected
const int SerialBaudRate = 9600; // Baud rate for the serial interface

void setup() {
  pinMode(LedPin, OUTPUT); // Set the LED pin to output mode
  Serial.begin(SerialBaudRate); // Initialize the serial interface with the specified baud rate
}

void loop() {
  if (Serial.available() > 0) { // Check if there are any available data in the serial interface
    String Command = Serial.readStringUntil('\n'); // Read the command until a newline character
    Command.trim(); // Remove leading and trailing spaces from the string
    
    if (Command == "led on") { // Use CamelCase for the command
      digitalWrite(LedPin, HIGH); // Turn on the LED
      Serial.println("Led Is On"); // Send confirmation message to the terminal using CamelCase
    } else if (Command == "led off") { // Use CamelCase for the command
      digitalWrite(LedPin, LOW); // Turn off the LED
      Serial.println("Led Is Off"); // Send confirmation message to the terminal using CamelCase
    } else {
      Serial.println("Unknown Command"); // Send a message about an unknown command using CamelCase
    }
  }
}