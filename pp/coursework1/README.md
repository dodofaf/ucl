# Description

This program is the realization of stage 5 of Coursework 1 of Principles of Programming.

It creates a grid of a random size and makes a random oval-shaped arena with obstacles and markers inside it. There is only one big free space inside it, which is created by using a DFS-like algorithm that carves out this space.

Robot searches for markers using Depth First Search. The robot doesn't know its coordinates, direction, or dimensions of the arena.

# Arguments
There are three possible arguments you can add to change the work of the program:
1) -v: changes visual display:
- 0 - colours visited cells in yellow, if the robot will return to them, or red otherwise
- 1 - shows how obstacles are carved out
- 2 - combines 0 and 1
2) -s: sets the time between the robot's actions to the given number in milliseconds. 50 by default
3) -h: prints information above on a window and doesn't run the main program

# Compilation
```bash
gcc -o main main.c grid.c robot.c graphics.c
```
# Usage
Without arguments:
```bash
./main | java -jar drawapp-4.5.jar
```
With arguments:
```bash
./main -v 2 -s 10 | java -jar drawapp-4.5.jar
```