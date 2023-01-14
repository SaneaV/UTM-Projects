#include <stdio.h>
#include <math.h>

int main()
{
    float    a, b, c;
    float    StartX, EndX, dX;
    float    F = 0;
    float    x = 0;

    printf("Input:");
    printf("\ta = ");
    scanf("%f", &a);
    printf("\tb = ");
    scanf("%f", &b);
    printf("\tc = ");
    scanf("%f", &c);
    printf("\tX beg. = ");
    scanf("%f", &StartX);
    printf("\tX end. = ");
    scanf("%f", &EndX);
    printf("\tdX = ");
    scanf("%f", &dX);

    printf("\nCycle while \n");

    x = StartX;

    while(EndX >= x)
    {
        F = 0;

        if(c<0 && b!=0)
        {
            F = a * pow(x, 2) + b * pow(x, 2) + c;

            if (((long)floor(a) & (long)floor(b)) | ((long)floor(a) & (long)floor(c))!=0)
                /* Выводим округленное (целое) значение */
                printf("x = %.2f\tF = %.0f\n", x, F);
            else
                /* Выводим вещественное значение */
                printf("x = %.2f\tF = %.2f\n", x, F);

        }
        else if(c>0 && b==0)
        {
            if(x+c != 0)
            {
                F = (x + a) / (x + c);

                if (((long)floor(a) & (long)floor(b)) | ((long)floor(a) & (long)floor(c))!=0)
                    /* Выводим округленное (целое) значение */
                    printf("x = %.2f\tF = %.0f\n", x, F);
                else
                    /* Выводим вещественное значение */
                    printf("x = %.2f\tF = %.2f\n", x, F);
            }
            else
            {
                printf("x = %lf\tFunction doesn't exist on f = (x + a) / (x + c)\n", x);
            }
        }
        else
        {
            if(c!= 0)
            {
                F = x/c;

                    if (((long)floor(a) & (long)floor(b)) | ((long)floor(a) & (long)floor(c))!=0)
                        /* Выводим округленное (целое) значение */
                        printf("x = %.2f\tF = %.0f\n", x, F);
                else
                    /* Выводим вещественное значение */
                    printf("x = %.2f\tF = %.2f\n", x, F);
            }
            else
            {
                printf("x = %lf\tFunction doesn't exist on f = x / c\n", x);
            }
        }

        x += dX;
    }

    printf("\nCycle do...while \n");

    x=StartX;
    if(EndX >= x)
    {
        do
        {
            F = 0;

            if(c<0 && b!=0)
            {
                F = a * pow(x, 2) + b * pow(x, 2) + c;

                if (((long)floor(a) & (long)floor(b)) | ((long)floor(a) & (long)floor(c))!=0)
                    /* Выводим округленное (целое) значение */
                    printf("x = %.2f\tF = %.0f\n", x, F);
                else
                    /* Выводим вещественное значение */
                    printf("x = %.2f\tF = %.2f\n", x, F);

            }
            else if(c>0 && b==0)
            {
                if(x+c != 0)
                {
                    F = (x + a) / (x + c);

                    if (((long)floor(a) & (long)floor(b)) | ((long)floor(a) & (long)floor(c))!=0)
                        /* Выводим округленное (целое) значение */
                        printf("x = %.2f\tF = %.0f\n", x, F);
                    else
                        /* Выводим вещественное значение */
                        printf("x = %.2f\tF = %.2f\n", x, F);
                }
                else
                {
                    printf("x = %lf\tFunction doesn't exist on f = (x + a) / (x + c)\n", x);
                }
            }
            else
            {
                if(c!= 0)
                {
                    F = x/c;

                        if (((long)floor(a) & (long)floor(b)) | ((long)floor(a) & (long)floor(c))!=0)
                            /* Выводим округленное (целое) значение */
                            printf("x = %.2f\tF = %.0f\n", x, F);
                    else
                        /* Выводим вещественное значение */
                        printf("x = %.2f\tF = %.2f\n", x, F);
                }
                else
                {
                    printf("x = %lf\tFunction doesn't exist on f = x / c\n", x);
                }
            }
            x += dX;
        }
        while(EndX>=x);
    }


    printf("\nCycle for \n");

    for(x = StartX; EndX >= x; x += dX)
    {
        F = 0;

        if(c<0 && b!=0)
        {
            F = a * pow(x, 2) + b * pow(x, 2) + c;

            if (((long)floor(a) & (long)floor(b)) | ((long)floor(a) & (long)floor(c))!=0)
                /* Выводим округленное (целое) значение */
                printf("x = %.2f\tF = %.0f\n", x, F);
            else
                /* Выводим вещественное значение */
                printf("x = %.2f\tF = %.2f\n", x, F);

        }
        else if(c>0 && b==0)
        {
            if(x+c != 0)
            {
                F = (x + a) / (x + c);

                if (((long)floor(a) & (long)floor(b)) | ((long)floor(a) & (long)floor(c))!=0)
                    /* Выводим округленное (целое) значение */
                    printf("x = %.2f\tF = %.0f\n", x, F);
                else
                    /* Выводим вещественное значение */
                    printf("x = %.2f\tF = %.2f\n", x, F);
            }
            else
            {
                printf("x = %lf\tFunction doesn't exist on f = (x + a) / (x + c)\n", x);
            }
        }
        else
        {
            if(c!= 0)
            {
                F = x/c;

                    if (((long)floor(a) & (long)floor(b)) | ((long)floor(a) & (long)floor(c))!=0)
                        /* Выводим округленное (целое) значение */
                        printf("x = %.2f\tF = %.0f\n", x, F);
                else
                    /* Выводим вещественное значение */
                    printf("x = %.2f\tF = %.2f\n", x, F);
            }
            else
            {
                printf("x = %lf\tFunction doesn't exist on f = x / c\n", x);
            }
        }
    }

    return 0;
}

