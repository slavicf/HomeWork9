import com.mashape.unirest.http.exceptions.UnirestException;
import entity.ResponseSearch;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Task1 extends Application {

    private static final double WIDTH = 800;                // Ширина окна
    private static final double HEIGHT = 800;               // Высота окна
    private static Button button;
    private static Label label;

    public static void main(String[] args) {
        launch(args);
    }

    private void windowSetup(Stage primaryStage, Pane pane) {
        primaryStage.setTitle("YouTube Search");
        primaryStage.setResizable(false);
//        Image icon = new Image(getClass().getResourceAsStream("icons/youtube-256.png"));
//        primaryStage.getIcons().add(icon);
        primaryStage.getIcons().add(new Image("https://cdn1.iconfinder.com/data/icons/logotypes/32/youtube-256.png"));
    }

    @Override
    public void start(Stage primaryStage) {
        Pane pane = new GridPane();
        Scene scene = new Scene(pane, WIDTH, HEIGHT);

        windowSetup(primaryStage, pane);

        FXControls.setPane(pane);

        TextField textField = FXControls.textField("", 0,0);
        button = FXControls.button("Search", 1, 0, 100, 25);
        label = FXControls.label("", 0, 1);
        button.setOnAction(event -> {
//            label.setText("123\n234\n345\n456\n567\n678\n789\n890\n");
            String query = textField.getText();
            ResponseSearch responseSearch = null;
            try {
                responseSearch = YouTubeAPI.search(query, 5);

                for (int i = 0; i < responseSearch.items.length; i++) {
                    System.out.println("Title -------- " + responseSearch.items[i].snippet.title);
                    System.out.println("Descriptin --- " + responseSearch.items[i].snippet.description);
                    System.out.println();
                }

            } catch (UnirestException e) {
                e.printStackTrace();
            }
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < responseSearch.items.length; i++) {
                stringBuilder.append("Title\t\t" + responseSearch.items[i].snippet.title + "\n");
                stringBuilder.append("Description\t" + responseSearch.items[i].snippet.description + "\n");
                System.out.println("Title\t\t" + responseSearch.items[i].snippet.title);
                System.out.println("Description\t" + responseSearch.items[i].snippet.description);
                System.out.println();
            }
            label.setText(stringBuilder.toString());
        });

        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
