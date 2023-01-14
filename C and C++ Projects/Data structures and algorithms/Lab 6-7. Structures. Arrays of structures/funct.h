#include <iostream>

struct country
{
    char name[50];
    char capital[50];
    int area;
    int population;
    int grossDomesticProduct;
};

void addNewElement(struct country *&cntr);

void copyOf(struct country *cntr, struct country *&newCntr, int newVolume);

void inputDates(struct country *cntr, int lastElement);
void showCountries(struct country *cntr);
void showCountry(struct country cntr);

void writeToFile(struct country *cntr);
void readFromFile(struct country *&cntr);

void sortCountries(struct country *cntr);
void swapCountries(struct country *cntrOne, struct country *cntrTwo);

void findCountry(struct country *cntr);
void editCountry(struct country *cntr);

void clearAllData(struct country *&cntr);
