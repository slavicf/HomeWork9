import javafx.scene.control.Control;

public class Layout {

    public static Control set(Control control, int x, int y, int width, int height) {
        control.setLayoutX(x);
        control.setLayoutY(y);
        control.setPrefSize(width, height);
        return control;
    }
}
