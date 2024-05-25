package parser;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.apache.poi.xslf.usermodel.XSLFSlide;

public class TextEditorPopUp {
    private TextParser textParser = new TextParser();

    public void showModal(XSLFSlide slide){
        TextArea titleArea = new TextArea();
        titleArea.setEditable(false);
        if(textParser.getTitle(slide) != null){
            titleArea.setText(textParser.getTitle(slide));
            titleArea.setEditable(true);
        }

        TextArea subtitleArea = new TextArea();
        subtitleArea.setEditable(false);
        if(textParser.getSubTitle(slide) != null){
            subtitleArea.setText(textParser.getSubTitle(slide));
            subtitleArea.setEditable(true);
        }

        TextArea textArea = new TextArea();
        textArea.setEditable(false);
        if(textParser.getText(slide) != null){
            textArea.setText(textParser.getText(slide));
            textArea.setEditable(true);
        }

        Label titleLabel = new Label("Title:");
        Label subtitleLabel = new Label("Subtitle:");
        Label textLabel = new Label("Text:");

        Button saveButton = new Button("Save");
        Button cancelButton = new Button("Cancel");

        HBox buttonBox = new HBox(saveButton, cancelButton);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setSpacing(10);

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        grid.add(titleLabel, 0, 0);
        grid.add(titleArea, 1, 0);
        grid.add(subtitleLabel, 0, 1);
        grid.add(subtitleArea, 1, 1);
        grid.add(textLabel, 0, 2);
        grid.add(textArea, 1, 2);

        VBox vbox = new VBox(grid, buttonBox);
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(10);

        Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setTitle("Modal Window");
        dialog.setScene(new Scene(vbox, 300, 200));
        saveButton.setOnAction(e -> {
            textParser.setNewSlideTitle(titleArea.getText(), slide);
            textParser.setNewSlideSubTitle(subtitleArea.getText(), slide);
            textParser.setNewSlideText(textArea.getText(), slide);
            dialog.close();
        });
        cancelButton.setOnAction(e -> dialog.close());
        dialog.showAndWait();
    }
}
