package container;
public class RefrigeratedContainer extends HeavyContainer {

    public RefrigeratedContainer(int ID, int weight) {
        super(ID, weight);
    }

    @Override
    public double consumption() {
        // Fuel consumption for RefrigeratedContainer: 5.00 per unit of weight
        return 5.00 * super.getWeight();
    }
    @Override
    public String getType() {
        return "Refrigerated";
    }
}

