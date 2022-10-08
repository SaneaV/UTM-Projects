//Testing of boolean logic

#include <stdio.h>
#include <stdbool.h>

int main()
{
    int a, b, s = 0;
    bool logic = true;

    printf("Input two numbers:\n");
    scanf("%d", &a);
    scanf("%d", &b);
    printf("\n");

    while(logic)
    {
        s += a + b;
        printf("S = %d", s);
        printf("\n");

        if(s > 100)
        {
            printf("\nThe sum of your numbers is bigger than 100.\nStop the program.\n");
            logic = false;
        }
    }

    return 0;
}
