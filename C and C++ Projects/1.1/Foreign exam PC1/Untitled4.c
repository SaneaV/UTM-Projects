#include <string.h>
#include <stdio.h>

int main()
{
    int index, i, count=0, max=0;
    char word[100]="";
    char space = ' ';

    printf("Input words throw space:\n");
    fgets(word, 100, stdin);

    int length = strlen(word);
    strncat(word, &space, 1);

    for(int i=0; i<length; i++)
    {
        if(word[i] == space)
        {
            if(count>max)
            {
                max=count;
                index=i;

            }
            count=0;

        }
        else
        {
            count++;
        }
    }

    printf("The longest word is:\n");
    for(i=index - max; i<index; i++)
    {
        printf("%c", word[i]);
    }

    getch();
    return 0;
}
