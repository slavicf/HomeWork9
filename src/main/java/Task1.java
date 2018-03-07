import com.mashape.unirest.http.exceptions.UnirestException;
import entity.ResponseSearch;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Task1 extends Application {

    private static final double WIDTH = 1200;                // Ширина окна
    private static final double HEIGHT = 800;               // Высота окна
    private static Pane pane = new GridPane();
    private static Scene scene = new Scene(pane, WIDTH, HEIGHT);
    private static Insets margin = new Insets(5);
    private static TextField textField;
    private static Button btn1;
    private static Button btn2;
//    private static Label label;
    private static TextArea textArea;
    private static WebView webview;
    private static ResponseSearch responseSearch;

    public static void main(String[] args) {
        launch(args);
    }

    private static Button button(String text, int column, int row, int width, int heigth) {
        Button button = new Button(text);
        button.setPrefSize(width, heigth);
        GridPane.setConstraints(button, column, row);
        GridPane.setMargin(button, margin);
        pane.getChildren().add(button);
        return button;
    }

    public static Label label(int column, int row, int width, int height) {
        Label label = new Label();
        label.setPrefSize(width, height);
        label.setAlignment(Pos.TOP_LEFT);
        label.setStyle("-fx-border-color:silver; -fx-background-color: white;");    // -fx-background-color: #CCFF99
        GridPane.setConstraints(label, column, row);
        GridPane.setMargin(label, new Insets(2));
        pane.getChildren().add(label);
        return label;
    }

    public static TextArea textArea(int column, int row, int width, int height) {
        TextArea textArea = new TextArea();
        textArea.setPrefSize(width, height);
        textArea.setStyle("-fx-border-color:silver; -fx-background-color: white;");    // -fx-background-color: #CCFF99
        GridPane.setConstraints(textArea, column, row);
        GridPane.setMargin(textArea, new Insets(2));
        pane.getChildren().add(textArea);
        return textArea;
    }

    public static WebView webView(int column, int row, int width, int height) {
        WebView frame = new WebView();
        frame.setPrefSize(width, height);
        GridPane.setConstraints(frame, column, row);
        GridPane.setMargin(frame, margin);
        pane.getChildren().add(frame);
        return frame;
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

    public static void search() {
        String query = textField.getText();
        new Thread(() -> {
            try {
//            System.out.printf(YouTubeAPI.search(query));
                responseSearch = YouTubeAPI.search(query, 1);

                String result = "";
                result = ("Title\t\t\t" + responseSearch.items[0].snippet.title + "\n");
                result += ("Channel ID\t\t" + responseSearch.items[0].snippet.channelTitle + "\n");
                result += ("Published at\t" + LocalDate.parse(responseSearch.items[0].snippet.publishedAt, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX")).toString() + "\n\n");
//            String target = responseSearch.items[i].snippet.publishedAt;
//            DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX", Locale.ENGLISH);
//            result +=  df.parse(target).toString() + "\n\n";
                textArea.setText(result);
            } catch (UnirestException e) {
                e.printStackTrace();
            }
        }).start();
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("YouTube Search");
        primaryStage.setResizable(false);
        primaryStage.getIcons().add(new Image("https://cdn1.iconfinder.com/data/icons/logotypes/32/youtube-256.png"));
        textField = textField("Camila Cabello - Havana (Audio)", 0, 0);
        btn1 = button("Search", 1, 0, 100, 25);
        btn2 = button("View", 1, 2, 100, 25);
//        label = label(0, 1, 1100, 175);
        textArea = textArea(0, 1, 1100, 175);
        webview = webView(0,2,800,600);

        btn1.setOnAction(event -> {
            search();
        });

        btn2.setOnAction(event -> {
            String url = "https://www.youtube.com/embed/" + responseSearch.items[0].id.videoId;
//            String url = "https://www.youtube.com/watch?v=" + responseSearch.items[0].id.videoId;
            webview.getEngine().load(url);
        });

//        String link = "https://www.youtube.com/watch?v=kJQP7kiw5Fk";
//        Media media = new Media(new URL(link).toURI().toString());
//        MediaPlayer player = new MediaPlayer(media);
//        MediaView mediaView = new MediaView(player);
//        player.play();
//        mediaView.setY(500);
//        GridPane.setConstraints(mediaView, 0, 3);
//        GridPane.setMargin(mediaView, margin);
//        pane.getChildren().add(mediaView);


        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
