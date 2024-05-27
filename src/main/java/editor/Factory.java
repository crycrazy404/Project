package editor;

import java.util.HashMap;
import java.util.Map;

public class Factory {
    Map<Integer, DrawElement> drawElements;
    public Factory() {
        drawElements = new HashMap<>();
        drawElements.put(0, new Pencil());
        drawElements.put(1, new Marker());
        drawElements.put(2, new Eraser());
    }
    public Map<Integer, DrawElement> getMap(){
        return drawElements;
    }
    public DrawElement getElement(int index){
        return drawElements.get(index);
    }
}

