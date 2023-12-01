import java.io.*;
import java.nio.Buffer;
import java.util.*;

public class KnapsackAlgorithm {
    private ArrayList<Item> itemArrayList;
    private ArrayList<Knapsack> knapsacks = new ArrayList<>();
    public KnapsackAlgorithm() throws IOException {
        String filePath = "src/InputFiles/input4Multiple";
        //String filePath = "C:/Users/elisa/OneDrive/Dokument/GitHub/lab2_knapsack/src/InputFiles/input2";
        //String filePath = "C:/Users/elisa/OneDrive/Dokument/GitHub/lab2_knapsack/src/InputFiles/input3";

        BufferedReader reader = new BufferedReader((new FileReader(filePath)));
        int amountOfBags = Integer.parseInt(reader.readLine());

        for (int i = 0; i < amountOfBags; i++) {
            knapsacks.add(new Knapsack(Double.parseDouble(reader.readLine())));
        }

        int n = Integer.parseInt(reader.readLine());

        itemArrayList = new ArrayList<>();
        for(int i = 0; i < n ; i++){
            String[] line = reader.readLine().split("\\s+");
            double value = Double.parseDouble(line[0]);
            double weight = Double.parseDouble(line[1]);
            Item item = new Item(value, weight);

            itemArrayList.add(item);
        }

        greedyApproach();

    }

    public void greedyApproach() {
        itemArrayList.sort(Comparator.comparingDouble(itemVal -> -itemVal.getCompareVal()));

        for (Item item : itemArrayList) {

            double weight = item.getWeight();

            for (Knapsack bag : knapsacks) {
                if (bag.getWeightLeft() >= weight) {
                    bag.addToList(item);
                    break;
                }
            }
        }

        /*for (Knapsack bag : knapsacks) {
            System.out.println("Weight left: " + bag.getWeightLeft());
            System.out.println("Total value: " + bag.getTotalValue());
            System.out.println("Max weight: " + bag.getMaxWeight());
            System.out.println("Items in bag: ");
            ArrayList<Item> itemsinbag = bag.getItemList();
            for (Item item : itemsinbag) {
                System.out.println(item.toString());
            }

        }*/

        // Sortera listan beroende på högst värde (högst till lägst) value/weight, ha reference till Item genom hashmap
        // Gå från vänster till höger och se om du kan lägga in den i knapsack (maxcapasity check)
        // Klar!
    }

    public void neighboursearchApproach(){
    }

    public void neighboursearchTaboApproach(){

    }
}
