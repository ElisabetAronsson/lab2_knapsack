public class Item {
    private final double value;
    private final double weight;
    private final double compareVal;

    public Item(double value, double weight) {
        this.value = value;
        this.weight = weight;
        compareVal = value/weight;
    }

    public Item(Item item) {
        this.value = item.value;
        this.weight = item.weight;
        this.compareVal = item.compareVal;
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