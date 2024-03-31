package shipandport;
import container.*;
import java.util.ArrayList;

public class Port implements IPort {

    private final int ID;
    private double X;
    private double Y;
    public final ArrayList<Container> containers;
    private ArrayList<Ship> history;
    public ArrayList<Ship> current;
    public Port(int ID, double X, double Y) {
        this.ID = ID;
        this.X = X;
        this.Y = Y;
        this.containers = new ArrayList<>();
        this.history = new ArrayList<>();
        this.current = new ArrayList<>();
    }
    public int getID() {
        return ID;
    }
    public double getX() {
        return X;
    }
    public void setX(double X) {
        this.X = X;
    }
    public double getY() {
        return Y;
    }
    public void setY(double Y) {
        this.Y = Y;
    }
    public ArrayList<Container> getContainers() {
        return containers;
    }

    public ArrayList<Ship> getHistory() {
        return history;
    }

    public ArrayList<Ship> getCurrent() {
        return current;
    }

    @Override
    public void incomingShip(Ship s) {
        history.add(s);
        if (!current.contains(s)) {
            current.add(s);
        }
    }

    @Override
    public void outgoingShip(Ship s) {
        current.remove(s);
    }

    public double getDistance(Port other) {
        double distanceX = other.getX() - this.X;
        double distanceY = other.getY() - this.Y;
        return Math.sqrt(distanceX * distanceX + distanceY * distanceY);
    }

}
