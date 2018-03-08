import com.mashape.unirest.http.exceptions.UnirestException;
import entity.ResponseSearch;
import entity.SnippetVideo;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Task1 extends Application {

    private static Pane pane = new Pane();
    private static Scene scene;
    private static Insets margin = new Insets(5);
    private static TextField textField;
    private static Button btn1;
    private static Button btn2;
    private static ImageView imageView;
    private static TextArea textArea;
    private static WebView webview;
    private static ResponseSearch responseSearch = new ResponseSearch();

    public static void main(String[] args) {
        launch(args);
    }

    public static void textField(String text, int x, int y) {
        textField = new TextField(text);
        textField.setLayoutX(x);
        textField.setLayoutY(y);
        textField.setPrefColumnCount(28);
        textField.setAlignment(Pos.CENTER_LEFT);
        pane.getChildren().add(textField);
    }

    private static Button button(String text, int x, int y, int width, int heigth) {
        Button button = new Button(text);
        button.setPrefSize(width, heigth);
        Layout.set(button, x, y, width, heigth);
        pane.getChildren().add(button);
        return button;
    }

    public static Label label(int x, int y, int width, int height) {
        Label label = new Label();
        label.setAlignment(Pos.TOP_LEFT);
        label.setStyle("-fx-border-color:silver; -fx-background-color: white;");    // -fx-background-color: #CCFF99
        Layout.set(label, x, y, width, height);
        pane.getChildren().add(label);
        return label;
    }

    public static void textArea(int x, int y, int width, int height) {
        textArea = new TextArea();
//        textArea.setStyle("-fx-border-color:silver; -fx-background-color: white;");    // -fx-background-color: #CCFF99
        Layout.set(textArea, x, y, width, height);
        pane.getChildren().add(textArea);
    }

    public static void imageView(String url, int x, int y, int width) {
        ImageView imageView =  new ImageView(new Image(url, true));
        imageView.setX(x);
        imageView.setY(y);
        imageView.setFitWidth(width);
        imageView.setPreserveRatio(true);
        pane.getChildren().add(imageView);
    }

    public static void webView(int x, int y, int width, int height) {
        WebView frame = new WebView();
        frame.setLayoutX(x);
        frame.setLayoutY(y);
        frame.setPrefSize(width, height);
        pane.getChildren().add(frame);
    }

    public static void search(String query) {
        new Thread(() -> {
            try {
//            System.out.printf(YouTubeAPI.search(query));
                responseSearch = YouTubeAPI.search(query, 5);

                String result = "";
                SnippetVideo snippet = responseSearch.items[0].snippet;
                result = ("Title\t\t\t" + snippet.title + "\n");
                result += ("Channel ID\t\t" + snippet.channelTitle + "\n");
                result += ("Published at\t" +
                        LocalDate.parse(snippet.publishedAt,
                                DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX")).toString() + "\n\n");
//            String target = responseSearch.items[i].snippet.publishedAt;
//            DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX", Locale.ENGLISH);
//            result +=  df.parse(target).toString() + "\n\n";
                textArea.setText(result);
                Platform.runLater(() -> {
//                    imageView(thumb.url, 625, 30, thumb.width);
                    Image image = new Image(snippet.thumbnails.high.url, true);
                    imageView.setImage(image);
                });
            } catch (UnirestException e) {
                e.printStackTrace();
            }
        }).start();
    }

    @Override
    public void start(Stage stage) {
        scene  = new Scene(pane, 1110, 800);
        stage.setTitle("YouTube Search");
        stage.setResizable(false);
        stage.getIcons().add(new Image("https://cdn1.iconfinder.com/data/icons/logotypes/32/youtube-256.png"));
        textField("Camila Cabello - Havana (Audio)", 5, 5);
        textArea(5, 30, 615, 360);
        webView(5,400,1100,600);
        imageView("http://www.clipartly.com/wp-content/uploads/2017/07/Youtube-Icon-Png-Clipart.png", 625, 30, 480);
        btn1 = button("Search", 300, 5, 95, 20);
        btn2 = button("View", 400, 5, 95, 20);

        btn1.setOnAction(event -> {
            search(textField.getText());
            textField.setText("");
        });

        btn2.setOnAction(event -> {
            String url = "https://www.youtube.com/embed/" + responseSearch.items[0].id.videoId;
//            String url = "https://www.youtube.com/watch?v=" + responseSearch.items[0].id.videoId;
            webview.getEngine().load(url);
        });

        stage.setScene(scene);
        stage.show();
    }
}
