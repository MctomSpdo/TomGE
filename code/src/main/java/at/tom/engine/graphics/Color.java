package at.tom.engine.graphics;

public class Color {

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
