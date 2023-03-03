import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class App extends Application {
  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage stage) throws Exception {
    stage.setTitle("Hey, That's My Fish!");
    StackPane root = new StackPane();
    stage.setScene(new Scene(root, 1920, 1080));
    stage.show();
  }
}
