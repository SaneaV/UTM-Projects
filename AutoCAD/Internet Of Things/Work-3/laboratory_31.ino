// Include necessary libraries
#include <LiquidCrystal.h>

// Define constants and variables
const int sensorPin = A0;
const float referenceVoltage = 5.00;
const float conversionFactor = 1024.0;
const float temperatureOffset = 0.5;
const float humidityConstant = 0.16 / 0.0062;

LiquidCrystal lcd(12, 11, 5, 4, 3, 2);

bool systemOn = false; // System state (ON/OFF)
float temperatureSetPoint = 25.0; // Default set-point temperature in Celsius

// Function prototypes
void turnSystemOn();
void turnSystemOff();
void setTemperatureSetPoint();
void displayHelp();
float getTemperatureCelsius(int analogValue);
float getHumidity(float preHumidity, float temperatureCelsius);

// Pins for controlling outputs (you can adjust if needed)
const int pin1 = 8;
const int pin2 = 9;
const int pin3 = 10;

void setup() {
    pinMode(sensorPin, INPUT);
    pinMode(pin1, OUTPUT);
    pinMode(pin2, OUTPUT);
    pinMode(pin3, OUTPUT);
    Serial.begin(9600);
    lcd.begin(16, 2);
    lcd.clear();
    lcd.setCursor(0, 0);
    lcd.print("System: OFF");
    delay(500);

    // Show help menu on program start
    displayHelp();
}

void loop() {
    // Check for user commands via Serial
    if (Serial.available() > 0) {
        String command = Serial.readStringUntil('\n');
        command.trim(); // Remove extra spaces and newlines

        if (command == "on") {
            turnSystemOn();
        } else if (command == "off") {
            turnSystemOff();
        } else if (command == "set-point") {
            setTemperatureSetPoint();
        } else if (command == "help") {
            displayHelp(); // Call help function when 'help' is entered
        } else {
            Serial.println("Invalid command! Use 'help' for a list of commands.");
        }
    }

    // If the system is off, do not process data
    if (!systemOn) {
        return;
    }

    // Rest of the loop code for temperature and humidity
    int sensorValue = analogRead(sensorPin);
    float preHumidity = sensorValue / referenceVoltage;
    float temperatureCelsius = getTemperatureCelsius(sensorValue);
    float humidity = getHumidity(preHumidity, temperatureCelsius);

    lcd.clear();
    lcd.setCursor(0, 0);
    lcd.print("Temp: ");
    lcd.print(temperatureCelsius);
    lcd.print("C");

    lcd.setCursor(0, 1);
    lcd.print("Hum: ");
    lcd.print(humidity);
    lcd.print("%");

    Serial.print("Temperature: ");
    Serial.print(temperatureCelsius);
    Serial.print(" C, Humidity: ");
    Serial.print(humidity);
    Serial.println(" %");

    if (temperatureCelsius > temperatureSetPoint) {
        Serial.println("Warning: Temperature exceeds Set-Point!");
        lcd.setCursor(0, 1);
        lcd.print("T > SET-POINT!");
    }

    if (temperatureCelsius <= 0) {
        digitalWrite(pin2, HIGH);
        digitalWrite(pin1, LOW);
        digitalWrite(pin3, LOW);
    } 
    else if (temperatureCelsius > 0 && temperatureCelsius <= 27) {
        digitalWrite(pin1, HIGH);
        digitalWrite(pin2, LOW);
        digitalWrite(pin3, LOW);
    } 
    else if (temperatureCelsius > 27 && temperatureCelsius <= 50) {
        digitalWrite(pin1, LOW);
        digitalWrite(pin2, LOW);
        digitalWrite(pin3, HIGH);
    } 
    else if (temperatureCelsius > 50) {
        int x = random(3);
        if (x == 0) {
            digitalWrite(pin2, HIGH);
            digitalWrite(pin1, LOW);
            digitalWrite(pin3, LOW);
        } else if (x == 1) {
            digitalWrite(pin1, HIGH);
            digitalWrite(pin2, LOW);
            digitalWrite(pin3, LOW);
        } else if (x == 2) {
            digitalWrite(pin1, LOW);
            digitalWrite(pin2, LOW);
            digitalWrite(pin3, HIGH);
        }
    }

    delay(1000); // Delay for ease of reading
}

// Function to turn the system ON
void turnSystemOn() {
    systemOn = true;
    lcd.clear();
    lcd.setCursor(0, 0);
    lcd.print("System: ON");
    Serial.println("System turned ON");
}

// Function to turn the system OFF
void turnSystemOff() {
    systemOn = false;
    lcd.clear();
    lcd.setCursor(0, 0);
    lcd.print("System: OFF");
    Serial.println("System turned OFF");
}

// Function to set the temperature set-point
void setTemperatureSetPoint() {
    Serial.println("Enter temperature set-point in Celsius: ");
    while (!Serial.available()) {
        // Wait for user input
    }
    String input = Serial.readStringUntil('\n');
    input.trim();
    temperatureSetPoint = input.toFloat();
    Serial.print("Set-point updated to: ");
    Serial.print(temperatureSetPoint);
    Serial.println(" C");
}

// Function to display help menu
void displayHelp() {
    Serial.println("Available commands:");
    Serial.println("  on         - Turn the system ON");
    Serial.println("  off        - Turn the system OFF");
    Serial.println("  set-point  - Set the desired temperature set-point");
    Serial.println("  help       - Display this help menu");
    Serial.println("");
    Serial.println("System will display temperature and humidity when ON.");
    Serial.println("If temperature exceeds set-point, a warning will be shown.");
}

// Function to calculate temperature in Celsius
float getTemperatureCelsius(int analogValue) {
    float voltage = (analogValue * referenceVoltage) / conversionFactor;
    return (voltage - temperatureOffset) * 100;
}

// Function to calculate humidity
float getHumidity(float preHumidity, float temperatureCelsius) {
    float humidityInitial = preHumidity - humidityConstant;
    float trueHumidityConstant = 0.00216 * temperatureCelsius;
    float trueHumidityFactor = 1.0546 - trueHumidityConstant;
    return humidityInitial / trueHumidityFactor;
}
