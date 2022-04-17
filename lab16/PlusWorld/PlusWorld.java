package PlusWorld;
import org.junit.Test;
import static org.junit.Assert.*;

import byowTools.TileEngine.TERenderer;
import byowTools.TileEngine.TETile;
import byowTools.TileEngine.Tileset;

import java.util.Random;

/**
 * Draws a world consisting of plus shaped regions.
 */
public class PlusWorld {
    private static final int WIDTH = 50;
    private static final int HEIGHT = 50;
    private static final long SEED = 2873123;
    private static final Random RANDOM = new Random(SEED);

    private static int randomCoordinate() {
        int tileNum = RANDOM.nextInt(40);
        return tileNum;
    }

    private static class Point{
        public int xCoordinate;
        public int yCoordinate;
        public Point(){
            xCoordinate = randomCoordinate();
            yCoordinate = randomCoordinate();
        }
    }

    public static void addPlus(Point point, TETile[][] tiles, int size) {
        for(int i = 0; i < 3; i++){ // left, center, right
            squareHelper(point,tiles,size);
            point.xCoordinate += size;
        }
        point.xCoordinate -= 2*size;
        point.yCoordinate += size;
        squareHelper(point,tiles,size);//top

        point.yCoordinate -= 2*size;
        squareHelper(point,tiles,size);//bottom
    }

    public static void squareHelper(Point point, TETile[][] tiles, int size) {//point is the left-bottom one
        for(int x = point.xCoordinate; x < point.xCoordinate+size; x++){
            for(int y = point.yCoordinate; y < point.yCoordinate+size; y++){
                if(!(x <0 || y < 0 || x > WIDTH || y >HEIGHT))
                tiles[x][y] = Tileset.WALL;
            }
        }
    }

    public static void addPlus(Point point, TETile[][] tiles){
        int y = point.yCoordinate;
        for (int x = point.xCoordinate-1; x < point.xCoordinate+2; x += 1) {
            tiles[x][y] = Tileset.WALL;
        }
        tiles[point.xCoordinate][y-1] = Tileset.WALL;
        tiles[point.xCoordinate][y+1] = Tileset.WALL;
    }

    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);

        Point newPoint = new Point();
        Point newPoint2 = new Point();
        Point newPoint3 = new Point();

        TETile[][] plusTiles = new TETile[WIDTH][HEIGHT];
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                plusTiles[x][y] = Tileset.NOTHING;
            }
        }
        addPlus(newPoint, plusTiles, 1);
        addPlus(newPoint2, plusTiles,2);
        addPlus(newPoint3, plusTiles, 3);

        ter.renderFrame(plusTiles);
    }
}
