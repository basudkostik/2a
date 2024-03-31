package shipandport;
import container.*;
import java.util.ArrayList;
public class Ship implements IShip {
    private final int ID,totalWeightCapacity;
    private double fuel;
    public Port currentPort;
    private int currentLiftingWeight,currentLiftingAllContainers,
            currentLiftingHeavyContainers,currentLiftingRefrigeratedContainers,
            currentLiftingLiquidContainers;

    private final ArrayList<Container> liftingContainers;
    private final int maxNumberOfAllContainers,maxNumberOfHeavyContainers,
            maxNumberOfRefrigeratedContainers, maxNumberOfLiquidContainers;
    private final double fuelConsumptionPerKM;

    public Ship(int ID, Port p, int totalWeightCapacity, int maxNumberOfAllContainers, int maxNumberOfHeavyContainers, int maxNumberOfRefrigeratedContainers, int maxNumberOfLiquidContainers, double fuelConsumptionPerKM) {
        liftingContainers = new ArrayList<>();
        this.currentLiftingWeight = 0;
        this.currentLiftingAllContainers = 0;
        this.currentLiftingHeavyContainers = 0;
        this.currentLiftingRefrigeratedContainers = 0;
        this.currentLiftingLiquidContainers = 0;

        this.maxNumberOfAllContainers = maxNumberOfAllContainers;
        this.maxNumberOfHeavyContainers = maxNumberOfHeavyContainers;
        this.maxNumberOfRefrigeratedContainers = maxNumberOfRefrigeratedContainers;
        this.maxNumberOfLiquidContainers = maxNumberOfLiquidContainers;

        this.fuelConsumptionPerKM = fuelConsumptionPerKM;
        this.fuel = 0;

        this.ID = ID;
        this.totalWeightCapacity = totalWeightCapacity;
        this.currentPort = p;
        p.incomingShip(this);

    }

    public ArrayList<Container> getCurrentContainers() {
        return liftingContainers;
    }

    public Port getCurrentPort() {
        return currentPort;
    }
    public int getShipID() {
        int shipID = 0;
        return shipID;
    }

    public boolean sailTo(Port p) {
        double containerRates = liftingContainers.stream().mapToDouble(Container::consumption).sum();
        if (fuel >= (currentPort.getDistance(p) * (fuelConsumptionPerKM + containerRates))) {
            fuel -= (currentPort.getDistance(p) * (fuelConsumptionPerKM + containerRates));
            currentPort.outgoingShip(this);
            currentPort = p;
            p.incomingShip(this);
            return true;
        }
        return false;
    }

    public void reFuel(double newFuel) {
        fuel += newFuel;
    }


    public boolean load(Container cont) {
        if (currentPort.getContainers().contains(cont)) {
            if (cont.getWeight() + currentLiftingWeight <= totalWeightCapacity) {
                if (currentLiftingAllContainers < maxNumberOfAllContainers) {
                    switch (cont.getType()) {
                        case "heavy":
                            if (currentLiftingHeavyContainers < maxNumberOfHeavyContainers) {
                                updateLiftingCounts(cont);
                                addContainerToShip(cont);
                                return true;
                            }
                            break;
                        case "liquid":
                            if (currentLiftingLiquidContainers < maxNumberOfLiquidContainers) {
                                updateLiftingCounts(cont);
                                addContainerToShip(cont);
                                return true;
                            }
                            break;
                        case "refrigerator":
                            if (currentLiftingRefrigeratedContainers < maxNumberOfRefrigeratedContainers) {
                                updateLiftingCounts(cont);
                                addContainerToShip(cont);
                                return true;
                            }
                            break;
                        default:
                            updateLiftingCounts(cont);
                            addContainerToShip(cont);
                            return true;
                    }
                }
            }
        }

        return false;
    }

    private void updateLiftingCounts(Container cont) {
        currentLiftingAllContainers++;
        currentLiftingWeight += cont.getWeight();

        if ("heavy".equals(cont.getType())) {
            currentLiftingHeavyContainers++;
        } else if ("liquid".equals(cont.getType())) {
            currentLiftingLiquidContainers++;
        } else if ("refrigerator".equals(cont.getType())) {
            currentLiftingRefrigeratedContainers++;
        }
    }

    private void addContainerToShip(Container cont) {
        if (!liftingContainers.contains(cont)) {
            for (int i = 0; i < liftingContainers.size(); i++) {
                if (cont.getID() < liftingContainers.get(i).getID()) {
                    liftingContainers.add(i, cont);
                    break;
                }
            }
        }
        if (!liftingContainers.contains(cont)) {
            liftingContainers.add(cont);
        }
        currentPort.getContainers().remove(cont);
    }

    public int getID() {
        return ID;
    }

    public double getFuel() {
        return fuel;
    }

    public boolean unLoad(Container cont) {
        if (liftingContainers.contains(cont) && !currentPort.getContainers().contains(cont)) {
            currentPort.getContainers().add(cont);
            liftingContainers.remove(cont);
            switch (cont.getType()) {
                case "heavy":
                    currentLiftingAllContainers--;
                    currentLiftingHeavyContainers--;
                    currentLiftingWeight -= cont.getWeight();
                    break;
                case "liquid":
                    currentLiftingAllContainers--;
                    currentLiftingHeavyContainers--;
                    currentLiftingLiquidContainers--;
                    currentLiftingWeight -= cont.getWeight();
                    break;
                case "refrigerator":
                    currentLiftingAllContainers--;
                    currentLiftingHeavyContainers--;
                    currentLiftingRefrigeratedContainers--;
                    currentLiftingWeight -= cont.getWeight();
                    break;
                case "basic":
                    currentLiftingAllContainers--;
                    currentLiftingWeight -= cont.getWeight();
                    break;
            }
            return true;
        }
        return false;
    }
}
