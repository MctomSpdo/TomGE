package at.tom.engine.graphics.api;

public class Color {
    public static final Color WHITE =  new Color(1, 1, 1);
    public static final Color BLACK =  new Color(0, 0, 0);
    public static final Color RED =  new Color(1, 0, 0);
    public static final Color GREEN =  new Color(0, 1, 0);
    public static final Color BLUE =  new Color(0, 0, 1);

    public float red, green, blue, alpha = 0;

    public Color() {
    }

    public Color(float red, float green, float blue) {
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    public Color(float red, float green, float blue, float alpha) {
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.alpha = alpha;
    }
}
