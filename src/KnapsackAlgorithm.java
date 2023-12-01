import java.io.*;
import java.nio.Buffer;
import java.util.*;

public class KnapsackAlgorithm {
    private ArrayList<Item> itemArrayList;
    public KnapsackAlgorithm() throws IOException {
        String filePath = "src/InputFiles/input2";
        //String filePath = "C:/Users/elisa/OneDrive/Dokument/GitHub/lab2_knapsack/src/InputFiles/input2";
        //String filePath = "C:/Users/elisa/OneDrive/Dokument/GitHub/lab2_knapsack/src/InputFiles/input3";

        BufferedReader reader = new BufferedReader((new FileReader(filePath)));
        int maxWeight = Integer.parseInt(reader.readLine());
        int n = Integer.parseInt(reader.readLine());

        greedyApproach(maxWeight, n, reader);

    }

    public void greedyApproach(int maxWeight, int n, BufferedReader reader) throws IOException {
        double currentWeight = 0;

        itemArrayList = new ArrayList<>();
        for(int i = 0; i < n ; i++){
            String[] line = reader.readLine().split("\\s+");
            double value = Double.parseDouble(line[0]);
            double weight = Double.parseDouble(line[1]);
            Item item = new Item(value, weight);

            itemArrayList.add(item);
        }
        Collections.sort(itemArrayList, Comparator.comparingDouble(itemVal -> -itemVal.getCompareVal()));
        for(int i = 0; i < itemArrayList.size(); i++){
            double weight = itemArrayList.get(i).getWeight();
            if((currentWeight + weight) <= maxWeight){
                System.out.println("Added weight to bag: " + weight);
                currentWeight += weight;
            }
        }

        System.out.println("Final weight: " + currentWeight);
        System.out.println("Of maximums weight: " + maxWeight);

        // Sortera listan beroende på högst värde (högst till lägst) value/weight, ha reference till Item genom hashmap
        // Gå från vänster till höger och se om du kan lägga in den i knapsack (maxcapasity check)
        // Klar!
    }

    public void neighboursearchApproach(){
    }

    public void neighboursearchTaboApproach(){

    }
}
