#include <stdio.h>
#include <windows.h>

int main(int argc, char* argv[])
{

    SetConsoleCP(1251);
    SetConsoleOutputCP(1251);

    /* Объявляем и инициализируем матрицу 8х8 */
    int     matrix[8][8] =
    {
        { -2, -7, -2, -7, -1, -4, -1, -3 },
        { 0, 0, 0, 0, 0, 0, 0, 0 },
        { 2, 7, 1, 8, 1, 4, 9, 3 },
        { 9, 2, 8, 5, 2, 0, 0, 6 },
        { 0, 0, 0, 0, 0, 0, 0, 0 },
        { 8, 2, 4, 9, 1, -6, 4, 9 },
        { 0, 3, 9, 0, 9, 4, 8, 8 },
        { 1, 8, 3, 2, 8, 2, 8, 0 }
    };
    int    bFlag;
    int N = 8; // количество строк
    int M = 8; // количество столбцов

    /*----------------------------------------------*/
    /*    П Е Р В А Я   Ч А С Т Ь   З А Д А Н И Я   */
    /*----------------------------------------------*/

    printf("Начальная матрица: \n\n");
    for(int i=0; i<N; i++)
    {
        for(int j=0; j<M; j++)
            printf("%2d ", matrix[i][j]);
        printf("\n");
    }

    for (int i = 0; i < N; i++)
    {
        bFlag = 1;
        for(int j=0; j < M && bFlag; j++)
        {
            if(matrix[i][j] != 0) bFlag = 0;
        }

        if(bFlag) //Если вся строка состоит из нулей, то bFlag = true, можем удалять
        {
            if(i<N-1) //Если наша строка не последняя, в противном случае просто удаляем строку
            {
                for(int j=i; j<N; j++)
                {
                    for(int k=0; k<M; k++)
                        matrix[j][k] = matrix[j+1][k]; // Заменяем все элементы строки из 0 на следующую строку
                }
            }
            N--; // Удаляем последнюю строку
            i--; // Перепроверяем, что новая строка не состоит тоже только из нулей
        }
    }

    if(N!=0)
    {
        printf("\n\nОтсортированная матрица: \n\n");

        for(int i=0; i<N; i++)
        {
            for(int j=0; j<M; j++)
                printf("%2d ", matrix[i][j]);
            printf("\n");
        }
    }
    else
    {
        printf("Матрица пуста!\n\n");
    }



    /*----------------------------------------------*/
    /*    В Т О Р А Я   Ч А С Т Ь   З А Д А Н И Я   */
    /*----------------------------------------------*/

    bFlag = 1;
    int firstPositive = -1;
    for(int i=0; i<N && bFlag; i++)
    {
        for(int j=0; j<M && bFlag; j++)
            if(matrix[i][j]>0)
            {
                bFlag = 0;
                firstPositive = i;
            }
    }

    if(firstPositive!=-1)
    {
        printf("\n\nПервая строка с положительным элеметом (начиная с 1) => %d\n\n", firstPositive+1);
    }
    else
    {
        printf("\n\nПоложительных элементов в матрице нет.\n\n");
    }


    return 0;
}
