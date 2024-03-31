package container;
public class HeavyContainer extends Container {

    public HeavyContainer(int ID, int weight) {
        super(ID, weight);
    }

    @Override
    public double consumption() {
        // Fuel consumption for HeavyContainer: 3.00 per unit of weight
        return 3.00 * super.getWeight();
    }

    @Override
    public String getType() {
        return "Heavy";
    }

    @Override
    public void setWeight(int weight) {
        if (weight>5000){
            super.setWeight(weight);
        }
        else{
            System.out.println("This is a basic container");
        }
    }

}
