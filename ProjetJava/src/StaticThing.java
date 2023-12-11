import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
public class StaticThing {
    //définition des variables privé (uniquement disponible ici)
    private double SizeX, SizeY;
    private double x, y;
    private ImageView imageView;
    //définition des instances objet public (accessible partout)
    public StaticThing(String fileName, double sizeX, double sizeY, double x, double y) {
        this.SizeX = sizeX;
        this.SizeY = sizeY;
        this.x = x;
        this.y = y;
        // création et téléchargement de l'image
        Image image = new Image(fileName);
        imageView = new ImageView(image);
        // SizeX et SizeY : taille de l'image
        // x et y : position de l'image
        imageView.setFitWidth(SizeX);
        imageView.setFitHeight(SizeY);
        imageView.setX(x);
        imageView.setY(y);
    }
    public ImageView getImageView() {
        return imageView;
    }

    public double getX() {return x;}

    public void setX(double x) {this.x = x;}
    public double getY() {return y;}

    public void setY(double y) {this.y = y;}
}
