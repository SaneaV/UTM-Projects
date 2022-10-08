#include <stdio.h>
#include <math.h>

int main()
{
    const MAX_ELEMENTS = 8;

    int matrix[8][8] =
    {
        { 20, 70, 20, 70, 10, 40, 10, 30 },
        { 10, 10, 70, 60, 10, -80, 30, 50 },
        { 20, 70, 10, 80, 10, 40, 90, 30 },
        { 90, 20, 80, 50, 20, 10, 10, 60 },
        { 70, 10, 10, 30, 90, 30, 90, 10 },
        { 80, 20, 40, 90, 10, -60, 40, 90 },
        { 10, 30, 90, 10, 90, 40, 80, 80 },
        { 10, 80, 30, 20, 80, 20, 80, 10 }
    };

    int     i, j, iSumm;
    int    flag = 1;

    printf("Pokazati massiv:\n");
    for (i = 0; i < MAX_ELEMENTS; i++)
    {
        for (j = 0; j < MAX_ELEMENTS; j++)
        {
            printf("%2d\t", matrix[i][j]);
        }
        printf("\n");
    }

    printf("\n\nSumma elementov v teh stolbtsah kotoorii ne soderjat otritsatelnie elementi: \n\n");
    for (i = 0; i < MAX_ELEMENTS; i++)
    {
        flag = 0;
        iSumm = 0;
        for (j = 0; j < MAX_ELEMENTS && flag == 0; j++)
        {
            if (matrix[j][i] < 0)
            {
                flag = 1;
            }
            iSumm += matrix[j][i];

        }

        if (flag == 0) printf("Summa elementov stobtsa #%d = %d\n", i, iSumm);
    }

    printf("\n\nMinimum sredi summ elementov diagonalej,parallelnyh pobochnoy diagonali matricy: ");
    int summin=abs(matrix[0][0]), t;
    for(i=1; i<MAX_ELEMENTS; i++)
    {
        t=0;
        for(j=0; j<=i && j<MAX_ELEMENTS; j++)
        {
            t+=abs(matrix[j][i-j]);
        }
        if(t<summin)
        {
            summin=t;
        }
    }
    for(i=1; i<MAX_ELEMENTS; i++)
    {
        t=0;
        for(j=0; j<MAX_ELEMENTS-i && j<MAX_ELEMENTS; j++)
        {
            t+=abs(matrix[i+j][MAX_ELEMENTS-1-j]);
        }
        if(t<summin)
        {
            summin=t;
        }
    }
    printf("%d\n", summin);

    return 0;
}

