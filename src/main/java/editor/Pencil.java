package editor;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Pencil implements DrawElement{
    @Override
    public void draw(GraphicsContext gc, double x, double y) {
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(1);
        gc.lineTo(x, y);
        gc.stroke();
    }
}
