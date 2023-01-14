/*���������� ������� ���������:
    <stdio.h> - ���������� �����/������
*/
#include <stdio.h>

int main(int argc, char* argv[])
{
    /* ����������� ���������, ������������ ���������� ��������� ������� */
    const    MAX_ELEMENTS = 8;

    /* ���������� � ������������� ���������� */
    int    m[MAX_ELEMENTS];
    int      iMin;
    int    min;

    /* ����������� ���� ������ � ���������� */
    for (int i = 0; i < MAX_ELEMENTS; i++)
    {
        printf("m[%d] = ", i);
        scanf("%d", &m[i]);
    }

    /* ��������� ������� ����������� ������� ������� 0 */
    iMin = 0;
    min = m[0];
    for (int i = 0; i < MAX_ELEMENTS; i++)
    {
        /* ������� ������ ������������ ��������� ������� */
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

    /* ����� �� ����� ���������������� ������ */
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

    /* ����� �� ����� ��������� ������ */
    printf("\nSorted array:\n");
    for (int i = 0; i < MAX_ELEMENTS; printf("%2d ", m[i++]));

    return 0;
}
