import javafx.util.Duration;

import javafx.scene.shape.Rectangle;

public class Hero extends AnimatedThing {
    private final Rectangle hitbox;
    private double speed;
    private double jump_capacity;
    private boolean is_running;
    private boolean is_jumpingUp;
    private boolean is_jumpingDown;
    private boolean is_shooting;


    public Hero(double x, double y, int attitude){
        super("file:/Users/33677/OneDrive/Documents/Ecole d'ingé/2e année/S7/INFO/java/Ressources audio et image pour le runner-20231108/img/heros.png",x, y, 6, 80, 100,85, attitude, 0);

        // Initialisation des attributs spécifiques au héros
        this.speed = 3;
        this.jump_capacity = 60.0;
        this.is_jumpingUp = false;
        this.is_jumpingDown = false;
        this.is_shooting = false;

        // Configuration de l'attitude initiale en fonction des états du héros
        if(is_running){this.attitude = 0;}
        if(is_jumpingUp){this.attitude = 1;}
        if(is_jumpingDown){this.attitude = 2;}
        if(is_shooting){this.attitude = 3;}

        hitbox = new Rectangle(80, 98);
        hitbox.setX(x);
        hitbox.setY(y);

        // Vérification si le héros est en train de sauter vers le haut et ajustement
        if(this.is_jumpingUp){if(getCurrentIndex() == getMaxIndex()){jumpingDown();}}
    }
    @Override
    // Méthode pour faire déplacer le héros
    public void running(){setX(getX() + speed);}

    // Méthode pour déclencher le saut vers le haut
    public void jumpingUp(){
        if(!is_jumpingUp){
            setAttitude(1);
            setCurrentIndex(0);
            setMaxIndex(0);
            setWindowSizey(100);
            setWindowSizex(90);
            setFrameOffset(82);
            setY(getY() - jump_capacity);
            is_jumpingUp = true;
        }
    }
    // Méthode pour déclencher la fin du saut vers le bas
    public void jumpingDown(){
        if (is_jumpingUp) {
            is_jumpingUp = false;
            setAttitude(0);
            setCurrentIndex(0);
            setMaxIndex(6);
            setWindowSizey(105);
            setWindowSizex(90);
            setFrameOffset(83);
            setFrameDuration(Duration.seconds(0.2));
            setY(getY() + jump_capacity);
        }
    }
    // Méthode pour déclencher le tir
    public void shoot(){
        if(!is_shooting){
            setAttitude(3);
            setMaxIndex(6);
            setCurrentIndex(0);
            setWindowSizey(108);
            setWindowSizex(80);
            setFrameOffset(81);
            is_shooting = true;
        }
    }

    // Méthode pour mettre fin au tir
    public void endShoot() {
        if (is_shooting) {
            is_shooting = false;
            setAttitude(0);
            setCurrentIndex(0);
            setMaxIndex(6);
            setWindowSizey(105);
            setWindowSizex(90);
            setFrameOffset(83);
        }
    }
}

