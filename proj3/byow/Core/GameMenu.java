package byow.Core;

import edu.princeton.cs.introcs.StdDraw;
import java.awt.Color;
import java.awt.Font;

import java.time.Duration;
import java.time.Instant;

public class GameMenu {
    public static  void drawSeed(String seed){ //dont care the input

            StdDraw.clear(Color.BLACK);
            StdDraw.setPenColor(Color.WHITE);
            Font fontBig = new Font("Monaco", Font.BOLD, 40);
            StdDraw.setFont(fontBig);
            StdDraw.text(Engine.WIDTH / 2, Engine.HEIGHT * 0.75, "ENTER SEED:");

            /* If the game is not over, display encouragement, and let the user know if they
             * should be typing their answer or watching for the next round. */

            Font fontSmall = new Font("Monaco", Font.BOLD, 20);
            StdDraw.setFont(fontSmall);
            StdDraw.text(Engine.WIDTH/2, Engine.HEIGHT *0.25, "Press S to start");
            StdDraw.text(Engine.WIDTH/2, Engine.HEIGHT / 2, seed);
            StdDraw.show();
    }

    public static void drawFrame(){
        /* Take the input string S and display it at the center of the screen,
         * with the pen settings given below. */

            StdDraw.setCanvasSize(Engine.WIDTH * 16, Engine.HEIGHT * 16);
            StdDraw.clear(Color.BLACK);
            StdDraw.setPenColor(Color.WHITE);
            StdDraw.setXscale(0,Engine.WIDTH);
            StdDraw.setYscale(0, Engine.HEIGHT);
            Font fontBig = new Font("Monaco", Font.BOLD, 30);
            StdDraw.setFont(fontBig);
            StdDraw.text(Engine.WIDTH / 2, Engine.HEIGHT -5, "CS61BL: The Game");

            /* If the game is not over, display encouragement, and let the user know if they
             * should be typing their answer or watching for the next round. */

            Font fontSmall = new Font("Monaco", Font.BOLD, 20);
            StdDraw.setFont(fontSmall);
            StdDraw.text(Engine.WIDTH/2, Engine.HEIGHT / 2 + 2, "New Game (N)");
            StdDraw.text(Engine.WIDTH/2, Engine.HEIGHT / 2, "Load Game (L)");
            StdDraw.text(Engine.WIDTH/2, Engine.HEIGHT / 2 - 2, "Replay Game(R)");
            StdDraw.text(Engine.WIDTH/2, Engine.HEIGHT / 2 - 4, "Quit (Q)");
            StdDraw.text(Engine.WIDTH/2, Engine.HEIGHT / 2 - 10, "WASD for player1(prey&) to move");
            StdDraw.text(Engine.WIDTH/2, Engine.HEIGHT / 2 - 12, "TFGH for player2(hunter@) to drop walls");
            StdDraw.text(Engine.WIDTH/2, Engine.HEIGHT / 2 - 14, "IJKL for player2(hunter@) to move");

            StdDraw.show();
    }

    public static void win() {
            StdDraw.setCanvasSize(Engine.WIDTH * 16, Engine.HEIGHT * 16);
            StdDraw.clear(Color.BLACK);
            StdDraw.setPenColor(Color.WHITE);
            StdDraw.setXscale(0,Engine.WIDTH);
            StdDraw.setYscale(0, Engine.HEIGHT);
            Font fontBig = new Font("Monaco", Font.BOLD, 30);
            StdDraw.setFont(fontBig);
            StdDraw.text(Engine.WIDTH / 2, Engine.HEIGHT -5, "Hunter wins the game!");
            Engine.end = Instant.now();
            Engine.timeElapsed = Duration.between(Engine.start, Engine.end);
            StdDraw.text(Engine.WIDTH / 2, Engine.HEIGHT -15, "Time still left:" + (Engine.currTime -
                    Engine.timeElapsed.toSecondsPart()));
            StdDraw.show();
            StdDraw.pause(5000);
            System.exit(0);
    }

        public static void lose() {
                StdDraw.setCanvasSize(Engine.WIDTH * 16, Engine.HEIGHT * 16);
                StdDraw.clear(Color.BLACK);
                StdDraw.setPenColor(Color.WHITE);
                StdDraw.setXscale(0,Engine.WIDTH);
                StdDraw.setYscale(0, Engine.HEIGHT);
                Font fontBig = new Font("Monaco", Font.BOLD, 30);
                StdDraw.setFont(fontBig);
                StdDraw.text(Engine.WIDTH / 2, Engine.HEIGHT -5, "Time used up! Hunter loses the game!");
                StdDraw.show();
                StdDraw.pause(5000);
                System.exit(0);
        }


}
