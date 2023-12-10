import java.io.*;
import java.util.*;

public class KnapsackAlgorithm {
    private ArrayList<Item> itemArrayList;
    private ArrayList<Knapsack> bestKnapsacks = new ArrayList<>();

    private ArrayList<Item> bestItemNotInKnapsack = new ArrayList<>();
    private ArrayList<Item> itemsNotInKnapsack = new ArrayList<>();
    public KnapsackAlgorithm() throws IOException {
        String filePath = "src/InputFiles/input5";

        BufferedReader reader = new BufferedReader((new FileReader(filePath)));
        int amountOfBags = Integer.parseInt(reader.readLine());

        for (int i = 0; i < amountOfBags; i++) {
            bestKnapsacks.add(new Knapsack(Double.parseDouble(reader.readLine())));
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

        int totalValueBefore = 0;
        System.out.println("BEFORE: ");
        for (Knapsack bag : bestKnapsacks) {
            totalValueBefore += bag.getTotalValue();
            System.out.println("Weight left: " + bag.getWeightLeft());
            System.out.println("Total value: " + bag.getTotalValue());
            System.out.println("Max weight: " + bag.getMaxWeight());
            System.out.println("Items in bag: ");
            ArrayList<Item> itemsinbag = bag.getItemList();
            for (Item item : itemsinbag) {
                System.out.println(item.toString());
            }
        }
        System.out.println("Items not in knapsack:");
        for (Item item :
                itemsNotInKnapsack) {
            System.out.println(item.toString());
        }
        System.out.println("Total value all bags: " + totalValueBefore);

        bestItemNotInKnapsack = new ArrayList<>(itemsNotInKnapsack);

        localSearch();

        System.out.println();

        int totalValueAfter = 0;
        System.out.println("AFTER: ");
        for (Knapsack bag : bestKnapsacks) {
            totalValueAfter += bag.getTotalValue();
            System.out.println("Weight left: " + bag.getWeightLeft());
            System.out.println("Total value: " + bag.getTotalValue());
            System.out.println("Max weight: " + bag.getMaxWeight());
            System.out.println("Items in bag: ");
            ArrayList<Item> itemsinbag = bag.getItemList();
            for (Item item : itemsinbag) {
                System.out.println(item.toString());
            }
        }
        System.out.println("Items not in knapsack:");
        for (Item item :
                itemsNotInKnapsack) {
            System.out.println(item.toString());
        }
        System.out.println("Total value all bags: " + totalValueAfter);

    }

    public void greedyApproach() {
        itemArrayList.sort(Comparator.comparingDouble(itemVal -> -itemVal.getCompareVal()));

        for (Item item : itemArrayList) {

            double weight = item.getWeight();

            for (Knapsack bag : bestKnapsacks) {
                if (bag.getWeightLeft() >= weight) {
                    bag.addToList(item);
                    itemsNotInKnapsack.remove(item);
                    break;
                }
                else {
                    if (!itemsNotInKnapsack.contains(item)){
                        itemsNotInKnapsack.add(item);
                    }
                }
            }
        }
    }


    public void localSearch() {
        boolean continueLoop = true;


        while (continueLoop) {
            int totalValue = 0;
            continueLoop = false;

            for (Knapsack knapsack : bestKnapsacks) {
                totalValue += knapsack.getTotalValue();
            }

            for (int i = 0; i < bestKnapsacks.size(); i++) {
                ArrayList<Knapsack> originalKnapsacks =  new ArrayList<>();
                for (Knapsack knapsack : bestKnapsacks) {
                    originalKnapsacks.add(new Knapsack(knapsack));
                }

                ArrayList<Item> originalItemsNotInKnapsack = new ArrayList<>(bestItemNotInKnapsack);

                int size = originalKnapsacks.size();
                Knapsack currentKnapsack = originalKnapsacks.get(i);

                for (int j = 0; j < currentKnapsack.getItemList().size(); j++) {
                    ArrayList<Knapsack> modifiedKnapsacks = new ArrayList<>();
                    for (Knapsack knapsack : originalKnapsacks) {
                        modifiedKnapsacks.add(new Knapsack(knapsack));
                    }
                    itemsNotInKnapsack = new ArrayList<>(originalItemsNotInKnapsack);


                    for (int k = i; k < size-1; k++) {
                        modifiedKnapsacks = new ArrayList<>();
                        for (Knapsack knapsack : originalKnapsacks) {
                            modifiedKnapsacks.add(new Knapsack(knapsack));
                        }

                        itemsNotInKnapsack = new ArrayList<>(originalItemsNotInKnapsack);

                        Knapsack modifyKnapsack = modifiedKnapsacks.get(i);

                        Item item = modifyKnapsack.removeFromList(j);

                        Knapsack nextKnapsack = modifiedKnapsacks.get(k+1%size);

                        Item itemToRemove = moveItemLogic(nextKnapsack, item);

                        Item bestItem = null;

                        for (Item itemNotInKnapsack : itemsNotInKnapsack) {
                            if (itemNotInKnapsack != itemToRemove){
                                if (itemNotInKnapsack.getWeight() <= modifyKnapsack.getWeightLeft()){
                                    if (bestItem == null){
                                        bestItem = itemNotInKnapsack;
                                    }else {
                                        if (itemNotInKnapsack.getCompareVal() > bestItem.getCompareVal()){
                                            bestItem = itemNotInKnapsack;
                                        }
                                    }
                                }
                            }
                        }

                        if (bestItem != null){
                            modifyKnapsack.addToList(bestItem);
                            itemsNotInKnapsack.remove(bestItem);
                        }

                        int newTotalValue = 0;

                        for (Knapsack knapsack : modifiedKnapsacks) {
                            newTotalValue += knapsack.getTotalValue();
                        }

                        if (newTotalValue > totalValue){
                            totalValue = newTotalValue;

                            bestKnapsacks = new ArrayList<>();
                            for (Knapsack knapsack : modifiedKnapsacks) {
                                bestKnapsacks.add(new Knapsack(knapsack));
                            }
                            bestItemNotInKnapsack = new ArrayList<>(itemsNotInKnapsack);

                            continueLoop = true;
                        }
                    }
                }
            }
        }
    }

    private Item moveItemLogic(Knapsack nextKnapsack, Item item) {
        if (nextKnapsack.getMaxWeight() >= item.getWeight()){
            if (nextKnapsack.getWeightLeft() >= item.getWeight()){
                nextKnapsack.addToList(item);
                itemsNotInKnapsack.remove(item);
            } else {
                for (int j = 0; j < nextKnapsack.getItemList().size(); j++) {
                    if (nextKnapsack.getItemList().get(j).getWeight() >= item.getWeight() - nextKnapsack.getWeightLeft()){ // Mellanskillnad
                        Item itemToRemove = nextKnapsack.removeFromList(j);
                        itemsNotInKnapsack.add(itemToRemove);
                        nextKnapsack.addToList(item);
                        return itemToRemove;
                    }
                }
            }
        }else {
            itemsNotInKnapsack.add(item);
        }
        return null;
    }

    private int chooseRandomItem(int size) {
        Random random = new Random();
        return random.nextInt(size);
    }
}
