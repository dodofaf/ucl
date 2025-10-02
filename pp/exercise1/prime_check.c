#include <stdio.h>
#include <string.h>

void reverse(char str[]) 
{ 
    int start = 0; 
    int end = strlen(str) -1; 
    while (start < end) 
    { 
        char temp = str[start]; 
        str[start] = str[end]; 
        str[end] = temp; 
        start++; 
        end--; 
    } 
}

int main()
{
	char number[100];
	scanf("%s", number);
    reverse(number);
    printf("%s\n", number);
}
