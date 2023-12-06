import java.util.ArrayList;

public class Knapsack {

    private double maxWeight;
    private ArrayList<Item> itemList = new ArrayList<>();
    private double value;
    private double weightLeft;

    public Knapsack(double maxWeight){
        this.maxWeight = maxWeight;
        weightLeft = maxWeight;
    }

    public void addToList(Item item){
        itemList.add(item);
        value += item.getValue();
        weightLeft -= item.getWeight();
    }

    public Item removeFromList(int itemInt){

        Item item = itemList.get(itemInt);

        itemList.remove(item);
        value -= item.getValue();
        weightLeft += item.getWeight();

        return item;
    }

    public double getTotalValue(){
        return value;
    }

    public double getWeightLeft(){
        return weightLeft;
    }

    public double getMaxWeight() {
        return maxWeight;
    }

    public ArrayList<Item> getItemList() {
        return itemList;
    }
}
