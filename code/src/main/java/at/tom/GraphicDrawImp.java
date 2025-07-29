package at.tom;

import at.tom.engine.graphics.GraphicDraw;

public class GraphicDrawImp extends GraphicDraw {
    @Override
    public void draw() {
        strokeWidth(0.01f);
        stroke(0, 1, 0);
        line(-1f, -1f, 0.9f, 0.9f);
        fill(0.5f, 0.5f, 0.5f);
        rect(-0.8f, -0.8f, 0.8f, 0.7f);
    }
}
