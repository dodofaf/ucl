#include "graphics.h"
#include <string.h>

#define CELL_SIZE 30
#define BORDER_WIDTH 2
#define MAX_MARKERS 10
#define MIN_WIDTH 15
#define MAX_WIDTH 40
#define MIN_HEIGHT 15
#define MAX_HEIGHT 40

//times between actions in milliseconds
extern int delta_time;

//stores coordinates
typedef struct {
	int x;
	int y;
} Position;

//compares to positions for equality
int equal(Position a, Position b);

//returns next position in the given direction
Position next_position(Position pos, int dir);

//stores parameters of the grid
typedef struct {
	int height;
	int width;
	int** map;
    Position* markers;
	int number_of_markers;
} Grid;

//creates grid of a random size (in defined boundaries),
//then generates random oval-shaped arena with random obsticales that don't create unreachable places
//if draw is 1, shows each step of generation of the arena
void initialize_grid(Grid *grid, int draw);

//frees grid->map and grid->markers
void free_grid(Grid *grid);

//covers marker at the position pos with while square in the background
void delete_marker(Grid *grid, Position pos);

//covers cell at the position pos with square of a colour col in the background
void colour_cell(Position pos, colour col);

//add marker to the given position
void add_marker(Grid *grid, Position pos);
