package at.tom;

import at.tom.engine.graphics.GraphicDraw;

public class GraphicDrawImp extends GraphicDraw {
    @Override
    public void draw() {
        strokeWidth(1);
        stroke(0, 1, 0);
        //line(0, 0, 90, 90);
        fill(0.5f, 0.5f, 0.5f);
        rect(20, 20, 70, 90);
    }
}
