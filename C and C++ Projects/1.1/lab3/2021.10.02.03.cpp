#include <iostream>
#include <math.h>
#include <time.h>

using namespace std;

int main()
{
    srand(time(NULL));
    const int n = 10;
    int Array[n][n];
    int localMinimum = 0;
    int sum = 0;

    cout << "Matrix:" <<endl;
    for(int i = 0; i < n; i++)
    {
        for(int j = 0; j < n; j++)
        {
            Array[i][j] = rand() % 10;
            cout<<Array[i][j]<<"  ";
        }
        cout<<endl;
    }

    cout<<"\n\nMinimum:"<<endl;
    for(int i = 0; i < n; i++)
    {
        for(int j = 0; j < n; j++)
        {
            if(i==0 && j==0 &&
               (Array[i][j] < Array[i][j+1]) &&
               (Array[i][j] < Array[i+1][j]) &&
               (Array[i][j] < Array[i+1][j+1]))
            {
                cout<<"String "<<i+1<<" column "<<j+1<<" min = "<<Array[i][j]<<endl;
                localMinimum++;
            }

            else if(i==n-1 && j==0 &&
                    (Array[i][j] < Array[i-1][j]) &&
                    (Array[i][j] < Array[i][j+1]) &&
                    (Array[i][j] < Array[i-1][j+1]))
            {
                cout<<"String "<<i+1<<" column "<<j+1<<" min = "<<Array[i][j]<<endl;
                localMinimum++;
            }

            else if(i==0 && j==n-1 &&
                    (Array[i][j] < Array[i][j-1]) &&
                    (Array[i][j] < Array[i+1][j]) &&
                    (Array[i][j] < Array[i+1][j-1]))
            {
                cout<<"String "<<i+1<<" column "<<j+1<<" min = "<<Array[i][j]<<endl;
                localMinimum++;
            }

            else if(i==n-1 && j==n-1 &&
                    (Array[i][j] < Array[i-1][j]) &&
                    (Array[i][j] < Array[i][j-1]) &&
                    (Array[i][j] < Array[i-1][j-1]))
            {
                cout<<"String "<<i+1<<" column "<<j+1<<" min = "<<Array[i][j]<<endl;
                localMinimum++;
            }

            else if(i==0 && j>0 && j<n-1 &&
                    (Array[i][j] < Array[i][j-1]) &&
                    (Array[i][j] < Array[i][j+1]) &&
                    (Array[i][j] < Array[i][j+1]) &&
                    (Array[i][j] < Array[i+1][j-1]) &&
                    (Array[i][j] < Array[i+1][j+1]))
            {
                cout<<"String "<<i+1<<" column "<<j+1<<" min = "<<Array[i][j]<<endl;
                localMinimum++;
            }

            else if(i==n-1 && j>0 && j<n-1 &&
                    (Array[i][j] < Array[i][j-1]) &&
                    (Array[i][j] < Array[i][j+1]) &&
                    (Array[i][j] < Array[i][j-1]) &&
                    (Array[i][j] < Array[i-1][j-1]) &&
                    (Array[i][j] < Array[i-1][j+1]))
            {
                cout<<"String "<<i+1<<" column "<<j+1<<" min = "<<Array[i][j]<<endl;
                localMinimum++;
            }

            else if(i>0 && i<n-1 && j==0 &&
                    (Array[i][j] < Array[i-1][j]) &&
                    (Array[i][j] < Array[i+1][j]) &&
                    (Array[i][j] < Array[i][j+1]) &&
                    (Array[i][j] < Array[i-1][j+1]) &&
                    (Array[i][j] < Array[i+1][j+1]))
            {
                cout<<"String "<<i+1<<" column "<<j+1<<" min = "<<Array[i][j]<<endl;
                localMinimum++;
            }

            else if(i>0 && i<n-1 && j==n-1 &&
                    (Array[i][j] < Array[i][j-1]) &&
                    (Array[i][j] < Array[i-1][j]) &&
                    (Array[i][j] < Array[i-1][j]) &&
                    (Array[i][j] < Array[i-1][j-11]) &&
                    (Array[i][j] < Array[i+1][j-11]))
            {
                cout<<"String "<<i+1<<" column "<<j+1<<" min = "<<Array[i][j]<<endl;
                localMinimum++;
            }

            else if((Array[i][j] < Array[i-1][j]) &&
                    (Array[i][j] < Array[i+1][j]) &&
                    (Array[i][j] < Array[i][j-1]) &&
                    (Array[i][j] < Array[i][j+1]) &&
                    (Array[i][j] < Array[i-1][j-1]) &&
                    (Array[i][j] < Array[i+1][j+1]) &&
                    (Array[i][j] < Array[i+1][j-1]) &&
                    (Array[i][j] < Array[i+1][j+1]))
            {
                cout<<"String "<<i+1<<" column "<<j+1<<" min = "<<Array[i][j]<<endl;
                localMinimum++;
            }
        }
    }
    cout <<"\n\nFound "<<localMinimum<<" minimums";

    for(int i = 0; i < n; i++)
    {
        for(int j = 0; j < n; j++)
        {
            if(i<j)
            {
                sum += abs(Array[i][j]);
            }
        }
    }
    cout<<"\n\nSum of the elements above the main diagonal = "<<sum<<"\n\n";
}
