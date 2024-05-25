package editor;

public class Factory {
    public static DrawElement createTool(String type) {
        switch (type) {
            case "pencil":
                return new Pencil();
            case "highlighter":
                return new Marker();
            case "eraser":
                return new Eraser();
            default:
                throw new IllegalArgumentException("Unknown tool type");
        }
    }
}

