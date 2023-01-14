#include <iostream>
#include <fstream>
#include <string.h>
#include <iomanip>
#include "funct.h"

using namespace std;

int numberOfCountries = 0;
int structVolume = 10;

void addNewElement(country *&cntr)
{
    if(cntr==NULL)
    {
        cntr = new country[structVolume];
    }

    if(numberOfCountries == structVolume)
    {
        int newVolume = numberOfCountries + 10;
        country *newCntr = NULL;
        copyOf(cntr, newCntr, newVolume);
    }

    inputDates(cntr, numberOfCountries++);
}

void copyOf(country *cntr, country *&newCntr, int newVolume)
{
    newCntr = new country[newVolume];

    for(int i=0; i<numberOfCountries; i++)
    {
        newCntr[i] = cntr[i];
    }

    cntr = newCntr;
}

void inputDates(struct country *cntr, int lastElement)
{
    cout<<"Input country name: ";
    cin>>cntr[lastElement].name;

    cout<<"Input country capital: ";
    cin>>cntr[lastElement].capital;

    cout<<"Input country area: ";
    cin>>cntr[lastElement].area;

    cout<<"Input country population: ";
    cin>>cntr[lastElement].population;

    cout<<"Input country gross domestic product: ";
    cin>>cntr[lastElement].grossDomesticProduct;
}

void showCountries(struct country *cntr)
{
    if (numberOfCountries == 0)
    {
        cout<<"Add a country firstly"<<endl;
    }
    else
    {
        cout<<"----------------------------------------------------------------------------\n";
        cout<<left<<setw(9)<<"Country\t\t"<<setw(18)<<"Capital\t"<<setw(13)<<"Area\t"<<setw(10)<<"Population\t"<<setw(8)<<"GDP\t"<<endl;
        cout<<"----------------------------------------------------------------------------\n";

        for (int i = 0; i < numberOfCountries; i++)
        {
            cout<<left<<setw(10)<<cntr[i].name<<"\t"<<setw(10)<<cntr[i].capital<<"\t"<<right<<setw(10)<<cntr[i].area<<"\t"<<setw(10)<<
                cntr[i].population<<"\t"<<setw(10)<<cntr[i].grossDomesticProduct<<endl;
        }
        cout<<"----------------------------------------------------------------------------\n";
    }
}

void showCountry(struct country cntr)
{
    cout<<"----------------------------------------------------------------------------\n";
    cout<<setw(10)<<"Country\t"<<setw(10)<<"Capital\t"<<setw(10)<<"Area\t"<<setw(10)<<"Population\t"<<setw(10)<<"GDP\t\n";
    cout<<"----------------------------------------------------------------------------\n";

    cout<<setw(10)<<cntr.name<<"\t"<<setw(10)<<cntr.capital<<"\t"<<setw(10)<<cntr.area<<"\t"<<setw(10)<<
        cntr.population<<"\t"<<setw(10)<<cntr.grossDomesticProduct<<endl;

    cout<<"----------------------------------------------------------------------------\n";
}

void writeToFile(struct country *cntr)
{
    if (numberOfCountries == 0)
    {
        cout<<"Add a country firstly"<<endl;
    }
    else
    {
        ofstream file;
        file.open ("countries.txt");
        file<<numberOfCountries<<"\n";
        for(int i=0; i<numberOfCountries; i++)
        {
            file<<cntr[i].name<<" "<<cntr[i].capital<<" "<<cntr[i].area<<" "<<cntr[i].population<<" "<<cntr[i].grossDomesticProduct<<"\n";
        }
        cout<<"Successfully wrote to countries.txt"<<endl;
        file.close();
    }
}

void readFromFile(struct country *&cntr)
{
    ifstream file("countries.txt");
    if (file.is_open())
    {
        int oldNumberOfCountries = numberOfCountries;
        file>>numberOfCountries;
        if(numberOfCountries==0)
        {
            cout<<"Empty file"<<endl;
        }
        else
        {
            if(cntr == NULL)
            {
                cntr = new country[numberOfCountries+10];
            }
            else if(numberOfCountries != oldNumberOfCountries)
            {
                delete[] cntr;
                cntr = NULL;
                cntr = new country[numberOfCountries+10];
            }

            for (int i=0; i < numberOfCountries; i++)
            {
                file >> cntr[i].name >> cntr[i].capital >>cntr[i].area >> cntr[i].population >> cntr[i].grossDomesticProduct;
            }
            cout<<"Successfully read from countries.txt"<<endl;

            file.close();

        }
    }
}

void sortCountries(struct country *cntr)
{
    if (numberOfCountries == 0)
    {
        cout<<"Add a country firstly"<<endl;
    }
    else
    {
        int min_idx;

        for (int i = 0; i < numberOfCountries-1; i++)
        {
            min_idx = i;
            for (int j = i+1; j < numberOfCountries; j++)
            {
                if (strcmp(cntr[j].name, cntr[i].name) < 0)
                    min_idx = j;
            }

            swapCountries(&cntr[min_idx], &cntr[i]);
        }

        cout<<"Structure was sorted"<<endl;
    }
}

void swapCountries(struct country *firstParameter, struct country *secondParameter)
{
    country temp = *firstParameter;
    *firstParameter = *secondParameter;
    *secondParameter = temp;
}

void findCountry(struct country *cntr)
{
    if (numberOfCountries == 0)
    {
        cout<<"Add a country firstly"<<endl;
    }
    else
    {
        char searchParam[50];
        bool result = false;
        cout<<"Input country name or capital: ";
        cin>>searchParam;

        for(int i=0; i<numberOfCountries; i++)
        {
            if(strcmp(cntr[i].name, searchParam)==0 || strcmp(cntr[i].capital, searchParam)==0)
            {
                showCountry(cntr[i]);
                result = true;
            }
        }

        if(!result)
        {
            cout<<"Nothing was found!"<<endl;
        }
    }
}

void editCountry(struct country *cntr)
{
    if (numberOfCountries == 0)
    {
        cout<<"Add a country firstly"<<endl;
    }
    else
    {
        char searchParam[50];
        bool result = false;
        cout<<"Input country name or capital: ";
        cin>>searchParam;

        for(int i=0; i<numberOfCountries; i++)
        {
            if(strcmp(cntr[i].name, searchParam)==0 || strcmp(cntr[i].capital, searchParam)==0)
            {
                cout<<"Input country name: ";
                cin>>cntr[i].name;

                cout<<"Input country capital: ";
                cin>>cntr[i].capital;

                cout<<"Input country area: ";
                cin>>cntr[i].area;

                cout<<"Input country population: ";
                cin>>cntr[i].population;

                cout<<"Input country gross domestic product: ";
                cin>>cntr[i].grossDomesticProduct;

                result = true;
            }
        }

        if(!result)
        {
            cout<<"Nothing was found!"<<endl;
        }
    }
}

void clearAllData(struct country *&cntr)
{
    numberOfCountries = 0;
    delete[] cntr;
    cntr = NULL;

    cout<<"All data was deleted"<<endl;
}
