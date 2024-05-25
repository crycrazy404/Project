package parser;

import org.apache.poi.xslf.usermodel.*;

import java.util.List;

public class TextParser {

    public String getTitle(XSLFSlide slide){
        XSLFTextShape titleShape = slide.getPlaceholder(0);
        if (titleShape != null) {
            String title = titleShape.getText();
            return title;
        }
        return "";
    }
    public String getSubTitle(XSLFSlide slide){
        try {
            XSLFTextShape subtitleShape = slide.getPlaceholder(1);
            if (subtitleShape != null) {
                String subtitle = subtitleShape.getText();
                return subtitle;
            }
        }catch (IndexOutOfBoundsException e){
            e.printStackTrace();
        }
        return "";
    }
    public String getText(XSLFSlide slide){
        for (XSLFShape shape : slide.getShapes()) {
            if (shape instanceof XSLFTextShape) {
                XSLFTextShape textShape = (XSLFTextShape) shape;
                if (!textShape.isPlaceholder()) {
                    String text = textShape.getText();
                    return text;
                }
            }
        }
        return "";
    }
    public void setNewSlideTitle(String title, XSLFSlide slide) {
        for (XSLFShape shape : slide.getShapes()) {
            if (shape instanceof XSLFTextShape) {
                XSLFTextShape textShape = (XSLFTextShape) shape;
                List<XSLFTextParagraph> paragraphs = textShape.getTextParagraphs();
                for (XSLFTextParagraph paragraph : paragraphs) {
                    List<XSLFTextRun> textRuns = paragraph.getTextRuns();
                    for (XSLFTextRun textRun : textRuns) {
                        String runText = textRun.getRawText();
                        if (runText != null && runText.equals(getTitle(slide))) {
                            textRun.setText(title);
                            String fontFamily = textRun.getFontFamily();
                            double fontSize = textRun.getFontSize();
                            boolean bold = textRun.isBold();
                            boolean italic = textRun.isItalic();
                            textRun.setFontFamily(fontFamily);
                            textRun.setFontSize(fontSize);
                            textRun.setBold(bold);
                            textRun.setItalic(italic);
                            textRun.setFontColor(textRun.getFontColor());
                        }
                    }
                }
            }
        }
    }
    public void setNewSlideSubTitle(String newSubtitle, XSLFSlide slide) {
        for (XSLFShape shape : slide.getShapes()) {
            if (shape instanceof XSLFTextShape) {
                XSLFTextShape textShape = (XSLFTextShape) shape;
                List<XSLFTextParagraph> paragraphs = textShape.getTextParagraphs();
                for (XSLFTextParagraph paragraph : paragraphs) {
                    List<XSLFTextRun> textRuns = paragraph.getTextRuns();
                    for (XSLFTextRun textRun : textRuns) {
                        String runText = textRun.getRawText();
                        if (runText != null && runText.equals(getSubTitle(slide))) {
                            textRun.setText(newSubtitle);
                            String fontFamily = textRun.getFontFamily();
                            double fontSize = textRun.getFontSize();
                            boolean bold = textRun.isBold();
                            boolean italic = textRun.isItalic();
                            textRun.setFontFamily(fontFamily);
                            textRun.setFontSize(fontSize);
                            textRun.setBold(bold);
                            textRun.setItalic(italic);
                            textRun.setFontColor(textRun.getFontColor());
                        }
                    }
                }
            }
        }
    }
    public void setNewSlideText(String text, XSLFSlide slide){
        for (XSLFShape shape : slide.getShapes()) {
            if (shape instanceof XSLFTextShape) {
                XSLFTextShape textShape = (XSLFTextShape) shape;
                List<XSLFTextParagraph> paragraphs = textShape.getTextParagraphs();
                for (XSLFTextParagraph paragraph : paragraphs) {
                    List<XSLFTextRun> textRuns = paragraph.getTextRuns();
                    for (XSLFTextRun textRun : textRuns) {
                        String runText = textRun.getRawText();
                        if (runText != null && runText.contains(getText(slide))) {
                            String newRunText = runText.replace(getText(slide), text);
                            textRun.setText(newRunText);
                            String fontFamily = textRun.getFontFamily();
                            double fontSize = textRun.getFontSize();
                            boolean bold = textRun.isBold();
                            boolean italic = textRun.isItalic();
                            textRun.setFontFamily(fontFamily);
                            textRun.setFontSize(fontSize);
                            textRun.setBold(bold);
                            textRun.setItalic(italic);
                            textRun.setFontColor(textRun.getFontColor());
                        }
                    }
                }
            }
        }
    }
}

