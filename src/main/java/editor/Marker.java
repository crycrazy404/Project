package editor;

import javafx.scene.canvas.GraphicsContext;

import javafx.scene.paint.Color;


public class Marker extends DrawElement{
    private Color color = Color.YELLOW;
    private double x,y;
    private double thickness = 5;
    @Override
    public void draw(GraphicsContext gc) {
        gc.setStroke(color);
        gc.setLineWidth(thickness);
        gc.setGlobalAlpha(0.5);
        gc.lineTo(x, y);
        gc.stroke();
    }
    @Override
    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public void setXY(double x, double y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public void setThickness(double thickness) {
        this.thickness = thickness;
    }
}
