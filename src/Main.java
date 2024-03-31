import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.*;

import shipandport.Port;
import shipandport.Ship;
import container.*;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner in = new Scanner(new File("C:\\Users\\User\\Desktop\\Baduman\\ShipsAndPort\\src\\input_2.txt"));
        PrintStream out = new PrintStream(new File("C:\\Users\\User\\Desktop\\Baduman\\ShipsAndPort\\src\\output_2.txt"));
        ArrayList<Port> allPorts = new ArrayList<Port>();
        ArrayList<Ship> allShips = new ArrayList<Ship>();
        ArrayList<Container> allContainers = new ArrayList<Container>();

        int actionCount = in.nextInt();
        in.nextLine();

        String[] actions = new String[actionCount];

        int containerID = 0;
        int portID = 0;
        int shipID = 0;

        for (int i = 0; i < actionCount; i++) {
            actions[i] = in.nextLine();
            String[] splitActions = actions[i].split(" ");

            int actionType = Integer.valueOf(splitActions[0]);

            switch (actionType) {
                case 1:
                    int portIDValue = Integer.valueOf(splitActions[1]);
                    int containerWeight = Integer.valueOf(splitActions[2]);

                    if (splitActions.length == 4) {
                        String containerType = splitActions[3];
                        if ("R".equals(containerType)) {
                            allPorts.get(portIDValue).containers.add(new RefrigeratedContainer(containerID, containerWeight));
                        } else if ("L".equals(containerType)) {
                            allPorts.get(portIDValue).containers.add(new LiquidContainer(containerID, containerWeight));
                        }
                    } else {
                        if (containerWeight <= 3000) {
                            allPorts.get(portIDValue).containers.add(new BasicContainer(containerID, containerWeight));
                        } else {
                            allPorts.get(portIDValue).containers.add(new HeavyContainer(containerID, containerWeight));
                        }
                    }
                    allContainers.add(allPorts.get(portIDValue).containers.get(allPorts.get(portIDValue).containers.size() - 1));
                    containerID++;
                    break;

                case 2:
                    int portIndex = Integer.valueOf(splitActions[1]);
                    Port shipPort = allPorts.get(portIndex);
                    int shipIDValue = Integer.valueOf(splitActions[2]);
                    int maxBasic = Integer.valueOf(splitActions[3]);
                    int maxHeavy = Integer.valueOf(splitActions[4]);
                    int maxLiquid = Integer.valueOf(splitActions[5]);
                    int maxRefrigerated = Integer.valueOf(splitActions[6]);
                    double fuelCapacity = Double.valueOf(splitActions[7]);

                    allShips.add(new Ship(shipID, shipPort, shipIDValue, maxBasic, maxHeavy, maxLiquid, maxRefrigerated, fuelCapacity));
                    shipID++;
                    break;

                case 3:
                    double x = Double.valueOf(splitActions[1]);
                    double y = Double.valueOf(splitActions[2]);
                    allPorts.add(new Port(portID, x, y));
                    portID++;
                    break;

                case 4:
                    int shipIndexLoad = Integer.valueOf(splitActions[1]);
                    int containerIndexLoad = Integer.valueOf(splitActions[2]);
                    allShips.get(shipIndexLoad).load(allContainers.get(containerIndexLoad));
                    break;

                case 5:
                    int shipIndexUnload = Integer.valueOf(splitActions[1]);
                    int containerIndexUnload = Integer.valueOf(splitActions[2]);
                    allShips.get(shipIndexUnload).unLoad(allContainers.get(containerIndexUnload));
                    break;

                case 6:
                    int shipIndexSail = Integer.valueOf(splitActions[1]);
                    int portIndexSail = Integer.valueOf(splitActions[2]);
                    allShips.get(shipIndexSail).sailTo(allPorts.get(portIndexSail));
                    break;

                case 7:
                    int shipIndexRefuel = Integer.valueOf(splitActions[1]);
                    double fuelAmount = Double.valueOf(splitActions[2]);
                    allShips.get(shipIndexRefuel).reFuel(fuelAmount);
                    break;

                default:

                    break;
            }
        }


        allPorts.sort(Comparator.comparingInt(Port::getID));


        for (Port port : allPorts) {
            if (port.getID() != 0) {
                out.print("\n");
            }
            out.printf("Port %d: (%.2f, %.2f)\n", port.getID(), port.getX(), port.getY());


            port.getContainers().sort(Comparator.comparing(Container::getType).thenComparingInt(Container::getID));


            printContainersInPort(port, out);


            port.getCurrent().sort(Comparator.comparingInt(Ship::getID));


            printShipsInPort(port, out);
        }


        in.close();
        out.close();
    }

    private static void printContainersInPort(Port port, PrintStream out) {
        Map<String, List<Integer>> containerMap = new HashMap<>();

        for (Container container : port.getContainers()) {
            containerMap.computeIfAbsent(container.getType(), k -> new ArrayList<>()).add(container.getID());
        }

        for (Map.Entry<String, List<Integer>> entry : containerMap.entrySet()) {
            out.printf("  %sContainer:", entry.getKey());
            for (Integer id : entry.getValue()) {
                out.printf(" %d", id);
            }
            out.println();
        }
    }

    private static void printShipsInPort(Port port, PrintStream out) {
        for (Ship ship : port.getCurrent()) {
            out.printf("\n  Ship %d: %.2f", ship.getID(), ship.getFuel());


            ship.getCurrentContainers().sort(Comparator.comparing(Container::getType).thenComparingInt(Container::getID));


            printContainersInShip(ship, out);
        }
    }

    private static void printContainersInShip(Ship ship, PrintStream out) {
        Map<String, List<Integer>> containerMap = new HashMap<>();

        for (Container shipContainer : ship.getCurrentContainers()) {
            containerMap.computeIfAbsent(shipContainer.getType(), k -> new ArrayList<>()).add(shipContainer.getID());
        }

        for (Map.Entry<String, List<Integer>> entry : containerMap.entrySet()) {
            out.printf("\n    %sContainer:", entry.getKey());
            for (Integer id : entry.getValue()) {
                out.printf(" %d", id);
            }
        }
    }
}
