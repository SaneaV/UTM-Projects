#include <stdio.h>

const int N = 10;

int main()
{
    int i, A[N];

    for(int i=0; i<N; i++)
    {
        printf("Input A[%d] = ", i);
        scanf("%d", &A[i]);
    }

    for(int i=0; i<N; i++)
    {
        A[i]*=2;
    }

    printf("\nResult\n");

    for(int i=0; i<N; i++)
    {
        printf("%d ", A[i]);
    }

    getchar();
}
