#include <stdio.h>

int main()
{
    int N = 3;
    int B[3][3] = { {1, 2, 3},
        {4, 5, 6},
        {7, 8, 9}
    };

    int Sum = 0;

    for (int j=0; j<N; j++)
    {
        Sum = 0;
        for(int i=0; i<N; i++)
        {
            Sum += B[i][j];
        }

        printf("Arithmetic mean for a column %d - %d\n", j, Sum/N);

    }

    getch();
    return 0;
}

