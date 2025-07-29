package at.tom;

import at.tom.engine.GameEngine;

public class Main {
    public static void main(String[] args) {
        var graphicDraw = new GraphicDraw2DImp();
        GameEngine gameEngine = new GameEngine(graphicDraw);
        gameEngine.run();
    }
}