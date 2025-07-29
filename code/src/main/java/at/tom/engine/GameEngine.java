package at.tom.engine;

import at.tom.engine.graphics.GraphicDraw;
import at.tom.engine.graphics.GraphicsEngine;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GameEngine {
    private static final Logger LOG = LogManager.getLogger();

    private GraphicsEngine graphicsEngine;

    public GameEngine(GraphicDraw graphicDraw) {
        graphicsEngine = new GraphicsEngine(graphicDraw);

    }

    public void init() {

    }

    public void run() {
        LOG.info("Starting Game Engine");

        graphicsEngine.run();
    }

}
