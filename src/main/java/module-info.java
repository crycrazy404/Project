module com.example.pptxreader {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.apache.poi.ooxml;
    requires java.desktop;
    requires javafx.swing;
    requires telegrambots;
    requires telegrambots.meta;


    opens com.example.pptxreader to javafx.fxml;
    exports com.example.pptxreader;
    exports iterator;
    opens iterator to javafx.fxml;
}