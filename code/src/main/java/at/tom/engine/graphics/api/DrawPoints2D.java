package at.tom.engine.graphics.api;

import at.tom.engine.position.Vector2D;

import java.util.Arrays;
import java.util.LinkedList;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.glEnd;

public class DrawPoints2D {
    private final LinkedList<Vector2D> points = new LinkedList<>();
    private boolean hasDrawn = false;

    public DrawPoints2D point(Vector2D ... points) {
        if(hasDrawn) {
            throw new IllegalStateException("DrawPoints2D has already drawn the points");
        }
        this.points.addAll(Arrays.asList(points));
        return this;
    }

    public DrawPoints2D point(float x, float y) {
        if(hasDrawn) {
            throw new IllegalStateException("DrawPoints2D has already drawn the points");
        }

        this.points.add(new Vector2D(x, y));
        return this;
    }

    public void draw() {
        hasDrawn = true;
        glBegin(GL_POINTS);
        points.forEach(p -> {
            glVertex2f(p.x, p.y);
        });
        glEnd();
    }
}
