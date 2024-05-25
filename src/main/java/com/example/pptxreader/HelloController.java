package com.example.pptxreader;

import TeleBot.Bot;
import TeleBot.BotRunnable;
import editor.*;
import iterator.Iterator;
import iterator.PPTXBuilder;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.apache.poi.xslf.usermodel.XSLFSlide;
import parser.TextEditorPopUp;
import parser.TextParser;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class HelloController{
    @FXML
    StackPane mainPane;
    @FXML
    ImageView imgView;
    @FXML
    Button pencilButton;
    @FXML
    Canvas drawCanvas;

    public PPTXBuilder pptxBuild;
    public Iterator imgIter;

    GraphicsContext gc;
    private DrawElement currentTool;
    private BufferedImage image = new BufferedImage(960,540, BufferedImage.TYPE_INT_ARGB);
    private final TextParser textParser = new TextParser();
    Bot bot;

    public void onNextButtonClick(){
        imgIter.Next().draw(image.createGraphics());
        Image fxImage = SwingFXUtils.toFXImage(image, null);
        imgView.setImage(fxImage);
    }
    public void onPrevButtonClick(){
        imgIter.Prev().draw(image.createGraphics());
        Image fxImage = SwingFXUtils.toFXImage(image, null);
        imgView.setImage(fxImage);
    }

    public void onPptxOpenButtonClick(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PowerPoint Presentations", "*.pptx"));
        File selectedFile = fileChooser.showOpenDialog(mainPane.getScene().getWindow());
        if (selectedFile != null) {
            try {
                pptxBuild = new PPTXBuilder(selectedFile.getAbsolutePath());
                imgIter = pptxBuild.getIterator();
            } catch (FileNotFoundException e) {
                System.out.println("Файл не найден");
            }
        } else {
            System.out.println("Выбор файла отменен");
        }
        imgIter.getSlide(0).draw(image.createGraphics());
        Image fxImage = SwingFXUtils.toFXImage(image, null);
        imgView.setFitWidth(1500);
        imgView.setFitHeight(848);
        drawCanvas.setWidth(1500);
        drawCanvas.setHeight(848);
        imgView.setImage(fxImage);
        mainPane.setAlignment(imgView, Pos.CENTER);
        mainPane.setAlignment(drawCanvas, Pos.CENTER);
        gc = drawCanvas.getGraphicsContext2D();
    }

    public void onPencilButtonClick(){
        setCurrentTool("pencil");
    }
    public void onMarkerButtonClick(){
        setCurrentTool("highlighter");
    }
    public void onEraserButtonClick(){
        setCurrentTool("eraser");
    }


    private void setCurrentTool(String toolType) {
        currentTool = Factory.createTool(toolType);
        drawCanvas.setOnMousePressed(e -> {
            gc.beginPath();
            if (currentTool instanceof Eraser) {
                (currentTool).draw(gc, e.getX(), e.getY());
            } else {
                currentTool.draw(gc, e.getX(), e.getY());
            }
            gc.lineTo(e.getX(), e.getY());
            gc.stroke();
        });
        drawCanvas.setOnMouseDragged(e -> {
            if (currentTool instanceof Eraser) {
                (currentTool).draw(gc, e.getX(), e.getY());
            } else {
                gc.lineTo(e.getX(), e.getY());
                gc.stroke();
            }
        });
        drawCanvas.setOnMouseReleased(e -> gc.closePath());
    }

    public void onSendButtonClick(){
        try {
            bot.sendPhotoWithDescription(saveImage(), generateDescription());
        }catch (NullPointerException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setHeaderText("Бот не запущен");
            alert.setContentText("Запустите бота во вкладке settings");
            alert.showAndWait();
        }

    }

    public void onModalButtonClick(){
        TextEditorPopUp textEditorPopUp = new TextEditorPopUp();
        textEditorPopUp.showModal(imgIter.getSlide(imgIter.getCurrent()));
        repaint();
    }

    private BufferedImage saveImage(){
        String filePath = "src/main/resources/snapshots/snapshot_" + (imgIter.getCurrent() + 1) +".png";
        WritableImage stackPaneImage = new WritableImage( (int) mainPane.getBoundsInParent().getWidth(), (int) mainPane.getBoundsInParent().getHeight());
        mainPane.snapshot(null, stackPaneImage);
        BufferedImage bufferedImage = SwingFXUtils.fromFXImage(stackPaneImage, null);
        File file = new File(filePath);
        try {
            ImageIO.write(bufferedImage, "png", file);
            System.out.println("Файл успешно сохранен: " + filePath);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return bufferedImage;
    }

    private void repaint(){
        imgIter.getSlide(imgIter.getCurrent()).draw(image.createGraphics());
        Image fxImage = SwingFXUtils.toFXImage(image, null);
        imgView.setImage(fxImage);
    }

    private String generateDescription(){
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        XSLFSlide currentSlide = imgIter.getSlide(imgIter.getCurrent());
        String title = textParser.getTitle(currentSlide);
        String subTitle = textParser.getSubTitle(currentSlide);
        String text = textParser.getText(currentSlide);
        return String.format("Лекция %s слайд № %s \n%s\n%s\n\n%s",formatter.format(today),(imgIter.getCurrent() + 1), title, subTitle, text);
    }

    public void onBotStartButtonClick(){
        bot = new Bot();
        Thread botThread = new Thread(new BotRunnable(bot));
        botThread.start();
    }
}
