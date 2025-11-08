#include "grid.h"
#include <stdlib.h>
#include <string.h>
#include <math.h>

int equal(Position a, Position b)
{
    return (a.x == b.x && a.y == b.y);
}

Position next_position(Position pos, int dir)
{
    int next_x = pos.x - (int)sin(M_PI * (double)dir / 180.0);
    int next_y = pos.y - (int)cos(M_PI * (double)dir / 180.0);
    return (Position){next_x, next_y};
}

// draws grid in the background
void draw_grid(Grid *grid)
{
    int width = 2*BORDER_WIDTH + grid->width * CELL_SIZE + (grid->width-1);
    int height = 2*BORDER_WIDTH + grid->height * CELL_SIZE + (grid->height-1);

    background();

    setColour(red);
    fillRect(0,0,BORDER_WIDTH, height);
    fillRect(0,0,width,BORDER_WIDTH);
    fillRect(width-BORDER_WIDTH, 0, width, height);
    fillRect(0,height-BORDER_WIDTH, width, height);

    setColour(gray);
    for (int i=1;i<grid->width;++i)
        drawLine(BORDER_WIDTH + i*(CELL_SIZE+1), 0, BORDER_WIDTH + i*(CELL_SIZE+1), height);
    for (int i=1;i<grid->height;++i)
        drawLine(0, BORDER_WIDTH + i*(CELL_SIZE+1), width, BORDER_WIDTH + i*(CELL_SIZE+1));

    setColour(black);
    for (int i=0;i<grid->height;++i) {
        for (int j=0;j<grid->width;++j) {
            if (grid->map[i][j]) {
                fillRect(BORDER_WIDTH + j*(CELL_SIZE+1)+2, BORDER_WIDTH + i*(CELL_SIZE+1) + 2,
                         CELL_SIZE-2, CELL_SIZE-2);
            }
        }
    }

    setColour(cyan);
    for (int i=0;i<grid->number_of_markers;++i) {
        int x = grid->markers[i].x;
        int y = grid->markers[i].y;
        fillOval(BORDER_WIDTH + x*(CELL_SIZE+1)+2, BORDER_WIDTH + y*(CELL_SIZE+1) + 2,
                 CELL_SIZE-2, CELL_SIZE-2);
    }
}

//generates oval-shaped arena with obstacles and one continuous empty space
//if draw is 1, shows each step of carving out obstacles
static void generate_random_arena_with_obstacles(Grid *grid, int draw)
{
    //fill whole map with obstacles
    for (int i=0;i<grid->height;++i)
        for (int j=0;j<grid->width;++j)
            grid->map[i][j] = 1;

    //random parameters of oval shape for the arena
    int a = rand() % (grid->width/8) + 3*(grid->width/8);
    int b = rand() % (grid->height/8) + 3*(grid->height/8);

    //2d array for remembering cells we already visited
    int **visited = (int**)malloc(grid->height*sizeof(int*));
    for (int i=0;i<grid->height;++i) {
        visited[i] = (int*)malloc(grid->width * sizeof(int));
        //no places are visited at first
        memset(visited[i], 0, sizeof(int) * grid->width);
    }

    //stack to perform algorithm similar to Depth First Search
    Position *stack = (Position*)malloc(sizeof(Position) * grid->height * grid->width);

    //initializing the stack and visited
    int stack_sz = 1;
    stack[0].x = grid->width/2;
    stack[0].y = grid->height/2;
    visited[grid->height/2][grid->width/2] = 1;

    //main loop of the algorithm
    while (stack_sz > 0) {
        Position top = stack[stack_sz-1];
        --stack_sz; //removing top element from the stack
        int x = top.x - grid->width/2;
        int y = top.y - grid->height/2;

        //checking if it is inside the oval shape
        if ((x*x)*(b*b) + (y*y)*(a*a) >= (a*a)*(b*b))
            continue;

        //removing the obstacle with a 90 percent chance
        grid->map[top.y][top.x] = (rand()%100 >= 90);

        //ensure that the 3x3 square in the middle is always without obstacles
        if (abs(x) <= 1 && abs(y) <= 1)
            grid->map[top.y][top.x] = 0;

        //not continuing further if obstacle remains, to avoid creating hollow spaces
        if (grid->map[top.y][top.x] == 1)
            continue;

        //adding unvisited adjacent cells
        if (visited[top.y+1][top.x] == 0) {
            stack[stack_sz].x = top.x;
            stack[stack_sz].y = top.y+1;
            ++stack_sz;
            visited[top.y+1][top.x] = 1;
        }
        if (visited[top.y][top.x+1] == 0) {
            stack[stack_sz].x = top.x+1;
            stack[stack_sz].y = top.y;
            ++stack_sz;
            visited[top.y][top.x+1] = 1;
        }
        if (visited[top.y-1][top.x] == 0) {
            stack[stack_sz].x = top.x;
            stack[stack_sz].y = top.y-1;
            ++stack_sz;
            visited[top.y-1][top.x] = 1;
        }
        if (visited[top.y][top.x-1] == 0) {
            stack[stack_sz].x = top.x-1;
            stack[stack_sz].y = top.y;
            ++stack_sz;
            visited[top.y][top.x-1] = 1;
        }

        if (draw) {
            sleep(delta_time);
            background();
            clear();
            draw_grid(grid);
        }
    }

    //freeing stack and visited
    free(stack);
    for (int i=0;i<grid->height;++i)
        free(visited[i]);
    free(visited);
}

static void generate_markers(Grid *grid)
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
    grid->number_of_markers = rand() % (count/5) + 1;
    if (grid->number_of_markers > MAX_MARKERS)
        grid->number_of_markers = MAX_MARKERS;
    grid->markers = (Position*)malloc(sizeof(Position) * grid->number_of_markers);
    for (int i=0;i<grid->number_of_markers;++i) {
        int rand_ind = rand() % count;
        while (free_cells[rand_ind].x == -1)
            rand_ind = rand() % count;
        grid->markers[i] = free_cells[rand_ind];
        free_cells[rand_ind].x = -1;
        free_cells[rand_ind].y = -1;
    }
    free(free_cells);
}

//sets window to the appropriate size
void initialize_window(Grid *grid)
{
    int width = 2*BORDER_WIDTH + grid->width * CELL_SIZE + (grid->width-1);
    int height = 2*BORDER_WIDTH + grid->height * CELL_SIZE + (grid->height-1);
    setWindowSize(width, height);
}

void initialize_grid(Grid *grid, int draw)
{
	grid->height = MIN_HEIGHT + rand()%(MAX_HEIGHT-MIN_HEIGHT);
	grid->width = MIN_WIDTH + rand()%(MAX_WIDTH-MIN_WIDTH);
	grid->map = (int**)malloc(grid->height*sizeof(int*));
	for (int i=0;i<grid->height;++i) {
        grid->map[i] = (int*)malloc(grid->width * sizeof(int));
    }
    grid->markers = NULL;
    grid->number_of_markers = 0;

    initialize_window(grid);
    generate_random_arena_with_obstacles(grid, draw);
    generate_markers(grid);
    draw_grid(grid);
}

void delete_marker(Grid *grid, Position pos)
{
    background();
    setColour(white);
    fillRect(BORDER_WIDTH + pos.x*(CELL_SIZE+1)+2, BORDER_WIDTH + pos.y*(CELL_SIZE+1) + 2,
             CELL_SIZE-2, CELL_SIZE-2);

    for (int i=0;i<grid->number_of_markers;++i) {
        if (equal(grid->markers[i], pos)) {
            grid->markers[i].x = -1;
            grid->markers[i].y = -1;
            break;
        }
    }
}

void colour_cell(Position pos, colour col)
{
    background();
    setColour(col);
    fillRect(BORDER_WIDTH + pos.x*(CELL_SIZE+1)+2, BORDER_WIDTH + pos.y*(CELL_SIZE+1) + 2,
             CELL_SIZE-2, CELL_SIZE-2);
}

void add_marker(Grid *grid, Position pos)
{
    background();
    setColour(cyan);
    for (int i=0;i<grid->number_of_markers;++i) {
        if (equal(grid->markers[i], (Position){-1,-1})) {
            grid->markers[i] = pos;
            break;
        }
    }
    fillOval(BORDER_WIDTH + pos.x*(CELL_SIZE+1)+2, BORDER_WIDTH + pos.y*(CELL_SIZE+1) + 2,
             CELL_SIZE-2, CELL_SIZE-2);
}

void free_grid(Grid *grid)
{
	for (int i=0;i<grid->height;++i) 
		free(grid->map[i]);
	free(grid->map);
    free(grid->markers);
}


