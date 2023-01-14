#include <iostream>

using namespace std;

int main()
{
    int h;
    char even, noteven;

    cout << "Input height: ";
    cin >> h;

    if(h % 2 == 0)
    {
        even = '*';
        noteven = '+';
    }
    else
    {
        even = '+';
        noteven = '*';
    }

    for (int i = 0; i < h; i++)
    {
        for (int j = 1; j < h - i; j++)
        {
            cout << " ";
        }

        for (int j = h - 2 * i; j <= h; j++)
        {
            if (j % 2 == 0)
            {
                cout<<even;
            }
            else
            {
                cout<<noteven;
            }
        }

        cout << endl;
    }
    return 0;
}
