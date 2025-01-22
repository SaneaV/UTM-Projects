#include <LiquidCrystal_I2C.h>

// Pin definitions
const int lightPin = 7;
const int motorEnablePin = 3;
const int motorIn3Pin = 5;
const int motorIn4Pin = 4;

// Constants for light state
const int lightOff = LOW;
const int lightOn = HIGH;

// Initialize LCD
LiquidCrystal_I2C lcd(0x20, 16, 2);

// Variables to store the state
int currentLightState = lightOff;
int motorSpeed = 0;  // Store speed in percentage from -100 to 100
int speedValue = 0;  // Store PWM speed value

void setup() {
  Serial.begin(9600);
  pinMode(lightPin, OUTPUT);
  pinMode(motorEnablePin, OUTPUT);
  pinMode(motorIn3Pin, OUTPUT);
  pinMode(motorIn4Pin, OUTPUT);

  // Set initial motor state
  digitalWrite(motorIn3Pin, LOW);
  digitalWrite(motorIn4Pin, LOW);

  // Set initial light state
  digitalWrite(lightPin, currentLightState);

  // Initialize and configure the LCD
  lcd.init();
  lcd.backlight();
  lcd.setCursor(0, 0);
  lcd.print("LIGHT IS OFF");
  lcd.setCursor(0, 1);
  lcd.print("MOTOR IS OFF");

  printHelp();
}

void loop() {
  while (Serial.available()) {
    String command = Serial.readStringUntil('\n');
    command.trim(); // Remove extra spaces
    handleCommand(command);
    updateDisplay();
  }
}

void handleCommand(String command) {
  if (command == "on") {
    turnLightOn();
    Serial.println("Light turned ON");
  } else if (command == "off") {
    turnLightOff();
    Serial.println("Light turned OFF");
  } else if (command.startsWith("motor ")) {
    changeMotorState(command);
  } else if (command == "help") {
    printHelp();
  } else if (command == "status") {
    printStatus();
  } else {
    Serial.println("Unknown command. Type 'help' for available commands.");
  }
}

void turnLightOn() {
  currentLightState = lightOn;
  digitalWrite(lightPin, currentLightState);

  // Update the LCD, clearing the old value
  lcd.setCursor(0, 0);  // Go to the first line
  lcd.print("LIGHT IS ON   ");  // Add spaces at the end to erase any old characters
}

void turnLightOff() {
  currentLightState = lightOff;
  digitalWrite(lightPin, currentLightState);

  // Update the LCD, clearing the old value
  lcd.setCursor(0, 0);  // Go to the first line
  lcd.print("LIGHT IS OFF  ");  // Add spaces at the end to erase any old characters
}

void changeMotorState(String value) {
  if (value.startsWith("motor ", 0)) {
    String signedValue = value.substring(6);  // Remove "motor "
    String sign = signedValue.substring(0, 1);  // Get the sign (+ or -)
    int speed = signedValue.substring(1).toInt();  // Get the speed, e.g. 50

    // Check if the speed is in a valid range
    if (speed > 100 || speed < -100) {
      Serial.println("Enter valid data!");
      return;
    }

    // Scale speed to PWM range 0-255
    int scaledSpeed = speed * (255 / 100); 

    // Multiply by 120 for correct RPM display
    if (sign == "+") {
      digitalWrite(motorIn3Pin, HIGH);
      digitalWrite(motorIn4Pin, LOW);
      analogWrite(motorEnablePin, scaledSpeed);
      speedValue = speed * 120;  // For forward direction
    } else if (sign == "-") {
      digitalWrite(motorIn3Pin, LOW);
      digitalWrite(motorIn4Pin, HIGH);
      analogWrite(motorEnablePin, scaledSpeed);
      speedValue = speed * -120;  // For backward direction
    } else if (speedValue == 0) {
      digitalWrite(motorIn3Pin, LOW);
      digitalWrite(motorIn4Pin, LOW);
      analogWrite(motorEnablePin, 0); // Stop the motor
      speedValue = 0;
    }

    // Output information to Serial Monitor
    Serial.print("Motor set to ");
    Serial.print(sign == "+" ? "forward" : (sign == "-" ? "backward" : "off"));
    Serial.print(" with speed ");
    Serial.println(speed);
  }
}

void updateDisplay() {
  // Clear the LCD and update the display
  lcd.clear();

  // Update the light status
  lcd.setCursor(0, 0);
  lcd.print(currentLightState == lightOn ? "LIGHT IS ON" : "LIGHT IS OFF");

  // Update motor status based on speed value
  if (speedValue > 0) {
    lcd.setCursor(0, 1);
    lcd.print("M->;RPM ");
    lcd.print(speedValue);  // Display the RPM value
  } else if (speedValue < 0) {
    lcd.setCursor(0, 1);
    lcd.print("M<-;RPM ");
    lcd.print(-speedValue);  // Display the absolute value of RPM
  } else {
    lcd.setCursor(0, 1);
    lcd.print("MOTOR IS OFF");
  }
}

void printHelp() {
  Serial.println("Available commands:");
  Serial.println("  on - Turn light ON");
  Serial.println("  off - Turn light OFF");
  Serial.println("  motor +<speed> - Set motor forward speed (e.g., motor +50)");
  Serial.println("  motor -<speed> - Set motor backward speed (e.g., motor -75)");
  Serial.println("  help - Show this help message");
  Serial.println("  status - Show current status of light and motor");
}

void printStatus() {
  Serial.print("Light is ");
  Serial.println(currentLightState == lightOn ? "ON" : "OFF");
  if (speedValue > 0) {
    Serial.print("Motor is moving forward at ");
    Serial.print(speedValue);
    Serial.println(" RPM");
  } else if (speedValue < 0) {
    Serial.print("Motor is moving backward at ");
    Serial.print(-speedValue);
    Serial.println(" RPM");
  } else {
    Serial.println("Motor is OFF");
  }
}
