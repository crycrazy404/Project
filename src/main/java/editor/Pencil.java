package editor;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Pencil extends DrawElement{
    private Color color;
    private double x,y;
    private double thickness = 1;
    @Override
    public void draw(GraphicsContext gc) {
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(thickness);
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
