public class Item {
    private double value;
    private double weight;
    private double compareVal;

    public Item(double value, double weight) {
        this.value = value;
        this.weight = weight;
        compareVal = value/weight;
    }

    public double getWeight() {
        return weight;
    }

    public double getCompareVal() {
        return compareVal;
    }

    public double getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "Item{" +
                "value=" + value +
                ", weight=" + weight +
                '}';
    }
}
