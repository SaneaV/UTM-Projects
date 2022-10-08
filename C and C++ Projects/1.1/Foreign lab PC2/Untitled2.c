#include <stdio.h>
#include <math.h>

int main()
{
    const MAX = 8;
    int M[MAX];
    int i = 0;

    //Zapolniaem massiv
    for ( ; i < MAX; i++)
    {
        printf("M[%d] = ", i);
        scanf("%d", &M[i]);
    }

    //ishem max element massiva
    int maximum = M[0];
    int position = 0;
    for(i = 1; i< MAX; i++)
    {
        if(M[i]>maximum)
        {
            position = i;
            maximum = M[i];
        }
    }
    printf("Maximum - M[%d] = %d", position, maximum);

    //ishem summu elementov do poslednego positivinogo chisla
    int Sum = 0;
    int flag = 0;
    for(i = MAX; i>=0; i--)
    {
        if(M[i]>0 && flag!=0)
        {
            Sum += M[i];
        }
        else if(M[i]>0 && flag==0)
        {
            flag = 1;
        }
    }
    printf("\n");
    printf("Summa elementov do poslednego positivnogo chisla = %d", Sum);

    //sjimaem massiv, udaliv elementi moduli kotorih nahoditsia v promejutke mejdy [a,b]
    //i zapolneam konets massiva nuleami
    printf("\n");
    int a, b;
    printf("a = ");
    scanf("%d", &a);
    printf("b = ");
    scanf("%d", &b);
    printf("\n");

    for(i = 0; i<MAX; i++)
    {
        printf("%2d\t", M[i]);
    }

    printf("\n");


    if(a>b)
    {
        int c = a;
        a = b;
        b = a;
    }

    int lastRightPosition = 0;
    for(i = 0; i<MAX; i++, lastRightPosition++)
    {
        if(abs(M[i])>=a && abs(M[i]<=b))
        {
            lastRightPosition--;
        }
        else
        {
            M[lastRightPosition] = M[i];
        }
    }

    for(i = lastRightPosition; i<MAX; i++)
    {
        M[i] = 0;
    }

    for(i = 0; i<MAX; i++)
    {
        printf("%2d\t", M[i]);
    }
}

