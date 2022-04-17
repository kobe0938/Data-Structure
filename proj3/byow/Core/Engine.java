package byow.Core;

import byow.InputDemo.InputSource;
import byow.InputDemo.KeyboardInputSource;
import byow.InputDemo.StringInputDevice;
import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import edu.princeton.cs.introcs.StdDraw;
import java.awt.Color;
import java.awt.Font;


import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


import java.io.File;

public class Engine{
    /* Feel free to change the WIDTH and HEIGHT. */
    public static final int WIDTH = 90;
    public static final int HEIGHT = 40;
    public static long seedInt;
    public static Random random;
    public static final int maxRoomSize = 6;
    public static final int minRoomSize = 4;
    public static final int maxRooms = 10;
    public static final int minRooms = 7;
    public static final int chanceHallway = 15; //change for wall to become hallway (1/chanceHallway)
    public List<Point> addHallwayPoints = new ArrayList<>();
    TERenderer ter = new TERenderer();
    public boolean alreadyN = false;
    public boolean alreadyColon = false;
    public boolean save = false;
    public Point avatar1;
    public Point avatar2;
    public String finalMoveString = "";
    public boolean playing = true;

    public static Instant start;
    public static Instant end;
    public static Duration timeElapsed;
    public static int currTime;
    public int health = 2;
    public static final int totalTime = 50;
    public static int wallNum = 5;
    public final int displayRow = 5;

    public final boolean notSubmitting = true; // https://edstem.org/us/courses/3834/discussion/540166?comment=1259648

    public static final File CWD = new File(System.getProperty("user.dir"));
    public static final File savedLocation = Utils.join(CWD, "savedGame.txt");
    /**
     * Method used for exploring a fresh world. This method should handle all inputs,
     * including inputs from the main menu.
     */
    public void interactWithKeyboard() {
        drawFrame();

        TETile[][] finalWorldFrame = new TETile[WIDTH][HEIGHT];
        InputSource inputSource = new KeyboardInputSource();
        String seed = "";
        String move = "";

        while(playing){
            if(inputSource.possibleNextInput()) {
                char nextInput = inputSource.getNextKey();
                if(nextInput == 'Q'|| nextInput == 'q'){ // quit game at start menu
                    if (notSubmitting){
                        System.exit(0);
                    }

                }


                if(!alreadyN && (nextInput == 'L' || nextInput == 'l') && savedLocation.exists()) {
                    SaveWorld toLoad = Utils.readObject(savedLocation, SaveWorld.class);
                    finalWorldFrame = toLoad.world;
                    avatar1 = toLoad.avatarLocation1;
                    avatar2 = toLoad.avatarLocation2;
                    random = toLoad.rand;
                    move = toLoad.moveString;;
                    seedInt = toLoad.seed;
                    alreadyN = true;
                    alreadyColon = false;
                    save = false;
                    wallNum = toLoad.wallNum;
                    health = toLoad.health;
                    start = Instant.now();
                    currTime = toLoad.preTime;
                    TERenderer ter = new TERenderer();
                    ter.initialize(WIDTH, HEIGHT+displayRow);
                    if (notSubmitting){
                        ter.renderFrame(finalWorldFrame);
                    }
                }else if(!alreadyN && (nextInput == 'R' || nextInput == 'r')){
                    SaveWorld toLoad = Utils.readObject(savedLocation, SaveWorld.class);
                    TERenderer ter = new TERenderer();
                    ter.initialize(WIDTH, HEIGHT+displayRow);
                    seedInt = toLoad.seed;
                    random = new Random(seedInt);
                    createWorld(finalWorldFrame);
                    alreadyN = true;

                    start = Instant.now();
                    currTime = toLoad.preTime;
                    if (notSubmitting){
                        ter.renderFrame(finalWorldFrame);
                    }

                    move = toLoad.moveString;
                    for(int i = 0; i < move.length(); i++){
                        whichWayToMove(finalWorldFrame, "" + move.charAt(i));
                        if (notSubmitting){
                            StdDraw.pause(100);
                            ter.renderFrame(finalWorldFrame);
                        }
                    }
                    if (notSubmitting){
                        StdDraw.pause(500);
//                        System.exit(0);
                    }

                } else if ((nextInput == 'N' || nextInput == 'n') && !alreadyN) {

                    drawSeed("");
                    nextInput = inputSource.getNextKey();//waiting next key, thus interupted
                    TERenderer ter = new TERenderer();
                    ter.initialize(WIDTH, HEIGHT+displayRow);

                    while (nextInput != 'S' && nextInput != 's') {
                        seed += nextInput;
                        drawSeed(seed);
                        nextInput = inputSource.getNextKey(); // edge not considered case: no next input
                    }

                    seedInt = Long.parseLong(seed);
                    random = new Random(seedInt);
                    createWorld(finalWorldFrame);
                    alreadyN = true;
                    if (notSubmitting){
                        ter.renderFrame(finalWorldFrame);
                    }

                    start = Instant.now();
                    currTime = totalTime;
                    timeElapsed = Duration.between(start, start);
                    diaplayInfo(currTime - timeElapsed.toSecondsPart(), health, wallNum, finalWorldFrame);

                } else if(!savedLocation.exists() && !alreadyN) {
                    if (notSubmitting) {
                        System.exit(0); // exist is l but no saved world
                    }
                }
                while(alreadyN && inputSource.possibleNextInput()){
                    nextInput = ' ';
                    if (StdDraw.hasNextKeyTyped()){
                        nextInput = inputSource.getNextKey();
                    }
                    end = Instant.now();
                    timeElapsed = Duration.between(start, end);
                    diaplayInfo(currTime - timeElapsed.toSecondsPart()
                            , health, wallNum, finalWorldFrame);
                    if (move.length()>4){
                        winOrLose(finalWorldFrame);
                    }
                    // letter after first s
                    if(nextInput == 'W' || nextInput == 'A' || nextInput == 'S' || nextInput == 'D' || nextInput == 'w'
                            || nextInput == 'a' || nextInput == 's' || nextInput == 'd' ||nextInput == 'I' || nextInput == 'i'
                    ||nextInput == 'J' || nextInput == 'j' || nextInput == 'K' || nextInput == 'k' ||
                            nextInput == 'L' || nextInput == 'l' || nextInput == 'T' || nextInput == 't'
                            || nextInput == 'F' || nextInput == 'f' || nextInput == 'G' || nextInput == 'g'
                            || nextInput == 'H' || nextInput == 'h'){
                        alreadyColon = false;
                        move += nextInput;
                        whichWayToMove(finalWorldFrame, "" + nextInput);

                        if (notSubmitting){
                            ter.renderFrame(finalWorldFrame);
                        }

                    }else if(nextInput == ':'){
                        alreadyColon = true;
                    }else if((nextInput == 'Q' || nextInput == 'q') && alreadyColon){ //meaning to store and quit
                        //TODO: do it later
                        save = true;
                        playing = false;
                        break;
                    }else if(nextInput == ' ') {
                        continue;
                    }
                        else {//else if other char, just ignore and skip them
                        alreadyColon = false;
                    }
                }

            }




        }
        if(save){
            finalMoveString = move;
            saveToCWD(finalWorldFrame);
            if(notSubmitting) {
                System.exit(0);
            }
        }



    }

    /**
     * Method used for autograding and testing your code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The engine should
     * behave exactly as if the user typed these characters into the engine using
     * interactWithKeyboard.
     * <p>
     * Recall that strings ending in ":q" should cause the game to quite save. For example,
     * if we do interactWithInputString("n123sss:q"), we expect the game to run the first
     * 7 commands (n123sss) and then quit and save. If we then do
     * interactWithInputString("l"), we should be back in the exact same state.
     * <p>
     * In other words, both of these calls:
     * - interactWithInputString("n123sss:q")
     * - interactWithInputString("lww")
     * <p>
     * should yield the exact same world state as:
     * - interactWithInputString("n123sssww")
     *
     * @paraminput the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public TETile[][] interactWithInputString(String input) {
        // TODO: Fill out this method so that it run the engine using the input
        // passed in as an argument, and return a 2D tile representation of the
        // world that would have been drawn if the same inputs had been given
        // to interactWithKeyboard().
        //
        // See proj3.byow.InputDemo for a demo of how you can make a nice clean interface
        // that works for many different input types.

        //getting input
        TETile[][] finalWorldFrame = new TETile[WIDTH][HEIGHT];
        InputSource inputSource = new StringInputDevice(input);
        String seed = "";
        String move = "";

        if (inputSource.possibleNextInput()) {
            char nextInput = inputSource.getNextKey();
            if(nextInput == 'Q'|| nextInput == 'q'){ // quit game at start menu
                if (notSubmitting){
                    System.exit(0);
                }
            }
            if(!alreadyN && (nextInput == 'L' || nextInput == 'l') && savedLocation.exists()) {
                SaveWorld toLoad = Utils.readObject(savedLocation, SaveWorld.class);
                finalWorldFrame = toLoad.world;
                avatar1 = toLoad.avatarLocation1;
                avatar2 = toLoad.avatarLocation2;
                random = toLoad.rand;
                finalMoveString = toLoad.moveString;;
                seedInt = toLoad.seed;
                alreadyN = true;
                alreadyColon = false;
                save = false;
                wallNum = toLoad.wallNum;
                health = toLoad.health;
                start = Instant.now();
                currTime = toLoad.preTime;
            } else if ((nextInput == 'N' || nextInput == 'n') && !alreadyN) {

                    nextInput = inputSource.getNextKey();
                    while (nextInput != 'S' && nextInput != 's') {
                        seed += nextInput;
                        nextInput = inputSource.getNextKey(); // edge not considered case: no next input
                    }

                seedInt = Long.parseLong(seed);
                random = new Random(seedInt);
                createWorld(finalWorldFrame);
                alreadyN = true;
                start = Instant.now();
                currTime = totalTime;
                timeElapsed = Duration.between(start, start);
            } else if(!savedLocation.exists() && !alreadyN) {
                if (notSubmitting) {
                    System.exit(0); // exist is l but no saved world
                }
            }
            while(alreadyN && inputSource.possibleNextInput()){
                nextInput = inputSource.getNextKey(); // letter after first s
                if(nextInput == 'W' || nextInput == 'A' || nextInput == 'S' || nextInput == 'D' || nextInput == 'w'
                        || nextInput == 'a' || nextInput == 's' || nextInput == 'd' ||nextInput == 'I' || nextInput == 'i'
                        ||nextInput == 'J' || nextInput == 'j' || nextInput == 'K' || nextInput == 'k' ||
                        nextInput == 'L' || nextInput == 'l' || nextInput == 'T' || nextInput == 't'
                        || nextInput == 'F' || nextInput == 'f' || nextInput == 'G' || nextInput == 'g'
                        || nextInput == 'H' || nextInput == 'h'){

                    alreadyColon = false;
                    move += nextInput;
                    whichWayToMove(finalWorldFrame, "" + nextInput);
                    if (notSubmitting){
                        if (move.length()>4){
                            winOrLose(finalWorldFrame);
                        }
                    }
                }else if(nextInput == ':'){
                    alreadyColon = true;
                }else if((nextInput == 'Q' || nextInput == 'q') && alreadyColon){ //meaning to store and quit
                    //TODO: do it later
                    save = true;
                    break;
                }else {//else if other char, just ignore and skip them
                    alreadyColon = false;
                }
            }

        }

        if(save){
            finalMoveString = move;
            saveToCWD(finalWorldFrame);
            if(notSubmitting) {
                System.exit(0);
            }
        }
        return finalWorldFrame;
    }
    public void drawSeed(String seed){
        if(notSubmitting) {
            GameMenu.drawSeed(seed);
        }
    }

    public void drawFrame() {
        /* Take the input string S and display it at the center of the screen,
         * with the pen settings given below. */
        if(notSubmitting) {
            GameMenu.drawFrame();
        }
    }

    public void diaplayInfo(int timeStamp, int health, int wallNum, TETile[][] world){
        if (notSubmitting){
            StdDraw.clear();
            ter.renderFrame(world);

            StdDraw.setPenColor(Color.WHITE);
            StdDraw.setXscale(0,Engine.WIDTH);
            StdDraw.setYscale(0, Engine.HEIGHT+displayRow);
            StdDraw.setFont(new Font("Monaco", Font.CENTER_BASELINE, 20));
            double currentTileX = StdDraw.mouseX();
            double currentTileY = StdDraw.mouseY();
            if (currentTileX >= 0 && currentTileX < WIDTH && currentTileY >= 0 && currentTileY < HEIGHT){
                TETile currentTile = world[(int)currentTileX][(int)currentTileY];
                StdDraw.text(WIDTH/2, HEIGHT+4, "Current Tile:" + currentTile.description());
            }
            StdDraw.text(WIDTH/2, HEIGHT+3, "Wall left:" + wallNum);
            StdDraw.text(WIDTH/2, HEIGHT+2, "Time left:" + timeStamp);
            StdDraw.text(WIDTH/2, HEIGHT+1, "Player2's Health:" + health);
            StdDraw.show();
        }
    }

    public void winOrLose(TETile[][] finalWorldFrame){
        end = Instant.now();
        timeElapsed = Duration.between(start, end);
        if (timeElapsed.toSecondsPart() >= currTime){
            if (notSubmitting){
                StdDraw.pause(200);
            }
            GameMenu.lose();
        }
        if ((avatar1.xCoordinate == avatar2.xCoordinate+1 && avatar1.yCoordinate == avatar2.yCoordinate)
        || (avatar1.xCoordinate == avatar2.xCoordinate-1 && avatar1.yCoordinate == avatar2.yCoordinate)
        || (avatar1.xCoordinate == avatar2.xCoordinate && avatar1.yCoordinate == avatar2.yCoordinate + 1)
        || (avatar1.xCoordinate == avatar2.xCoordinate && avatar1.yCoordinate == avatar2.yCoordinate - 1)
        ){
            health--;
            if (health > 0){
                secondChance(finalWorldFrame, avatar1, avatar2);
                if (notSubmitting){
                    ter.renderFrame(finalWorldFrame);
                }
            }else{
                if (notSubmitting){
                    StdDraw.pause(200);
                }

                GameMenu.win();
            }
        }
    }

    public void secondChance(TETile[][] finalWorldFrame, Point avatar1, Point avatar2){
        wallNum += 5;
        if (avatar1.xCoordinate == avatar2.xCoordinate){
            if (avatar1.yCoordinate == avatar2.yCoordinate-1){
                //a2
                //a1
                if(moveDown(finalWorldFrame, avatar1, Tileset.AVATAR1)){
                    return;
                }else if(moveLeft(finalWorldFrame, avatar1, Tileset.AVATAR1)){
                    return;
                }else if(moveRight(finalWorldFrame, avatar1, Tileset.AVATAR1)){
                    return;
                }
            }else if (avatar1.yCoordinate == avatar2.yCoordinate+1){
                if(moveUp(finalWorldFrame, avatar1, Tileset.AVATAR1)){
                    return;
                }else if(moveLeft(finalWorldFrame, avatar1, Tileset.AVATAR1)){
                    return;
                }else if(moveRight(finalWorldFrame, avatar1, Tileset.AVATAR1)){
                    return;
                }
            }
        }else{
            if (avatar1.xCoordinate == avatar2.xCoordinate-1){

                if(moveUp(finalWorldFrame, avatar1, Tileset.AVATAR1)){
                    return;
                }else if(moveDown(finalWorldFrame, avatar1, Tileset.AVATAR1)){
                    return;
                }else if(moveLeft(finalWorldFrame, avatar1, Tileset.AVATAR1)){
                    return;
                }
            }else if (avatar1.xCoordinate == avatar2.xCoordinate+1){
                if(moveDown(finalWorldFrame, avatar1, Tileset.AVATAR1)){
                    return;
                }else if(moveUp(finalWorldFrame, avatar1, Tileset.AVATAR1)){
                    return;
                }else if(moveRight(finalWorldFrame, avatar1, Tileset.AVATAR1)){
                    return;
                }
            }
        }
    }

    public void dropWallLeft(TETile[][] finalWorldFrame, Point avatarnum){
        if (finalWorldFrame[avatarnum.xCoordinate - 1][avatarnum.yCoordinate].description().equals(Tileset.FLOOR.description())){
            finalWorldFrame[avatarnum.xCoordinate - 1][avatarnum.yCoordinate] = Tileset.WALL;
            wallNum--;
        }
    }

    public void dropWallRight(TETile[][] finalWorldFrame, Point avatarnum){
        if (finalWorldFrame[avatarnum.xCoordinate + 1][avatarnum.yCoordinate].description().equals(Tileset.FLOOR.description())){
            finalWorldFrame[avatarnum.xCoordinate + 1][avatarnum.yCoordinate] = Tileset.WALL;
            wallNum--;
        }
    }

    public void dropWallUp(TETile[][] finalWorldFrame, Point avatarnum){
        if (finalWorldFrame[avatarnum.xCoordinate][avatarnum.yCoordinate + 1].description().equals(Tileset.FLOOR.description())){
            finalWorldFrame[avatarnum.xCoordinate][avatarnum.yCoordinate + 1] = Tileset.WALL;
            wallNum--;
        }
    }

    public void dropWallDown(TETile[][] finalWorldFrame, Point avatarnum){
        if (finalWorldFrame[avatarnum.xCoordinate][avatarnum.yCoordinate - 1].description().equals(Tileset.FLOOR.description())){
            finalWorldFrame[avatarnum.xCoordinate][avatarnum.yCoordinate - 1] = Tileset.WALL;
            wallNum--;
        }
    }

    public void saveToCWD(TETile[][] world){
        SaveWorld saving = new SaveWorld(world,seedInt, random, avatar1,avatar2, finalMoveString, wallNum, health, currTime - timeElapsed.toSecondsPart());
        Utils.writeObject(savedLocation, saving);
    }


    public void whichWayToMove(TETile[][] world, String direction){
        char c;
        for(int i = 0; i < direction.length(); i++){
            c = direction.charAt(i);
            if(c == 'W' || c == 'w'){
                moveUp(world, avatar2, Tileset.AVATAR2);
            }else if(c == 'A' || c == 'a'){
                moveLeft(world, avatar2, Tileset.AVATAR2);
            }else if(c == 'S' || c == 's'){
                moveDown(world, avatar2, Tileset.AVATAR2);
            }else if(c == 'D' || c == 'd'){
                moveRight(world, avatar2, Tileset.AVATAR2);
            }else if(c == 'I' || c == 'i'){
                moveUp(world, avatar1, Tileset.AVATAR1);
            }else if(c == 'J' || c == 'j'){
                moveLeft(world, avatar1, Tileset.AVATAR1);
            }else if(c == 'K' || c == 'k'){
                moveDown(world, avatar1, Tileset.AVATAR1);
            }else if(c == 'L' || c == 'l'){
                moveRight(world, avatar1, Tileset.AVATAR1);
            }else if(c == 'T' || c == 't'){
                if (wallNum > 0){
                    dropWallUp(world, avatar1);
                }
            }else if(c == 'G' || c == 'g'){
                if (wallNum > 0) {
                    dropWallDown(world, avatar1);
                }
            }else if(c == 'F' || c == 'f'){
                if (wallNum > 0) {
                    dropWallLeft(world, avatar1);
                }
            }else if(c == 'H' || c == 'h'){
                if (wallNum > 0) {
                    dropWallRight(world, avatar1);
                }
            }
            finalMoveString += c;
        }
    }

    public boolean moveUp(TETile[][] world, Point avatarnum, TETile AVATARTYPE){
        if (world[avatarnum.xCoordinate][avatarnum.yCoordinate + 1].description().equals(Tileset.FLOOR.description())){ //TODO: needs to apply to more stuff
            world[avatarnum.xCoordinate][avatarnum.yCoordinate] = Tileset.FLOOR;
            avatarnum.yCoordinate++;
            world[avatarnum.xCoordinate][avatarnum.yCoordinate] =AVATARTYPE;
            return true;
        }
        return false;
    }

    public boolean moveLeft(TETile[][] world, Point avatarnum, TETile AVATARTYPE){
        if (world[avatarnum.xCoordinate - 1][avatarnum.yCoordinate].description().equals(Tileset.FLOOR.description())){
            world[avatarnum.xCoordinate][avatarnum.yCoordinate] = Tileset.FLOOR;
            avatarnum.xCoordinate--;
            world[avatarnum.xCoordinate][avatarnum.yCoordinate] = AVATARTYPE;
            return true;
        }
        return false;
    }

    public boolean moveDown(TETile[][] world, Point avatarnum, TETile AVATARTYPE){
        if (world[avatarnum.xCoordinate][avatarnum.yCoordinate - 1].description().equals(Tileset.FLOOR.description())){
            world[avatarnum.xCoordinate][avatarnum.yCoordinate] = Tileset.FLOOR;
            avatarnum.yCoordinate--;
            world[avatarnum.xCoordinate][avatarnum.yCoordinate] = AVATARTYPE;
            return true;
        }
        return false;
    }

    public boolean moveRight(TETile[][] world, Point avatarnum, TETile AVATARTYPE){
        if (world[avatarnum.xCoordinate + 1][avatarnum.yCoordinate].description().equals(Tileset.FLOOR.description())){
            world[avatarnum.xCoordinate][avatarnum.yCoordinate] = Tileset.FLOOR;
            avatarnum.xCoordinate++;
            world[avatarnum.xCoordinate][avatarnum.yCoordinate] = AVATARTYPE;
            return true;
        }
        return false;
    }

    public void createWorld(TETile[][] world) {
        int WIDTH = world.length;
        int HEIGHT = world[0].length;

        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }


        int numRooms = RandomUtils.uniform(random, minRooms, maxRooms);
        for (int i = 0; i < numRooms; i++) {
            addRoom(world);
        }
        createHallways(addHallwayPoints, world);
        world[avatar1.xCoordinate][avatar1.yCoordinate] = Tileset.AVATAR1; //in addRoom() second line
        world[avatar2.xCoordinate][avatar2.yCoordinate] = Tileset.AVATAR2;
    }

    public void addRoom(TETile[][] world) { //need to check there is no room already there and it is not out of bounds
        Point start = new Point(this.random, world); //low-left point
        avatar1 = start; // avatar starting location will be at the lower right of last room
        avatar2 = new Point(start.xCoordinate, start.yCoordinate);
        int roomWidth = RandomUtils.uniform(this.random, this.minRoomSize, this.maxRoomSize);
        int roomHeight = RandomUtils.uniform(this.random, this.minRoomSize, this.maxRoomSize);


        //adding floor
        for (int x = start.xCoordinate; x < roomWidth + start.xCoordinate; x++) {
            for (int y = start.yCoordinate; y < roomHeight + start.yCoordinate; y++) {
                world[x][y] = Tileset.FLOOR;
            }
        }
        //at least way to reach room: adding point to be come hallway
        addHallwayPoints.add(new Point(start.xCoordinate + roomWidth, start.yCoordinate));//to right
        addHallwayPoints.add(new Point(start.xCoordinate - 1, start.yCoordinate)); //to left
        int set = random.nextInt(2);
        if (set == 0){
            addHallwayPoints.add(new Point(start.xCoordinate, start.yCoordinate + roomHeight)); // to top
        } else {
            addHallwayPoints.add(new Point(start.xCoordinate, start.yCoordinate - 1)); // to bottom
        }


        //adding Ywalls
        for (int x = start.xCoordinate - 1; x < roomWidth + start.xCoordinate + 1; x += roomWidth + 1) {
            for (int y = start.yCoordinate; y < roomHeight + start.yCoordinate; y++) {
                world[x][y] = Tileset.WALL;
                int chanceAddHallway = random.nextInt(chanceHallway);
                if (chanceAddHallway == 0 && !addHallwayPoints.contains(new Point(x, y - 1))) {
                    addHallwayPoints.add(new Point(x, y));
                }
            }
        }
        //adding Xwalls
        for (int y = start.yCoordinate - 1; y < roomHeight + start.yCoordinate + 1; y += roomHeight + 1) {
            for (int x = start.xCoordinate; x < roomWidth + start.xCoordinate; x++) {
                world[x][y] = Tileset.WALL;
                int chanceAddHallway = random.nextInt(chanceHallway);
                if (chanceAddHallway == 0 && !addHallwayPoints.contains(new Point(x - 1, y))) {
                    addHallwayPoints.add(new Point(x, y));
                }
            }
        }

        //adding corners
        world[start.xCoordinate - 1][start.yCoordinate - 1] = Tileset.WALL;
        world[start.xCoordinate - 1][start.yCoordinate + roomHeight] = Tileset.WALL;
        world[start.xCoordinate + roomWidth][start.yCoordinate - 1] = Tileset.WALL;
        world[start.xCoordinate + roomWidth][start.yCoordinate + roomHeight] = Tileset.WALL;


    }

    public void createHallways(List<Point> list, TETile[][] world) {
        for (int i = 0; i < list.size(); i++) {
            Point hall = list.get(i);
            if (hall.xCoordinate - 1 > 0 && world[hall.xCoordinate - 1][hall.yCoordinate] == Tileset.NOTHING) {
                hallLeft(world, hall);
            } else if (hall.xCoordinate + 1 < WIDTH && world[hall.xCoordinate + 1][hall.yCoordinate] == Tileset.NOTHING) {
                hallRight(world, hall);
            } else if (hall.yCoordinate - 1 > 0 && world[hall.xCoordinate][hall.yCoordinate - 1] == Tileset.NOTHING) {
                hallDown(world, hall);
            } else if (hall.yCoordinate + 1 < HEIGHT && world[hall.xCoordinate][hall.yCoordinate + 1] == Tileset.NOTHING) { //if tile on top is NOTHING
                hallUp(world, hall);
            }
        }
    }

    public void hallLeft(TETile[][] world, Point point) {
        int x = point.xCoordinate;
        int y = point.yCoordinate;
        if (y + 1 == HEIGHT || y - 1 < 0 || x - 1 < 0) { // stop if out of bounds
            return;
        }
        world[x][y] = Tileset.NOTHING;

        int stop = RandomUtils.uniform(random, WIDTH/3, WIDTH * 3/2);
        int count = 0;
        boolean end = false;
        while (!end && count < stop && world[x][y] == Tileset.NOTHING) {
            world[x][y] = Tileset.FLOOR;
            world[x][y + 1] = Tileset.WALL;
            world[x][y - 1] = Tileset.WALL;
            //adding a hallway to the hallway
            int chanceAddHallway = random.nextInt(chanceHallway);
            int side = random.nextInt(2);
            if (chanceAddHallway == 0) {
                if (side == 1 && y < HEIGHT - 5 && !addHallwayPoints.contains(new Point(x + 1, y + 1))) { //top side
                    addHallwayPoints.add(new Point(x, y + 1));
                } else if (y > 5 && !addHallwayPoints.contains(new Point(x + 1, y - 1))) { //bottom side
                    addHallwayPoints.add(new Point(x, y - 1));
                }
            }

            x--;
            count++;
            if (x < 0) {
                end = true;
            }
        }
        if (point.xCoordinate - 1 < 0) { // this if is reduldent, check line180
            world[point.xCoordinate][point.yCoordinate] = Tileset.WALL;
        } else if (x < 0 || count == stop) { // going into end --> need to set tile to a wall
            world[x + 1][y] = Tileset.WALL;
        } else { // next tile is a wall(another room or hallway)
            world[x][y] = Tileset.FLOOR;
            world[x][y + 1] = Tileset.WALL;
            world[x][y - 1] = Tileset.WALL;
            if (world[x - 1][y] == Tileset.WALL) { // run into border
                world[x - 1][y] = Tileset.FLOOR;
                if (world[x - 1][y + 1] == Tileset.NOTHING) {
                    world[x - 1][y + 1] = Tileset.WALL;
                } else {
                    world[x - 1][y - 1] = Tileset.WALL;
                }
            }
        }

    }

    public void hallRight(TETile[][] world, Point point) {
        int x = point.xCoordinate;
        int y = point.yCoordinate;
        if (y + 1 == HEIGHT || y - 1 < 0 || x + 1 == WIDTH) { //stop if out of bounds
            return;
        }
        world[x][y] = Tileset.NOTHING;


        int stop = RandomUtils.uniform(random, WIDTH/3, WIDTH * 3/2);;
        int count = 0;
        boolean end = false;
        while (!end && count < stop && world[x][y] == Tileset.NOTHING) {
            world[x][y] = Tileset.FLOOR;
            world[x][y + 1] = Tileset.WALL;
            world[x][y - 1] = Tileset.WALL;
            //adding a hallway to the hallway
            int chanceAddHallway = random.nextInt(chanceHallway);
            int side = random.nextInt(2);
            if (chanceAddHallway == 0) {
                if (side == 1 && y < HEIGHT - 5 && !addHallwayPoints.contains(new Point(x - 1, y + 1))) { //top side
                    addHallwayPoints.add(new Point(x, y + 1));
                } else if (y > 5 && !addHallwayPoints.contains(new Point(x - 1, y - 1))) { //bottom side
                    addHallwayPoints.add(new Point(x, y - 1));
                }
            }

            x++;
            count++;
            if (x == WIDTH) {
                end = true;
            }
        }
        if (point.xCoordinate + 1 == WIDTH) { // wall is already the end
            world[point.xCoordinate][point.yCoordinate] = Tileset.WALL;
        } else if (x == WIDTH || count == stop) { // going into end --> need to set tile to a wall
            world[x - 1][y] = Tileset.WALL;
        } else { // next tile is a wall(another room or hallway)
            world[x][y] = Tileset.FLOOR;
            world[x][y + 1] = Tileset.WALL;
            world[x][y - 1] = Tileset.WALL;
            if (world[x + 1][y] == Tileset.WALL) { // run into border
                world[x + 1][y] = Tileset.FLOOR;
                if (world[x + 1][y + 1] == Tileset.NOTHING) {
                    world[x + 1][y + 1] = Tileset.WALL;
                } else {
                    world[x + 1][y - 1] = Tileset.WALL;
                }
            }
        }
    }

    public void hallDown(TETile[][] world, Point point) {
        int x = point.xCoordinate;
        int y = point.yCoordinate;
        if (x + 1 == WIDTH || x - 1 < 0 || y - 1 < 0) { // stop if out of bounds
            return;
        }
        world[x][y] = Tileset.NOTHING;

        int stop = RandomUtils.uniform(random, HEIGHT/3, HEIGHT * 3/2);
        int count = 0;
        boolean end = false;
        while (!end && count < stop && world[x][y] == Tileset.NOTHING) {
            world[x][y] = Tileset.FLOOR;
            world[x + 1][y] = Tileset.WALL;
            world[x - 1][y] = Tileset.WALL;
            //adding a hallway to the hallway
            int chanceAddHallway = random.nextInt(chanceHallway);
            int side = random.nextInt(2);
            if (chanceAddHallway == 0) {
                if (side == 1 && x < WIDTH - 5 && !addHallwayPoints.contains(new Point(x + 1, y + 1))) { //right side
                    addHallwayPoints.add(new Point(x + 1, y));
                } else if (x > 5 && !addHallwayPoints.contains(new Point(x - 1, y + 1))) { //left side
                    addHallwayPoints.add(new Point(x - 1, y));
                }
            }

            y--;
            count++;
            if (y < 0) {
                end = true;
            }
        }
        if (point.yCoordinate - 1 < 0) { // wall is already the end
            world[point.xCoordinate][point.yCoordinate] = Tileset.WALL;
        } else if (y < 0 || count == stop) { // going into end --> need to set tile to a wall
            world[x][y + 1] = Tileset.WALL;
        } else { // next tile is a wall(another room or hallway)
            world[x][y] = Tileset.FLOOR;
            world[x + 1][y] = Tileset.WALL;
            world[x - 1][y] = Tileset.WALL;
            if (world[x][y - 1] == Tileset.WALL) { // run into border
                world[x][y - 1] = Tileset.FLOOR;
                if (world[x + 1][y - 1] == Tileset.NOTHING) {
                    world[x + 1][y - 1] = Tileset.WALL;
                } else {
                    world[x - 1][y - 1] = Tileset.WALL;
                }
            }
        }
    }

    public void hallUp(TETile[][] world, Point point) {
        int x = point.xCoordinate;
        int y = point.yCoordinate;
        if (x + 1 == WIDTH || x - 1 < 0 || y + 1 == HEIGHT) { // stop if out of bounds
            return;
        }
        world[x][y] = Tileset.NOTHING;

        int stop = RandomUtils.uniform(random, HEIGHT/3, HEIGHT * 3/2);;
        int count = 0;
        boolean end = false;
        while (!end && count < stop && world[x][y] == Tileset.NOTHING) {
            world[x][y] = Tileset.FLOOR;
            world[x + 1][y] = Tileset.WALL;
            world[x - 1][y] = Tileset.WALL;
            //adding a hallway to the hallway
            int chanceAddHallway = random.nextInt(chanceHallway);
            int side = random.nextInt(2);
            if (chanceAddHallway == 0) {
                if (side == 1 && x < WIDTH - 5 && !addHallwayPoints.contains(new Point(x + 1, y - 1))) { //right side
                    addHallwayPoints.add(new Point(x + 1, y));
                } else if (x > 5 && !addHallwayPoints.contains(new Point(x - 1, y - 1))) { //left side
                    addHallwayPoints.add(new Point(x - 1, y));
                }
            }

            y++;
            count++;
            if (y == HEIGHT) {
                end = true;
            }
        }
        if (point.yCoordinate + 1 < 0) { // wall is already the end
            world[point.xCoordinate][point.yCoordinate] = Tileset.WALL;
        } else if (y == HEIGHT || count == stop) { // going into end or stop --> need to set tile to a wall
            world[x][y - 1] = Tileset.WALL;
        } else { // next tile is a wall(another room or hallway)
            world[x][y] = Tileset.FLOOR;
            world[x + 1][y] = Tileset.WALL;
            world[x - 1][y] = Tileset.WALL;
            if (world[x][y + 1] == Tileset.WALL) { // run into border
                world[x][y + 1] = Tileset.FLOOR;
                if (world[x + 1][y + 1] == Tileset.NOTHING) {
                    world[x + 1][y + 1] = Tileset.WALL;
                } else {
                    world[x - 1][y + 1] = Tileset.WALL;
                }
            }
        }
    }

    public static void main(String[] args) {

        //For testing Keyboard
        Engine engine = new Engine();
        engine.interactWithKeyboard();



        //For testing inputString
//        TERenderer ter = new TERenderer();
//        ter.initialize(WIDTH, HEIGHT);
//        Engine engine = new Engine();

//        TETile[][] plusTiles = engine.interactWithInputString("lwsd");
//        ter.renderFrame(plusTiles);
    }
}
