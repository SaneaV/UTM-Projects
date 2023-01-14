#include <stdio.h>
#include <stdlib.h>

/* Объявляем и инициализируем матрицу 8х8 */
int matrix[8][8] =
{
    { 2, 7, 2, 7, 1, 4, 0, 3 },
    { 1, 0, 7, 6, 0, -8, 3, 5 },
    { 2, 7, 1, 8, 1, 4, 9, 3 },
    { 99, 22, 88, 55, 10, 20, 20, 62 },
    { 7, 1, 1, 3, 9, 3, 9, 1 },
    { 8, 2, 4, 9, 1, -6, 4, 9 },
    { 0, 3, 9, 0, 9, 4, 8, 8 },
    { 1, 8, 3, 2, 8, 2, 8, 0 }
};

int min_row(int n)
{
    int j, k, m;
    k = 0;
    m = matrix[n][0];
    for(j = 1; j < 8; j++)
        if(matrix[n][j]<m)
        {
            m = matrix[n][j];
            k = j;
        }
    return k;
}

int max_column(int n)
{
    int j, k, m;
    k = 0;
    m = matrix[0][n];
    for(j = 1; j < 8; j++)
        if(matrix[j][n]>m)
        {
            m = matrix[j][n];
            k = j;
        }
    return k;
}

int main(int argc, char* argv[])
{
    int     i, j, iSumm; /* Счетчик и переменная для хранения суммы */
    int    bFlag;          /* Флаг */

    printf("Show matrix:\n");
    for (i = 0; i < 8; i++)
    {
        for (j = 0; j < 8; j++)
        {
            printf("%2d\t", matrix[i][j]);
        }
        printf("\n");
    }

    /*----------------------------------------------*/
    /*    П Е Р В А Я   Ч А С Т Ь   З А Д А Н И Я   */
    /*----------------------------------------------*/

    for(i=0; i<8; i++)
    {
        bFlag = 0;
        iSumm = 0;
        for(j=0; j<8; j++)
        {
            iSumm+=matrix[i][j];
            if(matrix[i][j]<0)
            {
                bFlag++;
            }
        }
        if(bFlag!=0)
        {
            printf("Sum of elements in line %d is %d\n", i, iSumm);
        }
    }

    /*----------------------------------------------*/
    /*    В Т О Р А Я   Ч А С Т Ь   З А Д А Н И Я   */
    /*----------------------------------------------*/
    printf("\n\n");

    printf("Saddle point:\n");
    for(i = 0; i < 8; i++)
    {
        j = min_row(i);
        if(max_column(j) == i)
        {
            printf("Line: %d,\Column: %d, Element: %d\n", j, i, matrix[i][j]);
        }
    }


    return 0;
}

