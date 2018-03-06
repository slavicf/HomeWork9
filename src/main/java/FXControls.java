import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class FXControls {

    private static Pane pane;
    private static Insets margin = new Insets(5);

    public static void setPane(Pane pane) {
        FXControls.pane = pane;
    }
    public static Button button(String text, int column, int row, int width, int heigth) {
        Button button = new Button(text);
        button.setPrefSize(width, heigth);
        GridPane.setConstraints(button, column, row);
        GridPane.setMargin(button, margin);
        pane.getChildren().add(button);
        return button;
    }
    public static Label label(String text, int column, int row) {
        Label label = new Label(text);
        label.setPrefSize(700, 755);
        label.setAlignment(Pos.TOP_LEFT);
        label.setStyle("-fx-border-color:silver; -fx-background-color: white;");    // -fx-background-color: #CCFF99
        GridPane.setConstraints(label, column, row);
        GridPane.setMargin(label, new Insets(2));
        pane.getChildren().add(label);
        return label;
    }
    public static TextField textField(String text, int column, int row) {
        TextField textField = new TextField(text);
        textField.setPrefColumnCount(20);
        textField.setAlignment(Pos.CENTER_LEFT);
        GridPane.setConstraints(textField, column, row);
        GridPane.setMargin(textField, margin);
        pane.getChildren().add(textField);
        return textField;
    }

}
