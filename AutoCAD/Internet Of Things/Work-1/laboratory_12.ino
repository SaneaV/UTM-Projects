#include <Wire.h>
#include <Adafruit_LiquidCrystal.h>

const byte LcdAddress = 0;
Adafruit_LiquidCrystal lcd(LcdAddress);

// Define sensor pins
const int HumidityPin = A1; // Humidity sensor connected to analog pin A1

// Constants for humidity calculation
const float ReferenceVoltage = 5.0; // in volts
const float MaxHumidityValue = 100.0; // Maximum value for humidity

// Array size constants
const int ArraySize = 5;

void setup() {
    // Initialize the LCD with 16 columns and 2 rows
    lcd.begin(16, 2);
    lcd.setBacklight(HIGH); // Activate backlight
    
    // Start serial communication at 9600 baud rate
    Serial.begin(9600);
}

float getHumidity(int rawValue) {
    float voltage = (rawValue * (ReferenceVoltage / 1023.0)); // Convert to volts
    float humidity = (voltage / ReferenceVoltage) * MaxHumidityValue; // Convert to percentage
    
    Serial.print("Raw Value: ");
    Serial.println(rawValue);
    Serial.print("Voltage: ");
    Serial.println(voltage);
    Serial.print("Real time humidity = ");
    Serial.println(humidity);
    
    return humidity;
}

void shiftArray(float arr[], const int size) {
    for (int i = 0; i < size - 1; i++) {
        arr[i] = arr[i + 1];
    }
}

void display(float t1, float t2) {
    lcd.setCursor(0, 0);
    lcd.print("Avg Humi: ");
    lcd.print(t1);
    lcd.print("%");
    lcd.setCursor(0, 1);
    lcd.print("Filtered: ");
    lcd.print(t2);
    lcd.print("%");
}

void displayWaitingMessage() {
    lcd.setCursor(0, 0);
    lcd.print("Waiting...");
    lcd.setCursor(0, 1);
    lcd.print("Please wait.");
}

void clearDisplayMessage() {
    lcd.clear();
}

float calculateWeightedAverage(float readings[], const int size) {
    const int Weight = 2;
    float sum = 0;
    float totalWeight = 0;
    for (int i = 0; i < size; i++) {
        sum += readings[i] * Weight;
        totalWeight += Weight;
    }
    return sum / totalWeight;
}

int compareFloats(const void* a, const void* b) {
    float fa = *(const float*)a;
    float fb = *(const float*)b;
    return (fa > fb) - (fa < fb);
}

float applySaltAndPepperFilter(float readings[], const int size) {
    float sortedReadings[size];
    // Copy the array to a new one for sorting
    for (int i = 0; i < size; i++) {
        sortedReadings[i] = readings[i];
    }
    // Sort the array using qsort
    qsort(sortedReadings, size, sizeof(float), compareFloats);
    // Choose the median value as the filtered reading
    const int MedianIndex = size / 2;
    return sortedReadings[MedianIndex];
}

void loop() {
    static float humidityReadings[ArraySize]; // Static to preserve values between calls
    static int index = 0;
    
    Serial.println("MEASURING HUMIDITY...");
    int rawHumidityValue = analogRead(HumidityPin);
    humidityReadings[index % ArraySize] = getHumidity(rawHumidityValue); // Use modulo to cycle through array indices
    
    if (index < ArraySize - 1) {
        displayWaitingMessage();
        delay(2000); // Wait before clearing the display
        clearDisplayMessage();
    }
    
    if (index >= ArraySize - 1) { // Only process once we have enough readings
        float averageHumidity = calculateWeightedAverage(humidityReadings, ArraySize);
        float filteredHumidity = applySaltAndPepperFilter(humidityReadings, ArraySize);
        
        lcd.clear(); // Clear the entire LCD screen
        
        Serial.print("Average Humidity: ");
        Serial.println(averageHumidity);
        Serial.print("Filtered Humidity: ");
        Serial.println(filteredHumidity);
        
        display(averageHumidity, filteredHumidity);
    }
    
    delay(2000);
    index++;
}