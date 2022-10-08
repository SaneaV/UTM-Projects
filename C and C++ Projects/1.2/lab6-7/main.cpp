#include <iostream>
#include <windows.h>
#include "funct.h"

using namespace std;

int main()
{
    int menuItem;
    bool isActive = true;
    struct country *cntr = NULL;

    do
    {
        system("cls");
        cout<<"\t\t\tMenu:\n";
        cout<<"1. Add new country\n";
        cout<<"2. Show countries\n";
        cout<<"3. Write data to a file\n";
        cout<<"4. Read data from a file\n";
        cout<<"5. Sort countries\n";
        cout<<"6. Find a country\n";
        cout<<"7. Edit country data\n";
        cout<<"8. Clear all data\n";
        cout<<"9. Exit\n";
        cout<<"\n\nChoose a number from the menu: ";
        cin>>menuItem;

        system("cls");

        switch(menuItem)
        {
        case 1:
        {
            addNewElement(cntr);
            break;
        }
        case 2:
        {
            showCountries(cntr);
            system("PAUSE");
            break;
        }
        case 3:
        {
            writeToFile(cntr);
            system("PAUSE");
            break;
        }
        case 4:
        {
            readFromFile(cntr);
            system("PAUSE");
            break;
        }
        case 5:
        {
            sortCountries(cntr);
            system("PAUSE");
            break;
        }
        case 6:
        {
            findCountry(cntr);
            system("PAUSE");
            break;
        }
        case 7:
        {
            editCountry(cntr);
            system("PAUSE");
            break;
        }
        case 8:
        {
            clearAllData(cntr);
            system("PAUSE");
            break;
        }
        case 9:
        {
            isActive = false;
            if(cntr != NULL)
            {
                clearAllData(cntr);

            }
            system("PAUSE");
            break;
        }
        default:
        {
            cout<<"No such item in menu. Please, repeat your input";
        }
        }
    }
    while (isActive);

    return 0;

}
