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

    public int height;
    public int width;

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

        //draw internal, filled rect
        if (fill != null) {
            glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);
            glColor3f(fill.red, fill.blue, fill.green);
            glBegin(GL_POLYGON);
            glVertex2f(startX, startY);
            glVertex2f(startX, endY);
            glVertex2f(endX, endY);
            glVertex2f(endX, startY);
            glEnd();
        }

        //to avoid bad strokes (cut out corners), draw strokes as rects individual
        //setup
        var aspectRatio = (height == 0) ? 1.0f : (float) width / (float) height;
        var hX = this.strokeWidth * 0.005f;
        var hY = hX * aspectRatio;

        glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);
        glColor3f(stroke.red, stroke.green, stroke.blue);

        //top rect
        glBegin(GL_POLYGON);
        glVertex2f(startX - hX, endY - hY);
        glVertex2f(startX - hX, endY + hY);
        glVertex2f(endX + hX, endY + hY);
        glVertex2f(endX + hX, endY - hY);
        glEnd();

        //right rect
        glBegin(GL_POLYGON);
        glVertex2f(endX - hX, endY + hY);
        glVertex2f(endX + hX, endY + hY);
        glVertex2f(endX + hX, startY - hY);
        glVertex2f(endX - hX, startY - hY);
        glEnd();

        //bottom rect
        glBegin(GL_POLYGON);
        glVertex2f(startX - hX, startY + hY);
        glVertex2f(endX + hX, startY + hY);
        glVertex2f(endX + hX, startY - hY);
        glVertex2f(startX - hX, startY - hY);
        glEnd();

        //left rect
        glBegin(GL_POLYGON);
        glVertex2f(startX - hX, endY + hY);
        glVertex2f(startX + hX, endY + hY);
        glVertex2f(startX + hX, startY - hY);
        glVertex2f(startX - hX, startY - hY);
        glEnd();
    }

    private float convertX(float x) {
        return (x / 50) - 1;
    }
    private float convertY(float y) {
        return (y / 50) - 1;
    }

}
