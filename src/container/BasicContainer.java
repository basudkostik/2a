package container;
public class BasicContainer extends Container {

    public BasicContainer(int ID, int weight) {
        super(ID, weight);
    }

    @Override
    public double consumption() {
        // Fuel consumption for BasicContainer: 2.50 per unit of weight
        return 2.50 * super.getWeight();
    }

    @Override
    public String getType() {
        return "Basic";
    }

    @Override
    public void setWeight (int weight){
        if (weight<=5000){
            super.setWeight(weight);
        }
        else{
            System.out.println("This is a heavy container");
        }
    }


}
