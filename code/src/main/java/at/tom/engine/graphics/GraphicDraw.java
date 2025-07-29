package at.tom.engine.graphics;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static org.lwjgl.opengl.GL11.*;

abstract public class GraphicDraw {

    private static final Logger LOG = LogManager.getLogger();

    private Color stroke = new Color(0, 0,0);
    private float strokeWidth;

    private Color fill;
    private Color background = new Color(0,0,0);

    abstract public void draw();

    //region Stroke
    public void stroke(float red, float green, float blue) {
        stroke = new Color(red, green, blue);
    }

    public void stroke(Color stroke) {
        this.stroke = stroke;
    }

    public void strokeWidth(float strokeWidth) {
        this.strokeWidth = strokeWidth;
        glLineWidth(strokeWidth);
    }
    //endregion

    //region Fill
    public void fill(Color fill) {
        this.fill = fill;
    }

    public void fill(float red, float green, float blue) {
        this.fill = new Color(red, green, blue);
    }

    public void clearFill() {
        this.fill = null;
    }
    //endregion

    //region Background
    public void background(float red,  float green, float blue) {
        this.background = new Color(red,green,blue);
        glClearColor(background.red, background.green, background.blue, background.alpha);
    }

    public void background(float red,  float green, float blue, float alpha) {
        this.background = new Color(red,green,blue, alpha);
        glClearColor(background.red, background.green, background.blue, background.alpha);
    }

    public void background(Color background) {
        this.background = background;
        glClearColor(background.red, background.green, background.blue, background.alpha);
    }
    //endregion

    public void line(float startX, float startY, float endX, float endY) {
        startX = convertX(startX);
        startY = convertY(startY);
        endX = convertX(endX);
        endY = convertY(endY);

        glColor3f(stroke.red, stroke.blue, stroke.green);
        glBegin(GL_LINES);
        glVertex2f(startX, startY);
        glVertex2f(endX, endY);
        glEnd();
    }

    public void rect(float startX, float startY, float endX, float endY) {
        startX = convertX(startX);
        startY = convertY(startY);
        endX = convertX(endX);
        endY = convertY(endY);

        if(fill != null) {
            glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);
            glColor3f(fill.red, fill.blue, fill.green);
            glBegin(GL_POLYGON);
            glVertex2f(startX, startY);
            glVertex2f(startY, endY);
            glVertex2f(endY, endX);
            glVertex2f(endX, startX);
            glEnd();
        }

        //TODO: fix strokeWidths > 1
        glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
        glEnable(GL_LINE_SMOOTH);
        glHint(GL_LINE_SMOOTH_HINT, GL_NICEST);
        glColor3f(stroke.red, stroke.blue, stroke.green);
        glBegin(GL_POLYGON);
        glVertex2f(startX, startY);
        glVertex2f(startY, endY);
        glVertex2f(endY, endX);
        glVertex2f(endX, startX);
        glEnd();
    }

    private float convertX(float x) {
        return (x / 50) - 1;
    }
    private float convertY(float y) {
        return (y / 50) - 1;
    }

}
