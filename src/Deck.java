//Палуба
public class Deck {

    private Point position;
    private char aliveView, deadView;
    private boolean isAlive;

    public Deck( Point position, char aliveView, char deadView ) {

        this.position = position;
        this.aliveView = aliveView;
        this.deadView = deadView;
        isAlive = true;
    }
    public Deck( Point position, char aliveView, char deadView, boolean isAlive) {//

        this.position = position;
        this.aliveView = aliveView;
        this.deadView = deadView;
        this.isAlive = isAlive;
    }

    public boolean getIsAlive() {

        return isAlive;
    }

    public Point getPosition() {

        return position;
    }

    public char getView() {

        return isAlive ? aliveView : deadView;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }
}
