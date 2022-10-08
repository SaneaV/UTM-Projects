#include <iostream>
#include <string.h>

using namespace std;

int main()
{
    char line[256];
    printf("Input something: ");
    gets(line);

    char symbol;
    printf("Input the special symbol: ");
    cin>>symbol;

    int n = 0;

    for (int i = 0; i < strlen(line); i++)
    {
        if(line[i]==symbol)
        {
            if(i>0)
            {
                if(line[i-1]==' ')
                    n++;
            }
            else if (i == 0)
            {
                n++;
            }
        }
    }

    cout<<"Number of words that start with \""<<symbol<<"\": "<<n;
}
