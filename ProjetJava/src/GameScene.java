import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;

import java.util.Random;

public class GameScene extends Scene {
    //définition des variables privé (uniquement disponible ici)
    private Camera camera;
    private StaticThing backgroundLeft;
    private StaticThing backgroundRight;
    private int numberOfLives;
    private StaticThing heart;
    private Hero hero;
    private Timeline backgroundLoop;
    private StaticThing backgroundMiddle;
    private StaticThing obstacle1;
    private StaticThing obstacle2;
    private Rectangle obstacle1hitbox;
    private Rectangle obstacle2hitbox;
    private Rectangle heroHitbox;


    //définition des instances objet public (accessible partout)
    public GameScene(Group root, Camera camera, int windowWidth, int windowHeight) {
        super(root, windowWidth, windowHeight);
        this.camera = camera;
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////
        // Création du décor statique
        backgroundLeft = new StaticThing("file:/Users/33677/OneDrive/Documents/Ecole d'ingé/2e année/S7/INFO/java/Ressources audio et image pour le runner-20231108/img/desert.png", 800, 400, -400, 0);
        backgroundMiddle = new StaticThing("file:/Users/33677/OneDrive/Documents/Ecole d'ingé/2e année/S7/INFO/java/Ressources audio et image pour le runner-20231108/img/desert.png", 800, 400, 400, 0);
        backgroundRight = new StaticThing("file:/Users/33677/OneDrive/Documents/Ecole d'ingé/2e année/S7/INFO/java/Ressources audio et image pour le runner-20231108/img/desert.png", 800, 400, 1200, 0);
        obstacle1 = new StaticThing("file:/Users/33677/OneDrive/Documents/Ecole d'ingé/2e année/S7/INFO/java/Ressources audio et image pour le runner-20231108/img/obstacle.png", 120, 70, 2000, 280);
        obstacle2 = new StaticThing("file:/Users/33677/OneDrive/Documents/Ecole d'ingé/2e année/S7/INFO/java/Ressources audio et image pour le runner-20231108/img/obstacle2.png", 120, 70, 1000, 150);
        root.getChildren().add(backgroundLeft.getImageView());
        root.getChildren().add(backgroundMiddle.getImageView());
        root.getChildren().add(backgroundRight.getImageView());
        root.getChildren().add(obstacle1.getImageView());
        root.getChildren().add(obstacle2.getImageView());
        obstacle1hitbox = new Rectangle(810, 290, 100, 70);
        obstacle1hitbox.setFill(null);
        obstacle1hitbox.setStroke(Color.BLUE);
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////
        // Création des cœurs pour représenter les vies
        numberOfLives = 3;
        for (int i = 0; i < numberOfLives; i++) {
            heart = new StaticThing("file:/Users/33677/OneDrive/Documents/Ecole d'ingé/2e année/S7/INFO/java/Ressources audio et image pour le runner-20231108/img/coeur.png", 40, 40, 40, 40);
            heart.getImageView().setX(i * 50);
            heart.getImageView().setY(10);
            root.getChildren().add(heart.getImageView());
        }
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////
        // Création du héros et positionnement initial
        hero = new Hero(0, 0, 0);
        //hero.getImageView().setPreserveRatio(true);
        hero.setX(200);
        hero.setY(250);
        root.getChildren().addAll(hero.getImageView());
        heroHitbox = new Rectangle(hero.getX() + 15, hero.getY() + 15, 60, 70);
        heroHitbox.setFill(null);
        heroHitbox.setStroke(Color.RED);
        root.getChildren().add(heroHitbox);

        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////
        // Gestion des raccourcis clavier : barre d'espace pour sauter et touche X pour tirer
        setOnKeyPressed(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent event) {
                KeyCode keyPressed = event.getCode();
                if (keyPressed == KeyCode.SPACE) {
                    hero.jumpingUp();
                } else if (keyPressed == KeyCode.X) {
                    hero.shoot();
                    root.getChildren().add(obstacle1hitbox);
                    root.getChildren().add(obstacle2hitbox);
                }
            }
        });

        setOnKeyReleased(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent event) {
                KeyCode keyReleased = event.getCode();
                if (keyReleased == KeyCode.SPACE) {
                    hero.jumpingDown();
                } else if (keyReleased == KeyCode.X) {
                    hero.endShoot();
                    root.getChildren().add(obstacle1hitbox);
                }
            }
        });
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////
        // Création de la boucle qui va faire bouger l'image de fond
        backgroundLoop = new Timeline(new KeyFrame(Duration.millis(15), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                updateBackground();
            }
        }));
        backgroundLoop.setCycleCount(Timeline.INDEFINITE);
        backgroundLoop.play(); // Démarre la boucle du fond
    }
    public Camera getgameCamera() {return camera;}

    // Méthode pour adapter la position du fond à la position de la caméra
    public void render() {
        //adapte la position du fond à la position de la camera
        double cameraX = camera.getX();
        backgroundLeft.getImageView().setX(cameraX - 800);
        backgroundRight.getImageView().setX(cameraX + 800);
        backgroundMiddle.getImageView().setX(cameraX);
    }

    // Méthode pour arrêter la boucle de fond
    public void stopBackgroundLoop() {
        backgroundLoop.stop();
    }

    // Méthode pour mettre à jour le mouvement de l'image de fond
    private void updateBackground() {
        int speed = 3;
        double x1 = backgroundLeft.getX();
        double x2 = backgroundRight.getX();
        double x3 = backgroundMiddle.getX();
        double x4 = obstacle1.getX();
        double x5 = obstacle2.getX();
        // Si l'image de fond est complètement sortie de l'écran en x=4, on réinitialise sa position
        if (x3 < -399) {
            backgroundLeft.getImageView().setX(1200);
            backgroundLeft.setX(1200);
            backgroundMiddle.getImageView().setX(400);
            backgroundMiddle.setX(400);
            backgroundRight.getImageView().setX(-400);
            backgroundRight.setX(-400);
        } else {
            // On met à jour la position des images de fond en fonction de la vitesse
            backgroundLeft.getImageView().setX(x1 - speed);
            backgroundLeft.setX((x1 - speed));
            backgroundMiddle.getImageView().setX(x3 - speed);
            backgroundMiddle.setX((x3 - speed));
            backgroundRight.getImageView().setX(x2 - speed);
            backgroundRight.setX((x2 - speed));
        }
        if (x4 < -200) {
            Random random = new Random();
            int ran1 = random.nextInt(3);
            obstacle1.getImageView().setX(1500 + ran1 * 10);
            obstacle1.setX(1500 + ran1 * 10);
        } else {
            obstacle1.getImageView().setX(x4 - speed);
            obstacle1.setX((x4 - speed));
            if (x4 < -200) {
                Random random = new Random();
                int ran1 = random.nextInt(3);
                obstacle1.getImageView().setX(1100 + ran1 * 10);
                obstacle1.setX(1100 + ran1 * 10);
            } else {
                obstacle1.getImageView().setX(x4 - speed);
                obstacle1.setX((x4 - speed));
            }
        }
        heroHitbox.setX(hero.getX() + 15);
        heroHitbox.setY(hero.getY() + 15);
        obstacle1hitbox.setX(obstacle1.getX() + 10);
        obstacle1hitbox.setY(280);
        obstacle1hitbox.setX(obstacle2.getX() + 10);
        obstacle1hitbox.setY(150);
    }
}

