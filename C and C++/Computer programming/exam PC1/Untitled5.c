#include <stdio.h>

int numberOfEvenNumbers(int n)
{
    int res = 0;

    while (n) {
       if(!(n % 10 % 2)) res++;
       n /= 10;
    }

    return res;
}

int main()
{
    int n;
    printf("Input the number: ");
    scanf("%d", &n);

    printf("The number of even numbers is: %d", numberOfEvenNumbers(n));

    getch();
    return 0;
}
