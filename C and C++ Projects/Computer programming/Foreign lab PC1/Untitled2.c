#include <stdio.h>
#include <windows.h>

int main()
{
    SetConsoleCP(1251);
    SetConsoleOutputCP(1251);

    const MAX_ELEMENTS = 8;
    int A[MAX_ELEMENTS];

    printf("������� �������� �������:\n");
    for (int i = 0; i < MAX_ELEMENTS; i++)
    {
        printf("A[%d] = ", i);
        scanf("%d", &A[i]);
    }

    //===================================================

    printf("\n�������� ������:\n");
    for(int i=0; i<MAX_ELEMENTS; i++)
    {
        printf("%d\t", A[i]);
    }

    int iMax = 0, max = A[0];
    for (int i = 1; i < MAX_ELEMENTS; i++)
    {
        if (A[i] > max)
        {
            iMax = i;
            max = A[i];
        }

    }
    printf("\n\n������� ������������� �������� ������� A[%d] = %d\n", iMax, max);

    //===================================================

    int zeroF = -1, zeroE = -1;
    for (int i = 0; i < MAX_ELEMENTS; i++)
    {
        if(A[i] == 0)
        {
            if(zeroF == -1)
            {
                zeroF = i;
            }

            zeroE = i;
        }

    }

    if(zeroF != -1)
    {
        if(zeroF != zeroE)
        {
            int sum = 0;
            for (int i = zeroF; i < zeroE; i++)
            {
                sum += A[i];
            }
            printf("\n����� ��������� ����� ������ ���� A[%d] � ��������� ���� A[%d] = %d\n", zeroF, zeroE, sum);
        }
        else
        {
            printf("\n� ������� ������ ���� ����\n");
        }
    }
    else
    {
        printf("\n� ������� ��� �� ������ ����\n");
    }

    //===================================================

    printf("\n\n���������� ������� �� ������� �������:\n");

    for(int i = 0; i < MAX_ELEMENTS/2; ++i)
    {   int t = *(A + i);
        for(int j = 0; j < MAX_ELEMENTS - i - 1; ++j)
        {   *(A + i + j) = *(A + i + j + 1);
        }
        *(A + MAX_ELEMENTS - 1) = t;
    }

    for(int i=0; i<MAX_ELEMENTS; i++)
    {
        printf("%d\t", A[i]);
    }

    printf("\n");

}
