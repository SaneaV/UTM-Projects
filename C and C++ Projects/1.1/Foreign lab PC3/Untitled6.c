/*Подключние рабочих библиотек:
    <stdio.h> - библиотека ввода/вывода
*/
#include <stdio.h>

int main(int argc, char* argv[])
{
    /* Именованная константа, определяющая количество элементов массива */
    const    MAX_ELEMENTS = 8;

    /* Объявление и инициализация переменных */
    int    m[MAX_ELEMENTS];
    int      iMin;
    int    min;

    /* Запрашиваем ввод данных с клавиатуры */
    for (int i = 0; i < MAX_ELEMENTS; i++)
    {
        printf("m[%d] = ", i);
        scanf("%d", &m[i]);
    }

    /* Принимаем индексы инимального массива равными 0 */
    iMin = 0;
    min = m[0];
    for (int i = 0; i < MAX_ELEMENTS; i++)
    {
        /* Находим индекс минимального элементов массива */
        if (m[i] < m[iMin])
        {
            min = m[i];
            iMin = i;
        }
    }

    printf("Min element m[%d] = %d", iMin, min);

    int firstPositive = MAX_ELEMENTS;
    int lastPositive = 0;

    for(int i=0;i<MAX_ELEMENTS;i++)
    {
        if(m[i]>0 && firstPositive>i)
        {
            firstPositive = i;
        }

        if(m[i]>0 && lastPositive<i)
        {
            lastPositive = i;
        }
    }

    int sum = 0;
    for(int i=firstPositive; i<=lastPositive && i<MAX_ELEMENTS; i++)
    {
        sum+=m[i];
    }

    printf("\nSum between first positive and last positive = %d\n", sum);

    /* Вывод на экран отсортированного массив */
    printf("\nInitial array:\n");
    for (int i = 0; i < MAX_ELEMENTS; printf("%2d ", m[i++]));

    int zeroPosition = 0;
    for(int i=0;i<MAX_ELEMENTS;i++)
    {
        if(m[i]==0)
        {
            int c = m[zeroPosition];
            m[zeroPosition++] = m[i];
            m[i] = c;
        }
    }

    /* Вывод на экран начальный массив */
    printf("\nSorted array:\n");
    for (int i = 0; i < MAX_ELEMENTS; printf("%2d ", m[i++]));

    return 0;
}
