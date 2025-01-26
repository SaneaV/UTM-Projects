#include <Keypad.h>
#include <LiquidCrystal.h>

LiquidCrystal lcd(A5, A4, A3, A2, A1, A0);
int inputIndex = 0; // Для контроля ввода
int passwordIndex = 0; // Для контроля пароля
int currentState = 0; 
char keyPressed;
char inputPassword[5];
char storedPassword[5] = "1234";

const byte ROWS = 4;
const byte COLS = 4;
char keys[ROWS][COLS] = {
  {'1', '2', '3', 'A'},
  {'4', '5', '6', 'B'},
  {'7', '8', '9', 'C'},
  {'*', '0', '#', 'D'}
};
byte rowPins[ROWS] = {9, 8, 7, 6};
byte columnPins[COLS] = {5, 4, 3, 2};

Keypad keypad(makeKeymap(keys), rowPins, columnPins, ROWS, COLS);

const int GREEN_LED_PIN = 10;
const int RED_LED_PIN = 11;
const int BLUE_LED_PIN = 12;
const int PASSWORD_LENGTH = 4;
const int ENTER_PASSWORD_STATE = 0;
const int CHANGE_PASSWORD_STATE = 1;
const int CONFIRM_PASSWORD_STATE = 2;

void setup() {
  lcd.begin(16, 2);
  Serial.begin(9600);
  pinMode(GREEN_LED_PIN, OUTPUT); // Зеленый LED
  pinMode(RED_LED_PIN, OUTPUT); // Красный LED
  pinMode(BLUE_LED_PIN, OUTPUT); // Синий LED
}

void loop() {
  Serial.print("PASSWORD: ");
  Serial.println(storedPassword);
  
  keyPressed = keypad.getKey();  // Чтение с клавиатуры

  displayState();

  // BACKSPACE
  if (keyPressed == 'B') {
    handleBackspace();
  }

  // CHANGE PASSWORD
  if (keyPressed == 'C' && inputIndex == 0) {
    currentState = CONFIRM_PASSWORD_STATE;
    digitalWrite(BLUE_LED_PIN, HIGH);
  }

  // ENTER PASSWORD
  if (keyPressed && keyPressed != 'B' && keyPressed != 'C' && keyPressed != 'D' && keyPressed != 'A') {
    handleKeyPress();
  }
}

void displayState() {
  if (currentState == CHANGE_PASSWORD_STATE) {
    inputIndex = 0;
    lcd.setCursor(0, 0);
    lcd.print("Change Password:");
    printMaskedPassword(passwordIndex);
  } else if (currentState == ENTER_PASSWORD_STATE) {
    lcd.setCursor(0, 0);
    lcd.print("Enter Password: ");
    printMaskedPassword(inputIndex);
  } else if (currentState == CONFIRM_PASSWORD_STATE) {
    lcd.setCursor(0, 0);
    lcd.print("Confirm Pswd:   ");
    printMaskedPassword(inputIndex);
  }
}

void printMaskedPassword(int index) {
  for (int i = index; i < PASSWORD_LENGTH; i++) {
    lcd.setCursor(i, 1);
    lcd.print("-");
  }
}

void handleBackspace() {
  if ((currentState == ENTER_PASSWORD_STATE || currentState == CONFIRM_PASSWORD_STATE) && inputIndex != 0) {
    inputIndex--;
  } else if (currentState == CHANGE_PASSWORD_STATE && passwordIndex != 0) {
    passwordIndex--;
  }
}

void handleKeyPress() {
  if (currentState == CHANGE_PASSWORD_STATE) {
    Serial.println(keyPressed);
    lcd.setCursor(passwordIndex, 1);
    storedPassword[passwordIndex++] = keyPressed;
    lcd.print(keyPressed);
    Serial.println(" ");
    if (passwordIndex == PASSWORD_LENGTH) {
      delay(600);
      lcd.clear();
      lcd.print("PASSWORD CHANGED ");
      lcd.setCursor(0, 1);
      lcd.print("SUCCESSFULLY ");
      delay(1600);
      lcd.clear();
      passwordIndex = 0;
      digitalWrite(BLUE_LED_PIN, LOW);
      currentState = ENTER_PASSWORD_STATE;
    }
  } else if (currentState == ENTER_PASSWORD_STATE || currentState == CONFIRM_PASSWORD_STATE) {
    Serial.println(keyPressed);
    lcd.setCursor(inputIndex, 1);
    inputPassword[inputIndex++] = keyPressed;
    lcd.print(keyPressed);
    if (inputIndex == PASSWORD_LENGTH) {
      delay(400);
      if (!strcmp(inputPassword, storedPassword)) {
        lcd.clear();
        if (currentState == ENTER_PASSWORD_STATE) {
          lcd.print("!!! CORRECT !!!");
          digitalWrite(GREEN_LED_PIN, HIGH);
          for (int i = 3; i >= 0; i--) {
            lcd.setCursor(0, 1);
            lcd.print("Timer: ");
            lcd.print(i);
            delay(1000);
          }
          lcd.clear();
          inputIndex = 0;
          digitalWrite(GREEN_LED_PIN, LOW);
        } else {
          lcd.clear();
          lcd.print("CONFIRMATION   ");
          lcd.setCursor(0, 1);
          lcd.print("SUCCESSFUL     ");
          delay(600);
          lcd.clear();
          currentState = CHANGE_PASSWORD_STATE;
        }
      } else {
        lcd.clear();
        lcd.print("!!! WRONG !!!");
        digitalWrite(RED_LED_PIN, HIGH);
        for (int i = 3; i > 0; i--) {
          lcd.setCursor(0, 1);
          lcd.print("TIMER: ");
          lcd.print(i);
          delay(1000);
        }
        lcd.clear();
        inputIndex = 0;
        digitalWrite(RED_LED_PIN, LOW);
      }
    }
  }
}