#include <Adafruit_LiquidCrystal.h>

#define SENSOR_PIN A0
const byte LcdAddress = 0; // Use 0 as specified
const byte LcdColumns = 16;
const byte LcdRows = 2;

// Constants for temperature calculation
const float REFERENCE_VOLTAGE = 5.0; // in volts
const float VOLTAGE_OFFSET = 500.0; // in millivolts
const float TEMPERATURE_SLOPE = 10.0; // in millivolts per degree Celsius

// Array size constants
const int ARRAY_SIZE = 5;

Adafruit_LiquidCrystal lcd(LcdAddress);

void setup() {
    pinMode(SENSOR_PIN, INPUT);
    Serial.begin(9600);
    lcd.begin(LcdColumns, LcdRows);
    lcd.setCursor(0, 0);
    lcd.print("Median = ");
    lcd.setCursor(0, 1);
    lcd.print("Spices = ");
}

float getTemperature() {
    float voltage = (analogRead(SENSOR_PIN) * (REFERENCE_VOLTAGE / 1023.0)) * 1000; // Convert to millivolts
    float temperature = (voltage - VOLTAGE_OFFSET) / TEMPERATURE_SLOPE; // Convert to Celsius
    
    Serial.print("Real time temperature = ");
    Serial.println(temperature);
    return temperature;
}

void shiftArray(float arr[], const int size) {
    for (int i = 0; i < size - 1; i++) {
        arr[i] = arr[i + 1];
    }
}

void display(float t1, float t2) {
    lcd.setCursor(10, 0);
    lcd.print(t1);
    lcd.setCursor(10, 1);
    lcd.print(t2);
}

void displayWaitingMessage() {
    lcd.setCursor(9, 0);
    lcd.print("Waiting");
    lcd.setCursor(9, 1);
    lcd.print("Waiting");
}

void clearDisplayMessage() {
    lcd.setCursor(9, 0);
    lcd.print("          "); // Clear "Waiting" message on line 0
    lcd.setCursor(9, 1);
    lcd.print("          "); // Clear "Waiting" message on line 1
}

float calculateWeightedAverage(float temps[], const int size) {
    const int WEIGHT = 2;
    float sum = 0;
    float totalWeight = 0;
    for (int i = 0; i < size; i++) {
        sum += temps[i] * WEIGHT;
        totalWeight += WEIGHT;
    }
    return sum / totalWeight;
}

int compareFloats(const void* a, const void* b) {
    float fa = *(const float*)a;
    float fb = *(const float*)b;
    return (fa > fb) - (fa < fb);
}

float applySaltAndPepperFilter(float temps[], const int size) {
    float sortedTemps[size];
    for (int i = 0; i < size; i++) {
        sortedTemps[i] = temps[i];
    }
    qsort(sortedTemps, size, sizeof(float), compareFloats);
    const int MEDIAN_INDEX = size / 2;
    return sortedTemps[MEDIAN_INDEX];
}

void loop() {
    static float temperatures[ARRAY_SIZE]; // Static to preserve values between calls
    static int index = 0;
    
    Serial.println("MEASURING TEMPERATURE...");
    temperatures[index % ARRAY_SIZE] = getTemperature(); // Use modulo to cycle through array indices
    
    if (index < ARRAY_SIZE - 1) {
        displayWaitingMessage();
        delay(2000); // Wait before clearing the display
        clearDisplayMessage();
    }
    
    if (index >= ARRAY_SIZE - 1) { // Only process once we have enough readings
        float averageTemperature = calculateWeightedAverage(temperatures, ARRAY_SIZE);
        float filteredTemperature = applySaltAndPepperFilter(temperatures, ARRAY_SIZE);
        
        Serial.print("Average Temperature: ");
        Serial.println(averageTemperature);
        Serial.print("Filtered Temperature: ");
        Serial.println(filteredTemperature);
        
        display(averageTemperature, filteredTemperature);
    }
    
    delay(2000);
    index++;
}