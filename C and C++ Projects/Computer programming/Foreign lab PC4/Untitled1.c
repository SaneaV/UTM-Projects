/*
Подключние рабочих библиотек:
    <stdio.h> - библиотека ввода/вывода
*/
#include <stdio.h>
#include <windows.h>

int main(int argc, char* argv[])
{
    SetConsoleCP(1251);
    SetConsoleOutputCP(1251);

    /* Именованная константа, определяющая количество элементов массива */
    const    MAX_ELEMENTS = 8;

    /* Объявление и инициализация переменных */
    float    m[MAX_ELEMENTS];

    /* Запрашиваем ввод данных с клавиатуры */
    for (int i = 0; i < MAX_ELEMENTS; i++)
    {
        printf("m[%d] = ", i);
        scanf("%f", &m[i]);
    }

    /* Принимаем индекс максимального массива равным 0 */
    int iMax = m[0];
    for (int i = 1; i < MAX_ELEMENTS; i++)
    {
        /* Находим индексы максимального элемента массива */
        if (abs(m[i]) > abs(m[iMax])) iMax = i;
    }

    printf("\n\nНомер максимального по модулю элемента: M[%d] = %.2f\n\n", iMax, m[iMax]);

    int flag = 0;
    float sum = 0;
    for(int i = 0; i < MAX_ELEMENTS; i++)
    {
        if(m[i]>0) flag = 1;
        if(flag) sum+=m[i];
    }
    printf("\n\nCумма элементов массива после первого положительного элемента: %.2f\n\n", sum);

    /* Вывод на экран массива */
    printf("Начальный массив:\n");
    for (int i = 0; i < MAX_ELEMENTS; printf("%.2f ", m[i++]));

    int a, b;
    printf("\n\nВведите значение а = ");
    scanf("%d", &a);
    printf("\nВведите значение b = ");
    scanf("%d", &b);

    if(b<a)
    {
        int c = a;
        a = b;
        b = c;
    }

    int last_ind = 0;
    int last_ind_range = 0;
    for(int i = 0; i < MAX_ELEMENTS; i++)
    {
        if((int)m[i]>=a && (int)m[i]<=b)
        {
            if (i!=last_ind)
            {
                float temp = m[last_ind];
                m[last_ind] = m[i];
                m[i]= temp;
            }
            last_ind++;
        }
    }

    /* Вывод на экран массива */
    printf("\nОтсортированный массив:\n");
    for (int i = 0; i < MAX_ELEMENTS; printf("%.2f ", m[i++]));
    printf("\n\n");

    return 0;
}

