import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.geometry.Rectangle2D;
import javafx.application.Platform;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {

        primaryStage.setTitle("Hello world");
        Group root = new Group();
        Pane pane = new Pane(root);
        Scene theScene = new Scene(pane, 1000, 400, true);
        primaryStage.setScene(theScene);

        primaryStage.show();

        Camera camera = new Camera(0,0);
        Group root2 = new Group();
        GameScene gameScene = new GameScene(root2, camera,1000,400);
        primaryStage.setScene(gameScene);
        primaryStage.show();

        for (int i = 0; i < 500; i++) {
            final int xPosition = i; // variable finale
            Platform.runLater(() -> {
                camera.setX(xPosition);
                gameScene.render();
            });
            try {Thread.sleep(5);} catch (InterruptedException e) {System.err.println("Interruption pendant la pause : " + e.getMessage());}
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}