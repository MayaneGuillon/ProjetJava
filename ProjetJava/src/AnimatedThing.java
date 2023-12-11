import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public abstract class AnimatedThing {
    private double x,y; // Position de l'objet animé
    private ImageView imageView;
    int attitude;// Attitude de l'objet animé (0: course, 1: saut vers le haut, 2: saut vers le bas, 3: tir)
    private int currentIndex; // Index actuel dans la sprite sheet
    Duration frameDuration; // Durée de chaque frame dans l'animation
    private int maxIndex; // Index maximal dans la sprite sheet
    private double windowSizeX, windowSizeY; //Largeur et hauteur de la fenêtre pour chaque frame
    private double frameOffset; // Décalage entre chaque frame dans la sprite sheet
    final private Timeline animationTimeline; // Ligne de temps pour l'animation

    public AnimatedThing(String fileName, double x, double y, int maxIndex, double windowSizeX, double windowSizeY , double frameOffset, int attitude, int currentIndex) {
        this.x = x;
        this.y = y;
        this.attitude = attitude;
        this.frameDuration = Duration.seconds(0.04);
        this.currentIndex = currentIndex;
        this.maxIndex = maxIndex;
        this.windowSizeX = windowSizeX;
        this.windowSizeY = windowSizeY;
        this.frameOffset = frameOffset;


        Image spriteSheet = new Image(fileName);
        this.imageView = new ImageView(spriteSheet);
        this.imageView.setViewport(new Rectangle2D(0, 0, windowSizeX, windowSizeY));
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////
        // Configuration de la ligne de temps pour l'animation
        this.animationTimeline = new Timeline(
                new KeyFrame(Duration.ZERO, event -> updateFrame()),
                new KeyFrame(Duration.seconds(0.01 / frameDuration.toSeconds()), event -> updateFrame())
        );
        this.animationTimeline.setCycleCount(Animation.INDEFINITE);
        this.animationTimeline.play();
        updateFrame();
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////
    }
    // Getter pour l'objet ImageView
    public ImageView getImageView() {return imageView;}

    // Méthode pour mettre à jour la frame actuelle
    private void updateFrame() {
        double xOffset = (currentIndex - 1) * frameOffset / 2;
        double yOffset = 2 * attitude * frameOffset;
        imageView.setViewport(new Rectangle2D(xOffset, yOffset, windowSizeX, windowSizeY));
        // On vérifie si l'index actuel dépasse l'index maximal, sinon on l'incrémente
        if(currentIndex > maxIndex){currentIndex = 0;}
        else{currentIndex = currentIndex + 1;}
    }
    ////////////// set pour appliquer un élément et un get pour le recuperer////////////////////
    public void setX(double x) {
        this.x = x;
        imageView.setX(x);
    }
    public double getX() {
        return x;
    }
    public void setY(double y) {
        this.y = y;
        imageView.setY(y);
    }
    public double getY() {
        return y;
    }
    //////////////////////////////////////////////////////////////////////////////////
    public void setAttitude(int newAttitude){
        this.attitude = newAttitude;
    }
    public int getAttitude(){
        return attitude;
    }
    public int getMaxIndex(){
        return maxIndex;
    }
    public void setMaxIndex(int newMaxIndex){this.maxIndex = newMaxIndex;}
    public int getCurrentIndex(){
        return currentIndex;
    }
    public void setCurrentIndex(int newCurrentIndex){
        this.currentIndex = newCurrentIndex;
    }
    public void setWindowSizey(double newSetWindowsSizey){this.windowSizeY = newSetWindowsSizey;}
    public void setWindowSizex(double newSetWindowsSizex){this.windowSizeX = newSetWindowsSizex;}
    public void setFrameOffset(double newFrameOffset){this.frameOffset = newFrameOffset;}
    void setFrameDuration(Duration newFrameDuration){this.frameDuration = newFrameDuration;}

    public abstract void running();
}
