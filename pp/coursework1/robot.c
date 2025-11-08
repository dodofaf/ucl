#include "robot.h"
#include <stdlib.h>
#include <math.h>

//generates random position for a robot
void place_robot(Robot *robot, Grid *grid)
{
    Position *free_cells = (Position*)malloc(sizeof(Position) * grid->height * grid->width);
    int count = 0;
    for (int i=0;i<grid->height;++i) {
        for (int j=0;j<grid->width;++j) {
            if (grid->map[i][j] == 0) {
                free_cells[count].x = j;
                free_cells[count].y = i;
                ++count;
            }
        }
    }
    int rand_ind = rand() % count;
    robot->pos.x = free_cells[rand_ind].x;
    robot->pos.y = free_cells[rand_ind].y;
    free(free_cells);
}

void initialize_robot(Robot *robot, Grid *grid)
{
    place_robot(robot, grid);
    robot->dir = (rand()%4) * 90;
    robot->marker_count = 0;
}

void draw_robot(Robot *robot)
{
    foreground();
    int x_center = BORDER_WIDTH + robot->pos.x*(CELL_SIZE+1)+CELL_SIZE/2;
    int y_center = BORDER_WIDTH + robot->pos.y*(CELL_SIZE+1)+CELL_SIZE/2;
    int radius = CELL_SIZE/2 - 2;
    int x[3], y[3];
    for (int i=0;i<3;++i) {
        x[i] = x_center - radius * sin(M_PI * (double)(robot->dir + 120*i) / 180.0);
        y[i] = y_center - radius * cos(M_PI * (double)(robot->dir + 120*i) / 180.0);
    }
    setColour(blue);
    fillPolygon(3, x, y);
}

void left(Robot *robot)
{
    sleep(delta_time);
    foreground();
    clear();
    robot->dir = (robot->dir+90)%360;
    draw_robot(robot);
}

void right(Robot *robot)
{
    sleep(delta_time);
    foreground();
    clear();
    robot->dir = (robot->dir+270)%360;
    draw_robot(robot);
}

void forward(Robot *robot)
{
    sleep(delta_time);
    foreground();
    clear();
    robot->pos = next_position(robot->pos, robot->dir);
    draw_robot(robot);
}

int atMarker(Robot *robot, Grid *grid)
{
    sleep(delta_time);
    for (int i=0;i<grid->number_of_markers;++i) {
        if (equal(grid->markers[i], robot->pos)) {
            return 1;
        }
    }
    return 0;
}

int canMoveForward(Robot *robot, Grid *grid)
{
    Position next = next_position(robot->pos, robot->dir);
    if (next.x < 0 || next.x >= grid->width || next.y < 0 || next.y >= grid->height)
        return 0;

    return !grid->map[next.y][next.x];
}

void pickUpMarker(Robot *robot, Grid *grid)
{
    robot->marker_count++;
    delete_marker(grid, robot->pos);
}

void dropMarker(Robot *robot, Grid *grid)
{
    if (!robot->marker_count)
        return;
    add_marker(grid, robot->pos);
    robot->marker_count--;
}
