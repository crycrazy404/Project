package editor;

import javafx.scene.canvas.GraphicsContext;

import javafx.scene.paint.Color;

public abstract class DrawElement{
    public abstract void draw(GraphicsContext gc);
    public abstract void setColor(Color color);
    public abstract void setXY(double x, double y);
}
