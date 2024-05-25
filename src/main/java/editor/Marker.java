package editor;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Marker implements DrawElement{
    @Override
    public void draw(GraphicsContext gc, double x, double y) {
        gc.setStroke(Color.YELLOW);
        gc.setLineWidth(5);
        gc.setGlobalAlpha(0.5);
        gc.lineTo(x, y);
        gc.stroke();
    }
}
