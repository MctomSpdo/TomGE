package at.tom.engine.graphics;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class GraphicsSurveillanceThread {

    private static final Logger LOG = LogManager.getLogger();

    private final GraphicsEngine graphicsEngine;
    private final boolean logging;
    private ScheduledExecutorService executor;
    private long lastFrameCount = 0;
    private float fps;



    public GraphicsSurveillanceThread(GraphicsEngine graphicsEngine) {
        this.graphicsEngine = graphicsEngine;
        this.logging = true;
        init();
    }

    public GraphicsSurveillanceThread(GraphicsEngine graphicsEngine, boolean logging) {
        this.graphicsEngine = graphicsEngine;
        this.logging = logging;
        init();
    }

    public void init() {
        executor = Executors.newScheduledThreadPool(1);

    }

    public void run() {
        Runnable runnable = () -> {
            long currentFrameCount = graphicsEngine.getFrameCount();
            fps = currentFrameCount - lastFrameCount;
            lastFrameCount = currentFrameCount;
            graphicsEngine.setFps(fps);

            if(logging) {
                LOG.debug("FPS: {}", fps);
            }
        };

        executor.scheduleAtFixedRate(runnable, 0, 1000, TimeUnit.MILLISECONDS);
    }

    public void stop() {
        executor.shutdown();
    }
}

