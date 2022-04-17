package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.io.Serializable;
import java.util.Random;

public class Point implements Serializable {

    public static final int max = Engine.maxRoomSize;
    public int xCoordinate;
    public int yCoordinate;

    public Point(int x, int y) {
        xCoordinate = x;
        yCoordinate = y;
    }

    public Point(Random rand, TETile[][] world) {
        xCoordinate = randomXCoordinate(rand);
        yCoordinate = randomYCoordinate(rand);

        while (world[xCoordinate][yCoordinate] != Tileset.NOTHING
                || world[xCoordinate + max][yCoordinate] != Tileset.NOTHING
                || world[xCoordinate][yCoordinate + max] != Tileset.NOTHING
                || world[xCoordinate + max][yCoordinate + max] != Tileset.NOTHING
                || world[xCoordinate + max / 2][yCoordinate] != Tileset.NOTHING
                || world[xCoordinate + max / 2][yCoordinate + max] != Tileset.NOTHING
                || world[xCoordinate][yCoordinate + max / 2] != Tileset.NOTHING
                || world[xCoordinate + max][yCoordinate + max / 2] != Tileset.NOTHING) {
            int tileNum = rand.nextInt(2);
            switch (tileNum) {
                case 0:
                    xCoordinate = randomXCoordinate(rand);
                    break;
                default:
                    yCoordinate = randomYCoordinate(rand);
            }
        }


    }

    private static int randomXCoordinate(Random rand) {
        int tileNum = RandomUtils.uniform(rand, 2, Engine.WIDTH - max - 2);
        return tileNum;
    }

    private static int randomYCoordinate(Random rand) {
        int tileNum = RandomUtils.uniform(rand, 2, Engine.HEIGHT - max - 2);
        return tileNum;
    }
}
