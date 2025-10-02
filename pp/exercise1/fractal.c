#include "graphics.h"
#include <math.h>
#include <stdio.h>


void draw_line(double *x, double *y, int length, int angle, int n)
{
    if (n == 1) {
        double x2 = *x + (length * cos((angle/60) * (M_PI / 3.0)));
        double y2 = *y + (length * sin((angle/60) * (M_PI / 3.0)));
        drawLine((int)*x, (int)*y, (int)x2, (int)y2);
        *x = x2;
        *y = y2;
    } else {
        draw_line(x, y, length / 3, angle, n - 1);
        draw_line(x, y, length / 3, (angle+300)%360, n - 1);
        draw_line(x, y, length / 3, (angle+60)%360, n - 1);
        draw_line(x, y, length / 3, angle, n - 1);
    }
}



int main()
{
    setWindowSize(2000, 1200);
    int nIterations = 7, length = 1000;
    double x = 1000, y = 100;
    draw_line(&x, &y, length, 60, nIterations);
    draw_line(&x, &y, length, 180, nIterations);
    draw_line(&x, &y, length, 300, nIterations);
}