package at.tom.engine.graphics;

import org.lwjgl.Version;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryStack;

import java.nio.IntBuffer;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.GLFW.GLFW_FALSE;
import static org.lwjgl.glfw.GLFW.GLFW_RESIZABLE;
import static org.lwjgl.glfw.GLFW.GLFW_TRUE;
import static org.lwjgl.glfw.GLFW.GLFW_VISIBLE;
import static org.lwjgl.glfw.GLFW.glfwWindowHint;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.NULL;

public class GraphicsEngine {

    private static final Logger LOG = LogManager.getLogger();

    private long window;
    private int windowWidth = 400;
    private int windowHeight = 300;
    private String windowTitle = "TomsGraphicsEngine";
    private long frameCount = 0;
    private float fps = 0;
    private GraphicsSurveillanceThread surveillanceThread;
    private GraphicDraw graphicDraw;

    //region Constructor
    public GraphicsEngine(GraphicDraw graphicDraw) {
        this.graphicDraw = graphicDraw;
        this.graphicDraw.height = windowHeight;
        this.graphicDraw.width = windowWidth;
    }

    public GraphicsEngine(GraphicDraw graphicDraw,String windowTitle) {
        this.graphicDraw = graphicDraw;
        this.windowTitle = windowTitle;
        this.graphicDraw.height = windowHeight;
        this.graphicDraw.width = windowWidth;
    }
    //endregion

    //region Getter and Setter
    public long getFrameCount() {
        return frameCount;
    }

    public float getFps() {
        return fps;
    }

    void setFps(float fps) {
        this.fps = fps;
    }
    //endregion

    public void run() {
        LOG.info("Starting LWJGL engine: {}", Version.getVersion());

        init();
        loop();

        // Free the window callbacks and destroy the window
        glfwFreeCallbacks(window);
        glfwDestroyWindow(window);

        // Terminate GLFW and free the error callback
        glfwTerminate();
        glfwSetErrorCallback(null).free();

        surveillanceThread.stop();
    }

    private void init() {
        LOG.debug("Initializing GraphicsEngine");
        //init graphics surveillance
        surveillanceThread = new GraphicsSurveillanceThread(this);

        // Setup an error callback. The default implementation
        // will print the error message in System.err.
        GLFWErrorCallback.createPrint(System.err).set();

        // Initialize GLFW. Most GLFW functions will not work before doing this.
        if ( !glfwInit() )
            throw new IllegalStateException("Unable to initialize GLFW");

        // Configure GLFW
        glfwDefaultWindowHints(); // optional, the current window hints are already the default
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // the window will stay hidden after creation
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE); // the window will be resizable

        //anti aliasing
        glfwWindowHint(GLFW_SAMPLES, 6);

        // Create the window
        window = glfwCreateWindow(this.windowWidth, this.windowHeight, this.windowTitle, NULL, NULL);
        if ( window == NULL )
            throw new RuntimeException("Failed to create the GLFW window");

        // Setup a key callback. It will be called every time a key is pressed, repeated or released.
        glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
            if ( key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE )
                glfwSetWindowShouldClose(window, true); // We will detect this in the rendering loop
        });

        //resizing logic:
        glfwSetWindowSizeCallback(window, (window, width, height) -> {
            this.windowHeight = height;
            this.windowWidth = width;
            graphicDraw.height = height;
            graphicDraw.width = width;
            LOG.debug("Windows resized to size: {}x{}", width, height);
            glViewport(0, 0, width, height);
        });

        // Get the thread stack and push a new frame
        try ( MemoryStack stack = stackPush() ) {
            IntBuffer pWidth = stack.mallocInt(1); // int*
            IntBuffer pHeight = stack.mallocInt(1); // int*

            // Get the window size passed to glfwCreateWindow
            glfwGetWindowSize(window, pWidth, pHeight);

            // Get the resolution of the primary monitor
            GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

            // Center the window
            glfwSetWindowPos(
                    window,
                    (vidmode.width() - pWidth.get(0)) / 2,
                    (vidmode.height() - pHeight.get(0)) / 2
            );
        } // the stack frame is popped automatically

        // Make the OpenGL context current
        glfwMakeContextCurrent(window);
        // Enable v-sync
        glfwSwapInterval(0);

        // Make the window visible
        glfwShowWindow(window);
    }

    private void loop() {
        //start up graphics surveillance
        surveillanceThread.run();

        // This line is critical for LWJGL's interoperation with GLFW's
        // OpenGL context, or any context that is managed externally.
        // LWJGL detects the context that is current in the current thread,
        // creates the GLCapabilities instance and makes the OpenGL
        // bindings available for use.
        GL.createCapabilities();

        // Set the clear color
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

        // Run the rendering loop until the user has attempted to close
        // the window or has pressed the ESCAPE key.
        while ( !glfwWindowShouldClose(window) ) {
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer

            frameCount++;

            //render here
            render();

            glfwSwapBuffers(window); // swap the color buffers

            // Poll for window events. The key callback above will only be
            // invoked during this call.
            glfwPollEvents();
        }
    }

    private void render() {
        /*glColor3f(1, 1, 1);
        glBegin(GL_LINES);
        glVertex2f(0.0f, 0.0f);
        glVertex2f(1.0f, 4.0f);
        glEnd();

        glBegin(GL_LINES);
        glVertex2f(0.0f, 0.0f);
        glVertex2f(1.0f, -1.0f);
        glEnd(); */

        graphicDraw.draw();
    }

    private void stop() {

    }
}
