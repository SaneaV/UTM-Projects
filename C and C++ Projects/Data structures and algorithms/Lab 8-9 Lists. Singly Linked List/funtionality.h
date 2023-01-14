#include <iostream>

using namespace std;

struct Country {
    char name[50];
    char capital[50];
    int area;
    int population;
    int grossDomesticProduct;
};

struct List {
    struct Country *country;
    List *next;
};

List *createList();

List *addCountry(List *bg);

Country *inputCountryData();

List *getPenultimateCountry(List *bg);

int getLength(List *bg);

List *addAtPosition(List *bg);

List *addAtFirstPosition(List *bg, struct Country *country);

void showCountries(List *cr);

List *deleteCountry(List *bg);

void getAddressOfTheLastElement(List *bg);

void findCountry(List *bg);

void showCountry(Country *cntr);

List *editCountry(List *bg);

List *swapCountries(List *bg);

List *swapCountries(List *bg, char *fCountryName, char *sCountryName);

List *split(List *&bg, List *&bg2);

List *selectSplitList(List *&bg, List *&bg2);

List *unite(List *&bg, List *&bg2);

void eliminateCountries(List *&bg, List *&bg2);

void eliminateList(List *&bg);

List *sort(List *bg);

void writeToFile(List *bg);

List *readFromFile(List *bg);