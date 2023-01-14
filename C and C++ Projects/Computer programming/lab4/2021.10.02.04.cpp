#include<iostream>
#include <time.h>

using namespace std;

int** create(int n, int m)
{
    int **A;
    A = new int *[n];
    for (int i = 0; i < n; i++)
    {
        A[i] = new int [m];
    }
    return A;
}

void populate(int **Matrix, int n, int m)
{
    for(int i=0; i<n; i++)
    {
        for(int j=0; j<m; j++)
        {
            cout<<"Input element: ";
            cin>>Matrix[i][j];
        }
    }
}

void populateRandom(int **Matrix, int n, int m)
{
    srand(time(0));
    for(int i = 0; i < n; i++ )
    {
        for(int j = 0;  j < m;  j++ )
        {
            Matrix[i][j] = rand()%10;
        }
    }
}

void showMatrix(int **Matrix, int n, int m)
{
    for(int i = 0; i < n; i++ )
    {
        for(int j = 0;  j < m;  j++ )
        {
            cout<<Matrix[i][j]<<"\t";
        }
        cout << endl;
    }
}

void sortMatrix(int **Matrix, int n, int m)
{
    for (int i = 0; i < n; i++)
    {
        if(i%2!=0)
        {
            for (int j = 0; j < m; j++)
            {
                for (int k = 0; k < m; k++)
                {
                    if (Matrix[j][i] > Matrix[k][i])
                    {
                        int temp = Matrix[k][i];
                        Matrix[k][i] = Matrix[j][i];
                        Matrix[j][i] = temp;
                    }

                }
            }
        }

    }
}

void freeUpMemory(int **Matrix, int &n, int &m)
{
    free(Matrix);
    Matrix = NULL;
    n = 0;
    m = 0;
}

int main()
{
    bool isWorking = true;
    int **matrix;
    int m=0, n=0;

    while(isWorking)
    {
        int item;

        cout<<"1. Create matrix."<<endl;
        cout<<"2. Populate matrix."<<endl;
        cout<<"3. Populate matrix with random elements."<<endl;
        cout<<"4. Sort matrix."<<endl;
        cout<<"5. Show matrix."<<endl;
        cout<<"6. Free up memory."<<endl;
        cout<<"7. Exit."<<endl;
        cout<<"Chose the item: ";
        cin>>item;

        switch(item)
        {
        case 1:
        {
            cout<<"Input n: ";
            cin>>n;
            cout<<"Input m: ";
            cin>>m;

            matrix = create(n, m);
            cout<<"\n\n";
            break;
        }
        case 2:
        {
            if(m==0 && n==0)
            {
                cout<<"Matrix doesn't exist"<<endl;
            }
            else
            {
                populate(matrix, n, m);
                                cout<<"\n\n";
            }
            break;
        }
        case 3:
        {
            if(m==0 && n==0)
            {
                cout<<"Matrix doesn't exist"<<endl;
                cout<<"\n\n";
            }
            else
            {
                populateRandom(matrix, n, m);
                cout<<"\n\n";
            }
            break;
        }
        case 4:
        {
            if(m==0 && n==0)
            {
                cout<<"Matrix doesn't exist"<<endl;
                cout<<"\n\n";
            }
            else
            {
                sortMatrix(matrix, n, m);
                cout<<"\n\n";
            }
            break;
        }
        case 5:
        {
            if(m==0 && n==0)
            {
                cout<<"Matrix doesn't exist"<<endl;
                cout<<"\n\n";
            }
            else
            {
                showMatrix(matrix, n, m);
                cout<<"\n\n";
            }
            break;
        }
        case 6:
        {
            if(m==0 && n==0)
            {
                cout<<"Matrix doesn't exist"<<endl;
                cout<<"\n\n";
            }
            else
            {
                freeUpMemory(matrix, n, m);
                cout<<"\n\n";
            }
            break;
        }
        case 7:
        {
            isWorking = false;
            break;
        }
        }
    }
}
