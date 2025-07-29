package at.tom;

import at.tom.engine.graphics.GraphicDraw;

public class GraphicDrawImp extends GraphicDraw {
    @Override
    public void draw() {
        strokeWidth(10);
        stroke(1, 0, 1);
        line(0, 0, 90, 90);
        fill(0.5f, 0.5f, 0.5f);
        //rect(20, 20, 90, 90);
    }
}
