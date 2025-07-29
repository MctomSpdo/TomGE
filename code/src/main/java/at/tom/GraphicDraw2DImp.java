package at.tom;

import at.tom.engine.graphics.api.Color;
import at.tom.engine.graphics.api.GraphicDraw2D;
import at.tom.engine.position.Vector2D;

public class GraphicDraw2DImp extends GraphicDraw2D {
    @Override
    public void draw() {
        strokeWidth(0.01f);
        stroke(Color.GREEN);
        line(-1f, -1f, 0.9f, 0.9f);
        fill(0.5f, 0.5f, 0.5f);
        rect(-0.8f, -0.8f, 0.8f, 0.7f);
        point(0f, 0f);

        var points = points();

        for(float i = -1f; i <= 1f; i += 0.01f) {
            points.point(i, -0.3f);
        }

        points.point(0f, 0f)
                .point(new Vector2D(0.9f, 0.9f))
                .draw();
    }
}
