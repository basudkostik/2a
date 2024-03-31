package shipandport;
import container.Container;

import java.util.ArrayList;
import java.util.List;
public interface IShip {
    /*
     * Do not modify the code in this file!
     * You are responsible for all the compilation errors that originated from the changes
     * made in any of these files including the addition or removal of libraries.
     *
     */

    ArrayList<Container>getCurrentContainers();

    boolean sailTo(Port p);

    void reFuel(double newFuel);

    boolean load(Container cont);

    boolean unLoad(Container cont);
}
