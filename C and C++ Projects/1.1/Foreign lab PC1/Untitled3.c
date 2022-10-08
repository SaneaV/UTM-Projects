#include <stdio.h>
#include <windows.h>

int main()
{
    SetConsoleCP(1251);
    SetConsoleOutputCP(1251);

    const MAX_ELEMENTS = 8;

    int     matrix[8][8] =
    {
        { 2, 7, 2, 7, 1, 4, 0, 3 },
        { 1, 0, 7, 6, 0, -8, 3, 5 },
        { 2, 7, 1, 8, 1, 4, 9, 3 },
        { 9, 2, 8, 5, 2, 0, 0, 6 },
        { 7, 1, 1, 3, 9, 3, 9, 1 },
        { 8, 2, 4, 9, 1, -6, 4, 9 },
        { 0, 3, 9, 0, 9, 4, 8, 8 },
        { 1, 8, 3, 2, 8, 2, 8, 0 }
    };


    int     i, j, iSumm;
    int    bFlag = 1;

    printf("Показать массив:\n");
    for (i = 0; i < MAX_ELEMENTS; i++)
    {
        for (j = 0; j < MAX_ELEMENTS; j++)
        {
            printf("%2d\t", matrix[i][j]);
        }
        printf("\n");
    }

    printf("\n\nCтрока и столбец, которые совпадают по значениям = ");
    for (i = 0; i < MAX_ELEMENTS; i++)
    {
        bFlag = 1;
        for (j = 0; j < MAX_ELEMENTS; j++)
        {
            if (matrix[i][j] != matrix[j][i])
            {
                bFlag = 0;
                break;
            }
        }
        if (bFlag == 1)
        {
            printf("\nСтрока №%d, столбец №%d: \nЭлементы строки/столбца: ", i, i);
            for (j = 0; j < MAX_ELEMENTS; j++)
            {
                printf("%d\t ", matrix[i][j]);
            }

        }
    }

    printf("\n\nСумма элементов в тех строках, которые содержат хотя бы один отрицательный элемент: \n");
    for (i = 0; i < MAX_ELEMENTS; i++)
    {
        bFlag = 0;
        iSumm = 0;
        for (j = 0; j < MAX_ELEMENTS && bFlag == 0; j++)
        {
            if (matrix[i][j] < 0)
            {
                bFlag = 1;
                for(int k = 0; k<MAX_ELEMENTS; k++)
                {
                    iSumm += matrix[i][k];
                }
            }

        }

        if (bFlag == 1) printf("Сумма элементов строки #%d = %d\n", i, iSumm);
    }

    return 0;
}
