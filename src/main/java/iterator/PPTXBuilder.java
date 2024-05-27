package iterator;
import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xslf.usermodel.XSLFSlide;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class PPTXBuilder implements Aggregate {

    private List<XSLFSlide> slides;

    public PPTXBuilder(String path) throws FileNotFoundException {
        try {
            FileInputStream fis = new FileInputStream(path);
            XMLSlideShow ppt = new XMLSlideShow(fis);
            fis.close();
            slides = ppt.getSlides();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Iterator getIterator() {
        return new PPTXIterator();
    }

    private class PPTXIterator implements Iterator {

        int count = 0;

        @Override
        public XSLFSlide getSlide(int index){
            return slides.get(index);
        }

        @Override
        public boolean hasNext() {
            if (count + 1 < slides.size())  return true;
            return false;
        }

        @Override
        public int getCurrent(){
            return count;
        }

        @Override
        public boolean hasPrev() {
            if (count - 1 >= 0)  return true;
            return false;
        }

        @Override
        public XSLFSlide Next() {
            if (hasNext()) return slides.get(++count);
            else{
                count = 0;
                return slides.get(count);
            }
        }

        @Override
        public XSLFSlide Prev() {
            if (hasPrev()) return slides.get(--count);
            else{
                count = slides.size();
                return slides.get(count - 1);
            }
        }
    }
}