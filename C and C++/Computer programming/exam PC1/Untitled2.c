#include <stdio.h>

int main()
{
    int N = 5;
    int A[5] = {1, -5, 0, 2, 5};

    int minElement = 0;

    for(int i=0; i<N; i++)
    {
        if(A[i]>0 && A[i]<A[minElement])
        {
            minElement = i;
        }
    }

    printf("Min element is A[%d] = %d", minElement, A[minElement]);

    getch();
    return 0;
}

