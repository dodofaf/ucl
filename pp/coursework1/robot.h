#include "grid.h"

//stores information about robot
typedef struct {
    Position pos;
    int dir;
    int marker_count;
} Robot;

//draws robot on the foreground
void draw_robot(Robot *robot);

//places robot in a random free space directed randomly
void initialize_robot(Robot *robot, Grid *grid);

void forward(Robot *robot);
void left(Robot *robot);
void right(Robot *robot);
int atMarker(Robot *robot, Grid *grid);
int canMoveForward(Robot *robot, Grid *grid);
void pickUpMarker(Robot *robot, Grid *grid);
void dropMarker(Robot *robot, Grid *grid);
