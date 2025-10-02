#include "graphics.h"
#include <math.h>

void draw_octagon(int x, int y, int size)
{
    drawLine(x, y, x+size, y);
    drawLine(x+size, y, x+size+(int)sqrt(2)*size/2, y+(int)sqrt(2)*size/2);
    drawLine(x+size+(int)sqrt(2)*size/2, y+(int)sqrt(2)*size/2, x+size+(int)sqrt(2)*size/2, y+size+(int)sqrt(2)*size/2);
    drawLine(x+size+(int)sqrt(2)*size/2, y+size+(int)sqrt(2)*size/2, x+size, y+size+(int)sqrt(2)*size);
    drawLine(x+size, y+size+(int)sqrt(2)*size, x, y+size+(int)sqrt(2)*size);
    drawLine(x, y+size+(int)sqrt(2)*size, x-(int)sqrt(2)*size/2, y+size+(int)sqrt(2)*size/2);
    drawLine(x-(int)sqrt(2)*size/2, y+size+(int)sqrt(2)*size/2, x-(int)sqrt(2)*size/2, y+(int)sqrt(2)*size/2);
    drawLine(x-(int)sqrt(2)*size/2, y+(int)sqrt(2)*size/2, x, y);
}

int main()
{
    draw_octagon(100, 100, 50);
    return 0;
}