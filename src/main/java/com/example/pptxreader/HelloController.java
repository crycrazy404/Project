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
import javafx.stage.DirectoryChooser;
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
    @FXML
    Button nextButton;
    @FXML
    Button prevButton;
    public PPTXBuilder pptxBuild;
    public Iterator imgIter;
    GraphicsContext gc;
    private BufferedImage image = new BufferedImage(960,540, BufferedImage.TYPE_INT_ARGB);
    private final TextParser textParser = new TextParser();
    private final Factory drawFactory = new Factory();
    Bot bot;

    public void onNextButtonClick(){
        imgIter.Next().draw(image.createGraphics());
        Image fxImage = SwingFXUtils.toFXImage(image, null);
        imgView.setImage(fxImage);
        drawCanvas.getGraphicsContext2D().clearRect(0,0, drawCanvas.getWidth(), drawCanvas.getHeight());
    }
    public void onPrevButtonClick(){
        imgIter.Prev().draw(image.createGraphics());
        Image fxImage = SwingFXUtils.toFXImage(image, null);
        imgView.setImage(fxImage);
        drawCanvas.getGraphicsContext2D().clearRect(0,0, drawCanvas.getWidth(), drawCanvas.getHeight());
    }

    public void onPptxOpenButtonClick(){
        openPPTXFIle();
        imgIter.getSlide(0).draw(image.createGraphics());
        Image fxImage = SwingFXUtils.toFXImage(image, null);
        imgView.setImage(fxImage);
        drawCanvas.setDisable(false);
        prevButton.setDisable(false);
        nextButton.setDisable(false);
        gc = drawCanvas.getGraphicsContext2D();
    }
    private void openPPTXFIle(){
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
    }
    public void onPencilButtonClick(){
        setCurrentTool(0);
    }
    public void onMarkerButtonClick(){
        setCurrentTool(1);
    }
    public void onEraserButtonClick(){
        setCurrentTool(2);
    }


    private void setCurrentTool(Integer toolType) {
        drawFactory.getElement(toolType);
        drawCanvas.setOnMousePressed(e -> {
            gc.beginPath();
            drawFactory.getElement(toolType).setXY(e.getX(), e.getY());
            drawFactory.getElement(toolType).draw(gc);
            gc.lineTo(e.getX(), e.getY());
            gc.stroke();
        });
        drawCanvas.setOnMouseDragged(e -> {
            drawFactory.getElement(toolType).setXY(e.getX(), e.getY());
            drawFactory.getElement(toolType).draw(gc);
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

    private BufferedImage saveImage() {
        DirectoryChooser dc = new DirectoryChooser();
        dc.setTitle("Выберите папку для сохранения");
        dc.setInitialDirectory(new File(System.getProperty("user.home")));

        File selectedDirectory = dc.showDialog(null);

        if (selectedDirectory != null) {
            String fileName = "snapshot_" + (imgIter.getCurrent() + 1) + ".png";
            File file = new File(selectedDirectory, fileName);

            WritableImage stackPaneImage = new WritableImage((int) mainPane.getBoundsInParent().getWidth(), (int) mainPane.getBoundsInParent().getHeight());
            mainPane.snapshot(null, stackPaneImage);
            BufferedImage bufferedImage = SwingFXUtils.fromFXImage(stackPaneImage, null);
            try {
                ImageIO.write(bufferedImage, "png", file);
                return bufferedImage;
            } catch (IOException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Ошибка");
                alert.setHeaderText("Файл не сохранен");
                alert.setContentText("Возникла ошибка при сохранении файла! Попробуйте сохранить его еще раз.");
                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setHeaderText("Файл не сохранен");
            alert.setContentText("Папка не выбрана");
            alert.showAndWait();
        }
        return null;
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
