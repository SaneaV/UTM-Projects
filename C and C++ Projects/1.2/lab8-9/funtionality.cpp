#include <iostream>
#include <iomanip>
#include <cstring>
#include <fstream>
#include "funtionality.h"

using namespace std;

List *createList() {
    return new List;
}

List *addCountry(List *bg) {
    struct Country *country = inputCountryData();

    if (bg == nullptr) {
        cout << "List was created. Country was added!\n\n";
        return addAtFirstPosition(bg, country);
    } else {
        List *cr = new List;
        List *prv;

        if (getLength(bg) == 1) {
            prv = bg;
        } else {
            prv = getPenultimateCountry(bg);
        }

        cr->country = country;
        cr->next = nullptr;
        prv->next = cr;
        cout << "Country was added!\n\n";
        return bg;
    }
}

List *getPenultimateCountry(List *cr) {
    if (cr == nullptr) {
        cout << "The list of countries is empty\n\n";
    } else {
        if (getLength(cr) == 1) {
            return cr;
        }

        while (cr != nullptr) {
            cr = cr->next;

            if (cr->next == nullptr) {
                return cr;
            }
        }
    }

    return nullptr;
}

int getLength(List *cr) {
    if (cr == nullptr) {
        cout << "The list of countries is empty\n\n";
    } else {
        int k = 0;
        while (cr != nullptr) {
            k++;
            cr = cr->next;
        }
        return k;
    }

    return 0;
}

Country *inputCountryData() {
    auto *country = new Country;

    cout << "Input country name: ";
    cin >> country->name;

    cout << "Input country capital: ";
    cin >> country->capital;

    cout << "Input country area: ";
    cin >> country->area;

    cout << "Input country population: ";
    cin >> country->population;

    cout << "Input country gross domestic product: ";
    cin >> country->grossDomesticProduct;

    return country;
}

List *addAtPosition(List *bg) {
    int position;
    int lastPosition = getLength(bg);

    if (lastPosition == 0) {
        cout << "The element will be added at first position\n\n";
    } else {
        do {
            cout << "Input position: ";
            cin >> position;
            if (position <= 0 || position > lastPosition + 1) {
                cout << "Position should be between 1 and " << lastPosition + 1 << "\n\n";
            }
        } while (position <= 0 || position > lastPosition + 1);
    }

    struct Country *country = inputCountryData();

    if (bg == nullptr) {
        bg = addAtFirstPosition(bg, country);
        cout << "List was created. Country was added!\n\n";
        return bg;
    } else if (position == 1) {
        List *newElement = new List;
        newElement->country = country;
        newElement->next = bg;
        cout << "Country was added!\n\n";
        return newElement;
    } else if (position == lastPosition + 1) {
        List *prv = getPenultimateCountry(bg);
        List *newElement = new List;
        newElement->country = country;
        newElement->next = nullptr;
        prv->next = newElement;
        cout << "Country was added!\n\n";
        return bg;
    } else {
        List *cr = bg, *prv = bg;
        int k = 1;
        while (bg != nullptr) {
            if (k == position) {
                List *newElement = new List;
                newElement->country = country;
                newElement->next = cr;
                prv->next = newElement;
                return bg;
            }

            k++;
            prv = cr;
            cr = cr->next;
        }
    }
}

List *addAtFirstPosition(List *bg, struct Country *country) {
    bg = createList();
    bg->country = country;
    bg->next = nullptr;
    return bg;
}

void showCountries(List *cr) {
    if (cr == nullptr) {
        cout << "The list of countries is empty\n\n";
    } else {
        cout << "----------------------------------------------------------------------------\n";
        cout << left << setw(9) << "Country\t\t" << setw(18) << "Capital\t" << setw(13) << "Area\t" << setw(10)
             << "Population\t" << setw(8) << "GDP\t" << endl;
        cout << "----------------------------------------------------------------------------\n";

        while (cr != nullptr) {
            cout << left << setw(10) << cr->country->name << "\t" << setw(10) << cr->country->capital << "\t" << right
                 << setw(10) << cr->country->area << "\t" <<
                 setw(10) << cr->country->population << "\t" << setw(10) << cr->country->grossDomesticProduct << endl;
            cr = cr->next;
        }

        cout << "----------------------------------------------------------------------------\n";

    }
}

List *deleteCountry(List *bg) {
    char searchParam[50];
    cout << "Input country name or capital name: ";
    cin >> searchParam;

    if (strcmp(bg->country->name, searchParam) == 0 || strcmp(bg->country->capital, searchParam) == 0) {
        List *newBegin = bg->next;
        delete[] bg->country;
        bg->country = nullptr;
        delete[] bg;
        bg = nullptr;
        cout << "Country was deleted\n\n" << endl;
        return newBegin;
    } else {
        List *cr = bg->next, *prv = bg;

        while (cr != nullptr) {
            if (strcmp(cr->country->name, searchParam) == 0 || strcmp(cr->country->capital, searchParam) == 0) {
                prv->next = cr->next;
                delete[] cr->country;
                cr->country = nullptr;
                delete[] cr;
                cr = nullptr;
                cout << "Country was deleted\n\n" << endl;
                return bg;
            }

            prv = cr;
            cr = cr->next;
        }
    }

    cout << "Country was not found\n\n" << endl;
    return bg;
}


void getAddressOfTheLastElement(List *bg) {
    if (getLength(bg) != 0) {
        List *cr = bg;

        while (cr != nullptr) {
            if (cr->next == nullptr) {
                cout << "The address of the last element is: " << cr << "\n\n";

            }
            cr = cr->next;
        }
    }

}

void findCountry(List *bg) {
    if (bg == nullptr) {
        cout << "The list of countries is empty\n\n";
    } else {
        char searchParam[50];
        cout << "Input country name or capital name: ";
        cin >> searchParam;
        List *cr = bg;

        while (cr != nullptr) {
            if (strcmp(cr->country->name, searchParam) == 0 || strcmp(cr->country->capital, searchParam) == 0) {
                showCountry(cr->country);
                return;
            }

            cr = cr->next;
        }

        cout << "Country was not found\n\n" << endl;
    }
}

void showCountry(Country *country) {
    cout << "----------------------------------------------------------------------------\n";
    cout << left << setw(9) << "Country\t\t" << setw(18) << "Capital\t" << setw(13) << "Area\t" << setw(10)
         << "Population\t" << setw(8) << "GDP\t" << endl;
    cout << "----------------------------------------------------------------------------\n";

    cout << left << setw(10) << country->name << "\t" << setw(10) << country->capital << "\t" << right << setw(10)
         << country->area << "\t" <<
         setw(10) << country->population << "\t" << setw(10) << country->grossDomesticProduct << endl;

    cout << "----------------------------------------------------------------------------\n";
}

List *editCountry(List *bg) {
    if (bg == nullptr) {
        cout << "The list of countries is empty\n\n";
    } else {
        char searchParam[50];
        cout << "Input country name or capital name: ";
        cin >> searchParam;
        List *cr = bg;

        while (cr != nullptr) {
            if (strcmp(cr->country->name, searchParam) == 0 || strcmp(cr->country->capital, searchParam) == 0) {
                showCountry(cr->country);
                delete[] cr->country;
                cr->country = nullptr;
                struct Country *newCountry = inputCountryData();
                cr->country = newCountry;
                return bg;
            }

            cr = cr->next;
        }

        cout << "Country was not found\n\n" << endl;
    }

    return bg;
}

List *swapCountries(List *bg) {

    if (bg == nullptr) {
        cout << "The list of countries is empty\n\n";
    } else {
        char firstCountry[50];
        char secondCountry[50];
        cout << "Input first country name or capital name: ";
        cin >> firstCountry;
        cout << "Input second country name or capital name: ";
        cin >> secondCountry;
        List *cr = bg;

        List *fCountry = nullptr;
        List *sCountry = nullptr;

        while (cr->next != nullptr) {
            if (strcmp(cr->next->country->name, firstCountry) == 0 ||
                strcmp(cr->next->country->capital, firstCountry) == 0) {
                fCountry = cr;
            }
            if (strcmp(cr->next->country->name, secondCountry) == 0 ||
                strcmp(cr->next->country->capital, secondCountry) == 0) {
                sCountry = cr;
            }

            cr = cr->next;
        }

        if (sCountry == nullptr && fCountry != nullptr) {
            sCountry = fCountry;
            fCountry = nullptr;

            char tempCountry[50];
            strcpy(tempCountry, firstCountry);
            strcpy(firstCountry, secondCountry);
            strcpy(secondCountry, tempCountry);

        } else if (sCountry->next == fCountry) {
            List *fTemp = fCountry;
            fCountry = sCountry;
            sCountry = fTemp;
        }

        if (fCountry != nullptr && sCountry != nullptr) {
            if (fCountry->next == sCountry) {
                List *fTemp = fCountry->next;
                fCountry->next = sCountry->next;
                fTemp->next = sCountry->next->next;
                fCountry->next->next = fTemp;
            } else {
                List *fTemp = fCountry->next->next;
                List *sTemp = sCountry->next->next;

                List *tTemp = fCountry->next;
                fCountry->next = sCountry->next;
                sCountry->next = tTemp;

                fCountry->next->next = fTemp;
                sCountry->next->next = sTemp;
            }
        } else if (fCountry == nullptr &&
                   (strcmp(bg->country->name, firstCountry) == 0 || strcmp(bg->country->capital, firstCountry) == 0) &&
                   sCountry != nullptr) {
            List *fTemp = bg;
            List *sTemp = sCountry->next;
            List *tTemp = sTemp->next;
            sTemp->next = fTemp->next;
            fTemp->next = tTemp;
            sTemp->next->next = fTemp;

            bg = sTemp;
        } else {
            cout << "Countries were not found\n\n" << endl;
        }

    }

    return bg;
}

List *split(List *&bg, List *&bg2) {
    if (bg == nullptr) {
        cout << "The list of countries is empty\n\n";
    } else {
        int length = getLength(bg);

        if (bg2 != nullptr) {
            int selection;
            cout << "Your list is already divided, do you want to change main list?" << endl;
            return selectSplitList(bg, bg2);
        } else if (length != 1) {
            List *cr = bg;

            if (length == 2) {
                bg2 = cr->next;
                cr->next = nullptr;
            } else {
                for (int i = 0; i < (length - 1) / 2; i++) {
                    cr = cr->next;
                }

                bg2 = cr->next;
                cr->next = nullptr;
            }

            cout << "The list was split" << endl;
            return selectSplitList(bg, bg2);

        } else {
            cout << "We cannot split list with 1 element" << endl;
            return bg;
        }
    }
    return nullptr;
}

List *selectSplitList(List *&bg, List *&bg2) {
    int selection;
    cout << "1. Select first list as main." << endl;
    cout << "2. Select second list as main." << endl;

    cout << "List 1: " << endl;
    showCountries(bg);

    cout << "\n\n";

    cout << "List 2: " << endl;
    showCountries(bg2);

    do {
        cout << "Make your choice: ";
        cin >> selection;
    } while (selection < 1 || selection > 2);

    if (selection == 2) {
        List *temp = bg;
        bg = bg2;
        bg2 = temp;

        return bg;
    }
    return bg;
}

List *unite(List *&bg, List *&bg2) {
    if (bg == nullptr) {
        cout << "The list of countries is empty\n\n";
    }
    if (bg2 == nullptr) {
        cout << "The list of countries is already united\n\n";
        return bg;
    } else {
        List *cr = bg;

        while (cr->next != nullptr) {
            cr = cr->next;
        }

        cr->next = bg2;
        bg2 = nullptr;
        return bg;
    }
}

void writeToFile(List *bg) {
    if (bg == nullptr) {
        cout << "The list of countries is empty\n\n";
    } else {
        ofstream file;
        file.open("countries.txt");
        file << getLength(bg) << "\n";

        List *cr = bg;
        while (cr != nullptr) {
            file << cr->country->name << " " << cr->country->capital << " " << cr->country->area << " "
                 << cr->country->population << " " << cr->country->grossDomesticProduct << "\n";
            cr = cr->next;
        }

        cout << "Successfully wrote to countries.txt" << endl;
        file.close();
    }
}

List *readFromFile(List *bg) {
    if (bg != nullptr) {
        int choice;
        cout << "Your list isn't empty. Do you want to rewrite it?" << endl;
        cout << "1. Yes" << endl;
        cout << "2. No" << endl;
        cout << "Input your choice: ";
        cin >> choice;

        if (choice == 2) {
            return bg;
        }
    }

    ifstream file("countries.txt");
    if (file.is_open()) {
        int numberOfCountries = 0;
        file >> numberOfCountries;
        if (numberOfCountries == 0) {
            cout << "Empty file. Returned old list of countries." << endl;
            return bg;
        } else {
            if (bg != nullptr) {
                eliminateList(bg);
            }
            List *prv;

            for (int i = 0; i < numberOfCountries; i++) {
                auto *country = new Country;
                file >> country->name >> country->capital >> country->area >> country->population
                     >> country->grossDomesticProduct;

                List *cr = new List;
                if (i == 0) {
                    bg = new List;
                    bg->country = country;
                    bg->next = nullptr;
                    cr = bg;
                } else {
                    cr->country = country;
                    cr->next = nullptr;
                    prv->next = cr;
                }
                prv = cr;
            }
            cout << "Successfully read from countries.txt" << endl;

            file.close();
            return bg;
        }
    } else {
        cout << "There is a problem with countries.txt. We cannot read from this file" << endl;
        cout << "Returned old list of countries." << endl;
        return bg;
    }
}

List *sort(List *bg) {
    if (bg == nullptr) {
        cout << "The list of countries is empty\n\n";
    } else if (getLength(bg) == 1) {
        return bg;
    } else {
        List *cr = bg->next;
        List *prv = bg;
        bool sorted = false;

        while (!sorted) {
            sorted = true;
            while (cr != nullptr) {
                if (strcmp(cr->country->name, prv->country->name) < 0) {
                    sorted = false;
                    bg = swapCountries(bg, cr->country->name, prv->country->name);
                }
                prv = cr;
                cr = cr->next;
            }
            cr = bg->next;
            prv = bg;
        }
    }
    return bg;
}

void eliminateCountries(List *&bg, List *&bg2) {
    if (bg == nullptr) {
        cout << "The list of countries is empty\n\n";
    } else {
        eliminateList(bg);
        if (bg2 != nullptr) { eliminateList(bg2); }
        cout << "The list was eliminated" << endl;
    }
}

void eliminateList(List *&bg) {
    List *cr = bg;
    List *prv;
    while (cr->next != nullptr) {
        prv = cr;
        cr = cr->next;
        delete[] prv->country;
        prv->country = nullptr;
        delete[] prv;
        prv = nullptr;
    }

    bg = nullptr;
}

List *swapCountries(List *bg, char *fCountryName, char *sCountryName) {
    char firstCountry[50];
    char secondCountry[50];
    strcpy(firstCountry, fCountryName);
    strcpy(secondCountry, sCountryName);

    List *cr = bg;

    List *fCountry = nullptr;
    List *sCountry = nullptr;

    while (cr->next != nullptr) {
        if (strcmp(cr->next->country->name, firstCountry) == 0 ||
            strcmp(cr->next->country->capital, firstCountry) == 0) {
            fCountry = cr;
        }
        if (strcmp(cr->next->country->name, secondCountry) == 0 ||
            strcmp(cr->next->country->capital, secondCountry) == 0) {
            sCountry = cr;
        }

        cr = cr->next;
    }

    if (sCountry == nullptr && fCountry != nullptr) {
        sCountry = fCountry;
        fCountry = nullptr;

        char tempCountry[50];
        strcpy(tempCountry, firstCountry);
        strcpy(firstCountry, secondCountry);
        strcpy(secondCountry, tempCountry);
    } else if (sCountry->next == fCountry) {
        List *fTemp = fCountry;
        fCountry = sCountry;
        sCountry = fTemp;
    }

    if (fCountry != nullptr && sCountry != nullptr) {
        if (fCountry->next == sCountry) {
            List *fTemp = fCountry->next;
            fCountry->next = sCountry->next;
            fTemp->next = sCountry->next->next;
            fCountry->next->next = fTemp;
        }
    } else if (fCountry == nullptr &&
               (strcmp(bg->country->name, firstCountry) == 0 || strcmp(bg->country->capital, firstCountry) == 0) &&
               sCountry != nullptr) {
        List *fTemp = bg;
        List *sTemp = sCountry->next;
        List *tTemp = sTemp->next;
        sTemp->next = fTemp->next;
        fTemp->next = tTemp;
        sTemp->next->next = fTemp;

        bg = sTemp;
    }

    return bg;
}