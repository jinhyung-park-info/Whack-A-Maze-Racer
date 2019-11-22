Multi-Byte string are not allowed to be entered as the username or password


Saving for the Maze Game
-------------------------
Every time the MazeCustomizationActivity is interrupted, onPause is called and
calls a method inside the MazeView that converts the maze to a file.

The format of the file is below:

--start of file--
[colour of maze]
[player character (0 for lindsey, 1 for paul)]
[number of rows]
[number of columns]
[player row]
[player column]
[column 1 of the maze]
..
[last column of the maze]
--end of file--

Format for each column of the maze: #### #### ... ####
-Where # is either a 0 or 1, where 0 means false and 1 means true
-Each set of 4 #s represents a cell
-In each cell,
    the first # indicates if there is a left wall
    the second # indicates if there is a top wall
    the third # indicates if there is a right wall
    the fourth # indicates if there is a bottom

Every time onResume is called for the maze activity, this file is read and converted
to an ArrayList that will be read by the MazeView.