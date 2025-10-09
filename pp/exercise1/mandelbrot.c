#include <stdio.h>
#include <stdlib.h>
#include <math.h>
#include "graphics.h"

double clamp(double  x)
{
	if (x>1.0) return 1.0;
	if (x<0.0) return 0.0;
	return x;
}

int main()
{
	int width = 1000, height = 1000;
	int max_iteration = 10000;
	double max_x = 1.5, min_x = -1.5, max_y = 1.5, min_y = -1.5;
	//double max_x = 0.35, min_x = 0.25, max_y = 0.05, min_y = -0.05;
	setWindowSize(width, height);
	for (int px = 0; px <= width; ++px) {
		for (int py = 0; py <= height; ++py) {
			double x0 = (max_x-min_x)*px/width + min_x;
			double y0 = (max_y-min_y)*py/height + min_y;
			double x = 0, y = 0;
			int iteration = 0;
			while (x*x+y*y<4.0 && iteration < max_iteration) {
				double temp = x*x - y*y + x0;
				y = 2*x*y + y0;
				x = temp;
				++iteration;
			}

			if (iteration == max_iteration) {
				setRGBColour(0,0,0);
			} else {
				double log_zn = log(x*x + y*y) / 2.0;
				double mu = (double)iteration + 1.0 - log(log_zn) / log(2.0);

				double t_scaled = pow(mu / (double)max_iteration, 0.4);

				double A = 0.5;
				double B = 0.5;
				double C_R = 0.8;
				double C_G = 0.7;
				double C_B = 0.9;
				double D_R = 0.0;
				double D_G = 0.33;
				double D_B = 0.66;
				double r = A + B * cos(2.0 * M_PI * (C_R * t_scaled + D_R));
				double g = A + B * cos(2.0 * M_PI * (C_G * t_scaled + D_G));
				double b = A + B * cos(2.0 * M_PI * (C_B * t_scaled + D_B));

				r = clamp(r);
				g = clamp(g);
				b = clamp(b);
				setRGBColour((int)(r*255.0), (int)(g*255.0), (int)(b*255.0));
			}
			drawLine(px, py, px, py);
		}
	}
	return 0;
}
