# basic-mars-rovers-kata
Refactor kata performed some years ago
https://kata-log.rocks/images/mars_rover.jpg
## 1.0 How to build and execute it
	1 - Generate the code using the maven comand: "mvn clean package"
	2 - Ejecute jar file on folder "/target" with the comand "java -jar spacestation-x.y.z.jar ${Path to folder with input files}
   


## 2.0 The problem
A squad of robotic rovers are to be landed by NASA on a plateau on
Mars. This plateau, which is curiously rectangular, must be navigated
by the rovers so that their on-board cameras can get a complete
view of the surrounding terrain to send back to Earth.

A rovers position and location is represented by a combination of x
and y co- ordinates and a letter representing one of the four cardinal
compass points. The plateau is divided up into a grid to simplify
navigation. An example position might be 0, 0, N, which means the
rover is in the bottom left corner and facing North.

In order to control a rover, NASA sends a simple string of letters. The
possible letters are 'L', 'R' and 'F'. 'L' and 'R' makes the rover spin
90 degrees left or right respectively, without moving from its current
spot. 'F' means move forward one grid point, and maintain the same
Heading.

Assume that the square directly North from (x, y) is (x, y+1).


INPUT:
The first line of input is the upper-right coordinates of the plateau, the
lower- left coordinates are assumed to be 0,0.
The rest of the input is information pertaining to the rovers that have
been deployed. Each rover has two lines of input. The first line gives
the rovers position, and the second line is a series of instructions
telling the rover how to explore the plateau.
The position is made up of two integers and a letter separated by
spaces, corresponding to the x and y co-ordinates and the rover�s
orientation.
Each rover will be finished sequentially, which means that the
second rover won't start to move until the first one has finished
Moving.

OUTPUT
The output for each rover should be its final co-ordinates and
heading.
INPUT AND OUTPUT
Test Input:

    5 5
    1 2 N
    LMLMLMLMM
    3 3 E
    MMRMMRMRRM
    
Expected Output:

    1 3 N
    5 1 E

## 3.0 Considerations
### 3.1 Input data considerations
- In space real time communications can take a lot of time due to long distances. (http://www.spaceacademy.net.au/spacelink/commdly.htm).
On earth to mars comunications that delay can take 3-21 minutes.
We cannot use a simple command line in order to execute our request. My  proposal is define a fileSystem folder.
The files to process will has de extension ".request".
- Once a file is processed we will rename the original file changing his extension to ".processed".
- The responses to the original file will have the extension ".response".
- The main mehotd will read ALL files with .request extension shorted by date of creation.
If  the row one contains rovert coordinates, the row two contains another rovert coordinates and row three contains a movement order we wont move 
rovert from the first row.
- The system will take care about plateau limitations. if a rovert try to exits the plateau we will abort the movement 
before he exits from the area and keep the rovert on the actual position without executing the rest of the movements 
in order to allow Nasa experts to recalculate the route.
- The system will take care about collisions between roverts. if a rovert have orders to crash agains another rovert we will terminate their movement on the previous step to crash.
- We prepared a system for show errors at the end of the line of response movement. But its commented until we prepare a release plan with another applications that uses our output files.
-Two roverts cannot stay in the same square at the start in order to avoid collision problems. (They are too expensive to take any risk!!)


### 3.2 Error codes

In case of error show logs with error codes in order to help maintenance teams to look after our roverts.

There will be some return codes associated
Code        | Description 

* 001         | Invalid Input parameters
* 002         | Error during rovert movement
* 003		 | Error creating controler
* 004		 | Rovert is going to exit the plateau, aborting movements
* 005		 | Rovert is going to CrashAgainst another rovert
* 999         | Unexpected system error
 



## 4.0 System requeriments
All the input data will be stored on project folder /tmp/imputdata
All request input data need 

