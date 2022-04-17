package byow.Core;

import byow.TileEngine.TETile;

import java.io.Serializable;
import java.util.Random;

public class SaveWorld implements Serializable {
    public long seed;
    public Random rand;
    public TETile[][] world;
    public Point avatarLocation1;
    public Point avatarLocation2;
    public String moveString;
    public int wallNum;
    public int health;
    public int preTime;

    public SaveWorld(TETile[][] world,long seed,  Random random, Point avatar1, Point avatar2, String move,
                     int wallNum, int health, int preTime){
        this.rand = random;
        this.world = world;
        this.avatarLocation1 = avatar1;
        this.avatarLocation2 = avatar2;
        this.moveString = move;
        this.seed = seed;
        this.wallNum = wallNum;
        this.health = health;
        this.preTime = preTime;
    }
}
