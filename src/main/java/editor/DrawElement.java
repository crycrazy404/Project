package editor;

import javafx.scene.canvas.GraphicsContext;

public interface DrawElement{
    void draw(GraphicsContext gc, double x, double y);
}
