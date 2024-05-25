package iterator;

import org.apache.poi.xslf.usermodel.XSLFSlide;

import java.util.List;

public interface Iterator {

    public XSLFSlide getSlide(int index);
    public boolean hasNext();
    public boolean hasPrev();
    public XSLFSlide Next();
    public XSLFSlide Prev();
    public int getCurrent();

}
