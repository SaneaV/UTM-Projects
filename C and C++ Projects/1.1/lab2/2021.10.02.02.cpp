#include <iostream>
#include <math.h>

using namespace std;

void findMinimalElement(int n, int Array[])
{
    int iMin = 0, minElement, sum=0;
    minElement = Array[0];

    for(int i=0; i<n; i++)
    {
        if(abs(Array[i])<abs(minElement))
        {
            minElement = Array[i];
            iMin = i;
        }
    }

    cout<<"\n\nMinimal is Matrix["<<iMin+1<<"] = "<<minElement<<endl;
}

void findSumOfElementsAfterFirstZero(int n, int Array[])
{
    int sum = 0;
    bool isZeroFind = false;

    for(int i=0; i<n; i++)
    {
        if(isZeroFind)
        {
            sum += abs(Array[i]);
        }
        else if(Array[i] == 0)
        {
            isZeroFind = true;
        }
    }

    cout<<"\nSum of elements after first zero: "<<sum<<"\n\n";
}

void showArray(int n, int Array[])
{
    for(int i=0; i<n; i++)
    {
        cout<<Array[i]<<" ";
    }
    cout<<endl;
}

void sortArrayByPosition(int n, int Array[])
{
    cout<<"Initial array: ";
    showArray(n, Array);

    for(int i = 0; i < n/2; ++i)
    {
        int t = *(Array + i);
        for(int j = 0; j < n - i - 1; ++j)
        {
            *(Array + i + j) = *(Array + i + j + 1);
        }
        *(Array + n - 1) = t;
    }

    cout<<"Sorted array: ";
    showArray(n, Array);
}

int main()
{
    int n;

    cout<<"Input n = ";
    cin>>n;

    int *Array = new int[n];

    cout<<"Input array: "<<endl;
    for(int i=0; i<n; i++)
    {
        cout<<"Input element "<<i+1<<": ";
        cin>>Array[i];
    }

    findMinimalElement(n, Array);
    findSumOfElementsAfterFirstZero(n, Array);
    sortArrayByPosition(n, Array);
}
