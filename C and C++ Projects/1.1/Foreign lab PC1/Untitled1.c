#include <stdio.h>
#include <math.h>
#include <windows.h>

int main()
{
    SetConsoleCP(1251);
    SetConsoleOutputCP(1251);

    double a, b, c, xB, xE, dX, F = 0, x;

    printf("������� �������� a: ");
    scanf("%lf", &a);


    printf("������� �������� b: ");
    scanf("%lf", &b);


    printf("������� �������� c: ");
    scanf("%lf", &c);


    printf("������� �������� xB: ");
    scanf("%lf", &xB);


    printf("������� �������� xE: ");
    scanf("%lf", &xE);


    printf("������� �������� dX: ");
    scanf("%lf", &dX);

    x = xB;

    printf("\n������� ������ ����� ���� while.\n");

    while(xE >= x)
    {
        F = 0;

        if(x<5 && c!=0)
        {
            F = (-1 * a * x * x) - b;

            if ((((long)floor(a) | (long)floor(b)) ^ ((long)floor(a) | (long)floor(c)))!=0)
            {
                printf("x = %.2f\tF = (-1 * a * x * x) - b = %.0f\n", x, F);
            }
            else
            {
                printf("x = %.2f\tF = (-1 * a * x * x) - b = %.2f\n", x, F);
            }

        }
        else if(x>5 && c==0)
        {
            if(x!=0)
            {
                F = (x - a) / x;

                if ((((long)floor(a) | (long)floor(b)) ^ ((long)floor(a) | (long)floor(c)))!=0)
                {
                    printf("x = %.2f\tF = (x - a) / x = %.0f\n", x, F);
                }
                else
                {
                    printf("x = %.2f\tF = (x - a) / x = %.2f\n", x, F);
                }
            }
            else
            {
                printf("x = %lf\t������� �� ���������� ��� f = (x - a) / x\n", x);
            }
        }
        else
        {
            if(c!=0)
            {
                F = (-1 * x) / c;

                if ((((long)floor(a) | (long)floor(b)) ^ ((long)floor(a) | (long)floor(c)))!=0)
                {
                    printf("x = %.2f\tF = (-1 * x) / c = %.0f\n", x, F);
                }
                else
                {
                    printf("x = %.2f\tF = (-1 * x) / c = %.2f\n", x, F);
                }
            }
            else
            {
                printf("x = %lf\t������� �� ���������� ��� f = (-1 * x) / c\n", x);
            }
        }

        x += dX;
    }

    printf("\n������� ������ ����� ���� for.\n");

    for(x = xB; xE >= x; x += dX)
    {
        F = 0;

        if(x<5 && c!=0)
        {
            F = (-1 * a * x * x) - b;

            if ((((long)floor(a) | (long)floor(b)) ^ ((long)floor(a) | (long)floor(c)))!=0)
            {
                printf("x = %.2f\tF = (-1 * a * x * x) - b = %.0f\n", x, F);
            }
            else
            {
                printf("x = %.2f\tF = (-1 * a * x * x) - b = %.2f\n", x, F);
            }

        }
        else if(x>5 && c==0)
        {
            if(x!=0)
            {
                F = (x - a) / x;

                if ((((long)floor(a) | (long)floor(b)) ^ ((long)floor(a) | (long)floor(c)))!=0)
                {
                    printf("x = %.2f\tF = (x - a) / x = %.0f\n", x, F);
                }
                else
                {
                    printf("x = %.2f\tF = (x - a) / x = %.2f\n", x, F);
                }
            }
            else
            {
                printf("x = %lf\t������� �� ���������� ��� f = (x - a) / x\n", x);
            }
        }
        else
        {
            if(c!=0)
            {
                F = (-1 * x) / c;

                if ((((long)floor(a) | (long)floor(b)) ^ ((long)floor(a) | (long)floor(c)))!=0)
                {
                    printf("x = %.2f\tF = (-1 * x) / c = %.0f\n", x, F);
                }
                else
                {
                    printf("x = %.2f\tF = (-1 * x) / c = %.2f\n", x, F);
                }
            }
            else
            {
                printf("x = %lf\t������� �� ���������� ��� f = (-1 * x) / c\n", x);
            }
        }
    }

    printf("\n������� ������ ����� ���� do...while.\n");
    x=xB;
    if(xE >= x)
    {
        do
        {
            F = 0;

            if(x<5 && c!=0)
            {
                F = (-1 * a * x * x) - b;

                if ((((long)floor(a) | (long)floor(b)) ^ ((long)floor(a) | (long)floor(c)))!=0)
                {
                    printf("x = %.2f\tF = (-1 * a * x * x) - b = %.0f\n", x, F);
                }
                else
                {
                    printf("x = %.2f\tF = (-1 * a * x * x) - b = %.2f\n", x, F);
                }

            }
            else if(x>5 && c==0)
            {
                if(x!=0)
                {
                    F = (x - a) / x;

                    if ((((long)floor(a) | (long)floor(b)) ^ ((long)floor(a) | (long)floor(c)))!=0)
                    {
                        printf("x = %.2f\tF = (x - a) / x = %.0f\n", x, F);
                    }
                    else
                    {
                        printf("x = %.2f\tF = (x - a) / x = %.2f\n", x, F);
                    }
                }
                else
                {
                    printf("x = %lf\t������� �� ���������� ��� f = (x - a) / x\n", x);
                }
            }
            else
            {
                if(c!=0)
                {
                    F = (-1 * x) / c;

                    if ((((long)floor(a) | (long)floor(b)) ^ ((long)floor(a) | (long)floor(c)))!=0)
                    {
                        printf("x = %.2f\tF = (-1 * x) / c = %.0f\n", x, F);
                    }
                    else
                    {
                        printf("x = %.2f\tF = (-1 * x) / c = %.2f\n", x, F);
                    }
                }
                else
                {
                    printf("x = %lf\t������� �� ���������� ��� f = (-1 * x) / c\n", x);
                }
            }

            x += dX;
        }
        while(xE>=x);
    }

}
