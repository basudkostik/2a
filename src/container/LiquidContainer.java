package container;
public class LiquidContainer extends HeavyContainer {

    public LiquidContainer(int ID, int weight) {
        super(ID, weight);
    }

    @Override
    public double consumption() {
        // Fuel consumption for LiquidContainer: 4.00 per unit of weight
        return 4.00 * super.getWeight();
    }
    @Override
    public String getType() {
        return "Liquid";
    }
}
