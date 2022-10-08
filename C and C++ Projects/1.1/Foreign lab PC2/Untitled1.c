#include <stdio.h>
#include <math.h>

int main()
{
    double a, b, c, xB, xE, dX, F = 0, x;

    printf("Vvedite a: ");
    scanf("%lf", &a);
    printf("Vvedite b: ");
    scanf("%lf", &b);
    printf("Vvedite c: ");
    scanf("%lf", &c);
    printf("Vvedite xB: ");
    scanf("%lf", &xB);
    printf("Vvedite xE: ");
    scanf("%lf", &xE);
    printf("Vvedite dX: ");
    scanf("%lf", &dX);

    x = xB;

    printf("\n");
    printf("Tsikl while.");
    printf("\n");

    while(xE >= x)
    {
        F = 0;

        if(x<0 && b!=0)
        {
            if(10+b != 0)
            {
                F = a - x / (10 + b);

                if ((((long)floor(a) | (long)floor(b)) & (long)floor(c))!=0)
                {
                    printf("x = %.2f\tF = a - x / (10 + b) = %.0f\n", x, F);
                }
                else
                {
                    printf("x = %.2f\tF = a - x / (10 + b) = %.2f\n", x, F);
                }
            }
            else
            {
                printf("x = %lf\tFunctia ne sushestvuet na f = a - x / (10 + b)\n", x);
            }

        }
        else if(x>0 && b==0)
        {
            if(x-c != 0)
            {
                F = (x - a) / (x - c);

                if ((((long)floor(a) | (long)floor(b)) & (long)floor(c))!=0)
                {
                    printf("x = %.2f\tF = (x - a) / (x - c) = %.0f\n", x, F);
                }
                else
                {
                    printf("x = %.2f\tF = (x - a) / (x - c) = %.2f\n", x, F);
                }
            }
            else
            {
                printf("x = %lf\tFunctia ne sushestvuet na f = (x - a) / (x - c)\n", x);
            }
        }
        else
        {
            if(c!=0)
            {
                F = 3 * x + 2 / c;

                if ((((long)floor(a) | (long)floor(b)) & (long)floor(c))!=0)
                {
                    printf("x = %.2f\tF = 3 * x + 2 / c = %.0f\n", x, F);
                }
                else
                {
                    printf("x = %.2f\tF = 3 * x + 2 / c = %.2f\n", x, F);
                }
            }
            else
            {
                printf("x = %lf\tFunctia ne sushestvuet na f = 3 * x + 2 / c\n", x);
            }
        }

        x += dX;
    }

    printf("\n");
    printf("do...while.");
    printf("\n");

    x=xB;
    if(xE >= x)
    {
        do
        {
            F = 0;

            if(x<0 && b!=0)
            {
                if(10+b != 0)
                {
                    F = a - x / (10 + b);

                    if ((((long)floor(a) | (long)floor(b)) & (long)floor(c))!=0)
                    {
                        printf("x = %.2f\tF = a - x / (10 + b) = %.0f\n", x, F);
                    }
                    else
                    {
                        printf("x = %.2f\tF = a - x / (10 + b) = %.2f\n", x, F);
                    }
                }
                else
                {
                    printf("x = %lf\tFunctia ne sushestvuet na f = a - x / (10 + b)\n", x);
                }

            }
            else if(x>0 && b==0)
            {
                if(x-c != 0)
                {
                    F = (x - a) / (x - c);

                    if ((((long)floor(a) | (long)floor(b)) & (long)floor(c))!=0)
                    {
                        printf("x = %.2f\tF = (x - a) / (x - c) = %.0f\n", x, F);
                    }
                    else
                    {
                        printf("x = %.2f\tF = (x - a) / (x - c) = %.2f\n", x, F);
                    }
                }
                else
                {
                    printf("x = %lf\tFunctia ne sushestvuet na f = (x - a) / (x - c)\n", x);
                }
            }
            else
            {
                if(c!=0)
                {
                    F = 3 * x + 2 / c;

                    if ((((long)floor(a) | (long)floor(b)) & (long)floor(c))!=0)
                    {
                        printf("x = %.2f\tF = 3 * x + 2 / c = %.0f\n", x, F);
                    }
                    else
                    {
                        printf("x = %.2f\tF = 3 * x + 2 / c = %.2f\n", x, F);
                    }
                }
                else
                {
                    printf("x = %lf\tFunctia ne sushestvuet na f = 3 * x + 2 / c\n", x);
                }
            }
            x += dX;
        }
        while(xE>=x);
    }

    printf("\n");
    printf("for.");
    printf("\n");

    for(x = xB; xE >= x; x += dX)
    {
        F = 0;

        if(x<0 && b!=0)
        {
            if(10+b != 0)
            {
                F = a - x / (10 + b);

                if ((((long)floor(a) | (long)floor(b)) & (long)floor(c))!=0)
                {
                    printf("x = %.2f\tF = a - x / (10 + b) = %.0f\n", x, F);
                }
                else
                {
                    printf("x = %.2f\tF = a - x / (10 + b) = %.2f\n", x, F);
                }
            }
            else
            {
                printf("x = %lf\tFunctia ne sushestvuet na f = a - x / (10 + b)\n", x);
            }

        }
        else if(x>0 && b==0)
        {
            if(x-c != 0)
            {
                F = (x - a) / (x - c);

                if ((((long)floor(a) | (long)floor(b)) & (long)floor(c))!=0)
                {
                    printf("x = %.2f\tF = (x - a) / (x - c) = %.0f\n", x, F);
                }
                else
                {
                    printf("x = %.2f\tF = (x - a) / (x - c) = %.2f\n", x, F);
                }
            }
            else
            {
                printf("x = %lf\tFunctia ne sushestvuet na f = (x - a) / (x - c)\n", x);
            }
        }
        else
        {
            if(c!=0)
            {
                F = 3 * x + 2 / c;

                if ((((long)floor(a) | (long)floor(b)) & (long)floor(c))!=0)
                {
                    printf("x = %.2f\tF = 3 * x + 2 / c = %.0f\n", x, F);
                }
                else
                {
                    printf("x = %.2f\tF = 3 * x + 2 / c = %.2f\n", x, F);
                }
            }
            else
            {
                printf("x = %lf\tFunctia ne sushestvuet na f = 3 * x + 2 / c\n", x);
            }
        }
    }
}
