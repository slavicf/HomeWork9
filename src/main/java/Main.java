import com.mashape.unirest.http.exceptions.UnirestException;
import entity.ResponseSearch;
import entity.SnippetVideo;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.io.File;
import java.net.MalformedURLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Main extends Application {
    //  -------------------------------------------------------------------------
    private static Pane pane = new Pane();
    private static Pane box = new Pane();
    private static TextField textField;
    private static ImageView imageView;
    private static TextArea textArea;
    private static Button btnView;
    private static WebView webView;
    private static ResponseSearch responseSearch = new ResponseSearch();
    //  -------------------------------------------------------------------------
    private static int[] txtFld = {5, 5, 275, 20};
    private static int[] btnSch = {285, 5, 95, 20};
    private static int[] btnVew = {5, 210, 230, 40};
    private static int[] image = {120, 30, 0, 540};
    private static int[] txtArr = {5, 30, 960, 175};
    private static int[] view = {5, 30, 960, 540};
    //  -------------------------------------------------------------------------

    private static void layout(Control control, int[] prop) {
        control.setLayoutX(prop[0]);
        control.setLayoutY(prop[1]);
        control.setPrefSize(prop[2], prop[3]);
    }

    private static void textField(String text, int[] prop) {
        textField = new TextField(text);
        layout(textField, prop);
        textField.setAlignment(Pos.CENTER_LEFT);
//        pane.getChildren().add(textField);
    }

    private static Button button(String text, int[] prop) {
        Button button = new Button(text);
        layout(button, prop);
//        pane.getChildren().add(button);
        return button;
    }

    public static void label(String text, int[] prop) {
        Label label = new Label();
        label.setAlignment(Pos.TOP_LEFT);
        label.setStyle("-fx-border-color:silver; -fx-background-color: white;");    // -fx-background-color: #CCFF99
        layout(label, prop);
//        pane.getChildren().add(label);
    }

    private static void textArea(int[] prop) {
        textArea = new TextArea();
//        textArea.setStyle("-fx-border-color:silver; -fx-background-color: white;");    // -fx-background-color: #CCFF99
        layout(textArea, prop);
//        pane.getChildren().add(textArea);
    }

    private static ImageView imageView(String url, int[] prop) {
        ImageView imageView = new ImageView(new Image(url, true));
        imageView.setX(prop[0]);
        imageView.setY(prop[1]);
//        imageView.setFitWidth(prop[2]);
        imageView.setFitHeight(prop[3]);
        imageView.setPreserveRatio(true);
//        pane.getChildren().add(imageView);
        return imageView;
    }

    private static WebView webView(int[] prop) {
        WebView webView = new WebView();
        webView.setLayoutX(prop[0]);
        webView.setLayoutY(prop[1]);
        webView.setPrefSize(prop[2], prop[3]);
//        pane.getChildren().add(webView);
        return webView;
    }

    private static void search(String query) {
        new Thread(() -> {
            try {
                int maxResults = 5;
                responseSearch = YouTubeAPI.search(query, maxResults);

                SnippetVideo snippet = responseSearch.items[0].snippet;
                String result = ("Title\t\t\t" + snippet.title + "\n");
                result += ("Channel ID\t\t" + snippet.channelTitle + "\n");
                result += ("Published at\t" +
                        LocalDate.parse(snippet.publishedAt,
                                DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX")).toString() + "\n\n");
//                String target = responseSearch.items[i].snippet.publishedAt;
//                DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX", Locale.ENGLISH);
//                result += df.parse(target).toString() + "\n\n";
                textArea.setText(result);
                Platform.runLater(() -> {
                    webView.getEngine().load(null);
                    box.getChildren().clear();
                    box.getChildren().addAll(textArea, btnView, imageView);
                    imageView.setX(240);
                    imageView.setY(210);
                    imageView.setFitHeight(360);
                    imageView.setImage(new Image(snippet.thumbnails.high.url, true));
                });
            } catch (UnirestException e) {
                e.printStackTrace();
            }
        }).start();
    }

    @Override
    public void start(Stage primaryStage) throws MalformedURLException {
        Scene scene = new Scene(pane, 970, 575);
        pane.getChildren().add(box);
        primaryStage.setTitle("YouTube Search");
        primaryStage.setResizable(false);
        primaryStage.getIcons().add(new Image("https://cdn1.iconfinder.com/data/icons/logotypes/32/youtube-256.png"));

        textField("go go", txtFld);
        Button btnSearch = button("Search", btnSch);
        pane.getChildren().addAll(textField, btnSearch);

        File file = new File("lib/youtube.jpg");
        String localUrl = file.toURI().toURL().toString();
        imageView = imageView(localUrl, image);
        box.getChildren().add(imageView);

        btnView = button("View", btnVew);

        textArea(txtArr);

        webView = webView(view);

        btnSearch.setOnAction(event -> {
            search(textField.getText());
            textField.setText("");
        });

        btnView.setOnAction(event -> {
            box.getChildren().clear();
            box.getChildren().add(webView);
            String url = "https://www.youtube.com/embed/" + responseSearch.items[0].id.videoId + "?autoplay=1";
            webView.getEngine().load(url);
        });

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
