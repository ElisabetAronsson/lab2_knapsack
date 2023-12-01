public class Item {
    private double weight;
    private double compareVal;

    public Item(double value, double weight) {
        this.weight = weight;
        compareVal = value/weight;
    }

    public double getWeight() {
        return weight;
    }

    public double getCompareVal() {
        return compareVal;
    }
}
