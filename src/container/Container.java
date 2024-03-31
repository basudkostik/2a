// Container.java
package container;

import java.util.Objects;

public abstract class Container {

    private int ID;
    private int weight;

    public Container(int ID, int weight) {
        this.ID = ID;
        this.weight = weight;
    }
    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public abstract double consumption();

    public boolean equals(Container other) {
        return Objects.equals(this.getType(), other.getType()) && this.getID() == other.getID()
                && this.getWeight() == other.getWeight();
    }

    public abstract String getType();

}

