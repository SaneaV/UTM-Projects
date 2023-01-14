#include <stdio.h>

int main()
{
    float xN, xK, dx;
    float e = 2.71;
    printf("Input xN: ");
    scanf("%f", &xN);
    printf("Input xK: ");
    scanf("%f", &xK);
    printf("Input dx: ");
    scanf("%f", &dx);

    if(xN>xK)
    {
        float temp = xN;
        xN = xK;
        xK = temp;
    }

    float x = xN;
    float f = 0;

    printf("\n\n");

    while(x<=xK){
        if(x<=0)
        {
            f = 3 * pow(x, 2.0) / (1 + pow(x, 2.0));
            printf("f(%f) = 3 * pow(%f, 2) / (1 + pow(%f, 2)); f(%f) = %f\n", x, x, x, x, f);
        }
        if(x>0)
        {
            f = sqrt(1 + 2 * x / (pow(e, 5.0 * x) + pow(x, 3.0)));
            printf("sqrt(1 + 2 * %f / (pow(e, 5 * %f) + pow(%f, 3))); f(%f) = %f\n", x, x, x, x, f);
        }
        x += dx;
    }

    getch();
    return 0;
}


