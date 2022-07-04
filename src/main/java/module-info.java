module com.github.youssefwadie.sudokugame {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.github.youssefwadie.sudokugame to javafx.fxml;
    exports com.github.youssefwadie.sudokugame;
}