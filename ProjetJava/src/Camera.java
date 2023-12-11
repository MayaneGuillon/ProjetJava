public class Camera {
    //définition des variables privé (uniquement disponible ici)
    private int xcam, ycam;
    
    public Camera(int x, int y) {
        this.xcam = x;
        this.ycam = y;
    }
    public void setX(int x) {this.xcam = x;}
    public void setY(int y) {this.ycam = y;}
    public int getX() {
        return xcam;
    }
    public int getY() {return ycam;}
    @Override
    public String toString() {return xcam + "," + ycam;}
}
