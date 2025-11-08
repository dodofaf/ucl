#include "robot.h"
#include <stdlib.h>
#include <time.h>

#define INITIAL_SIZE 50

//initializing delta_time
int delta_time = 50;

// stores robot's memory
struct robot_memory {
    Position *visited;
    Position pos;
    int dir;
    int num_visited;
    int visited_sz;
};

//checks if given relative position was visited by robot before
int visited_before(Position pos, struct robot_memory *memory)
{
    for (int i = 0; i < memory->num_visited; ++i) {
        if (equal(pos, memory->visited[i])) {
            return 1;
        }
    }
    return 0;
}

//implementation of Depth-First Search
void recursive_dfs(Grid *grid, Robot *robot, struct robot_memory *memory, int draw) {
    if (memory->num_visited == memory->visited_sz) {
        memory->visited_sz *= 2;
        memory->visited = (Position *) realloc(memory->visited, memory->visited_sz*sizeof(Position));
    }
    memory->visited[memory->num_visited] = memory->pos;
    memory->num_visited++;

    if (atMarker(robot, grid)) {
        pickUpMarker(robot, grid);
    }

    if (draw)
        colour_cell(robot->pos, yellow);

    left(robot);
    memory->dir = (memory->dir + 90) % 360;
    Position next = next_position(memory->pos, memory->dir);
    if (canMoveForward(robot, grid) && !visited_before(next, memory)) {
        forward(robot);
        memory->pos = next;
        recursive_dfs(grid, robot, memory, draw);
    }

    right(robot);
    memory->dir = (memory->dir + 270) % 360;
    next = next_position(memory->pos, memory->dir);
    if (canMoveForward(robot, grid) && !visited_before(next, memory)) {
        forward(robot);
        memory->pos = next;
        recursive_dfs(grid, robot, memory, draw);
    }

    right(robot);
    memory->dir = (memory->dir + 270) % 360;
    next = next_position(memory->pos, memory->dir);
    if (canMoveForward(robot, grid) && !visited_before(next, memory)) {
        forward(robot);
        memory->pos = next;
        recursive_dfs(grid, robot, memory, draw);
    }

    if (draw)
        colour_cell(robot->pos, red);

    right(robot);
    memory->dir = (memory->dir+270)%360;
    memory->pos = next_position(memory->pos, memory->dir);
    if (canMoveForward(robot, grid)) {
        forward(robot);
        right(robot);
        right(robot);
        memory->dir = (memory->dir + 180) % 360;
    }
}

//initializes robot's memory and starts DFS algorithm
void search_for_markers(Grid *grid, Robot *robot, int draw)
{
    struct robot_memory memory;
    memory.pos = (Position){0,0};
    memory.visited = (Position*)malloc(INITIAL_SIZE*sizeof(Position));
    memory.num_visited = 0;
    memory.visited_sz = INITIAL_SIZE;
    memory.dir = 0;
    recursive_dfs(grid, robot, &memory, draw);
    free(memory.visited);
}

int main(int argc, char *argv[])
{
    int draw = 0;
    int visuals = 0;
    int error = 0;
    for (int i=1;i<argc;++i) {
        if (!strncmp(argv[i], "-v", 2)) {
            if (i == argc-1) {
                error = 1;
            } else {
                visuals = atoi(argv[i+1]);
                draw = 1;
                ++i;
            }
        } else if (!strncmp(argv[i], "-s", 2)) {
            if (i == argc-1) {
                error = 1;
            } else {
                delta_time = atoi(argv[i+1]);
                ++i;
            }
        } else if (!strncmp(argv[i], "-h", 2)) {
            char *help_message = "Usage:\n\t-v - 0 (colours visited cells), 1 (shows carving out of the obstacles), 2 (both 0 and 1)\n\t-s - sets delta time to the given number of milliseconds";
            message(help_message);
            exit(0);
        }
    }
    if (error || visuals < 0 || visuals > 2 || delta_time < 0) {
        char *err_message = "Invalid arguments\n";
        message(err_message);
        exit(1);
    }

    srand(time(NULL));
    Grid grid;
	initialize_grid(&grid, draw && (visuals > 0));
    Robot robot;
    initialize_robot(&robot, &grid);
    search_for_markers(&grid, &robot, draw && (visuals!=1));
    free_grid(&grid);
	return 0;
}
