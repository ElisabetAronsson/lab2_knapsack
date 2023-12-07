import java.io.*;
import java.util.*;

public class KnapsackAlgorithm {
    private ArrayList<Item> itemArrayList;
    private ArrayList<Knapsack> bestKnapsacks = new ArrayList<>();
    private ArrayList<Item> itemsNotInKnapsack = new ArrayList<>();
    public KnapsackAlgorithm() throws IOException {
        String filePath = "src/InputFiles/input5Multiple";
        //String filePath = "C:/Users/elisa/OneDrive/Dokument/GitHub/lab2_knapsack/src/InputFiles/input2";
        //String filePath = "C:/Users/elisa/OneDrive/Dokument/GitHub/lab2_knapsack/src/InputFiles/input3";

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


        //neighboursearchApproach();
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

        // Sortera listan beroende på högst värde (högst till lägst) value/weight, ha reference till Item genom hashmap
        // Gå från vänster till höger och se om du kan lägga in den i knapsack (maxcapasity check)
        // Klar!
    }

    public void neighboursearchApproach(){
        boolean continueLoop = true;

        while (continueLoop){
            int totalValue = 0;
            ArrayList<Knapsack> modifyKnapsacks = new ArrayList<>(bestKnapsacks);
            continueLoop = false;

            for (Knapsack knapsack : modifyKnapsacks) {
                totalValue += knapsack.getTotalValue();
            }

            for (int i = 0; i < modifyKnapsacks.size(); i++) {
                int size = modifyKnapsacks.size();

                Knapsack currentKnapsack = modifyKnapsacks.get(i);

                //innerLoop:
                for (int m = 0; m < currentKnapsack.getItemList().size(); m++) {
                    Item item = currentKnapsack.removeFromList(m);

                    Knapsack nextKnapsack = modifyKnapsacks.get((i+1)%size);

                    Item itemToRemove = null;

                    if (nextKnapsack.getMaxWeight() >= item.getWeight()){
                        if (nextKnapsack.getWeightLeft() >= item.getWeight()){
                            nextKnapsack.addToList(item);
                            itemsNotInKnapsack.remove(item); // ?
                            //continue innerLoop;
                        }
                        else if (nextKnapsack.getWeightLeft() < item.getWeight()){
                            for (int j = 0; j < nextKnapsack.getItemList().size(); j++) {
                                if (nextKnapsack.getItemList().get(j).getWeight() >= item.getWeight() - nextKnapsack.getWeightLeft()){
                                    itemToRemove = nextKnapsack.removeFromList(j);
                                    nextKnapsack.addToList(item);
                                    itemsNotInKnapsack.remove(item);
                                    break;
                                }
                            }
                        }
                    }else {
                        itemToRemove = item;
                    }

                    if (itemToRemove != null){
                        itemsNotInKnapsack.add(itemToRemove);
                    }

                    Item bestItem = null;

                    for (Item itemNotInKnapsack : itemsNotInKnapsack) {
                        if (itemNotInKnapsack != itemToRemove){
                            if (itemNotInKnapsack.getWeight() <= currentKnapsack.getWeightLeft()){
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
                        currentKnapsack.addToList(bestItem);
                        itemsNotInKnapsack.remove(bestItem);
                    }

                    int newTotalValue = 0;

                    for (Knapsack knapsack : modifyKnapsacks) {
                        newTotalValue += knapsack.getTotalValue();
                    }

                    if (newTotalValue > totalValue){
                        totalValue = newTotalValue;
                        bestKnapsacks = new ArrayList<>(modifyKnapsacks);
                        continueLoop = true;
                    }
                }

            }
        }

        /*
        boolean continueLoop = true;
        while(continueLoop)
            totalValue = value av alla bags
            continueLoop = false;
            for each bag:
                remove one item (i') from bag
                if (bag+1.maxWeight >= i'.weight):
                    if(bag+1.weightLeft < i'.weight):
                        if (item i''.weight >= item i'.weight - bag+1.weightLeft):
                            bag+1.remove(item i'')
                            bag+1.add(item i')
                for each bag:
                    if weightLeft >= någon vikt av items utanför väskorna (som inte är i'')
                        lägg till (item i''') i den väskan
                if newTotalValue > totalValue
                    set this as the new bags
                    set totalValue = newTotalValue
                    continueLoop = true
         */
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
                ArrayList<Knapsack> originalKnapsacks =  new ArrayList<>(bestKnapsacks);
                //tempKnapsacks = new ArrayList<>(originalKnapsacks);
                int size = originalKnapsacks.size();
                Knapsack currentKnapsack = originalKnapsacks.get(i);

                for (int j = 0; j < currentKnapsack.getItemList().size(); j++) {
                    ArrayList<Knapsack> modifiedKnapsacks = new ArrayList<>(originalKnapsacks);
                    Knapsack modifyKnapsack = new Knapsack(currentKnapsack);


                    Item item = modifyKnapsack.removeFromList(j);
                    Knapsack nextKnapsack = modifiedKnapsacks.get((i +1)%size);

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

                        bestKnapsacks = new ArrayList<>(modifiedKnapsacks);
                        continueLoop = true;
                    }
                }
            }
        }
    }

    private Item moveItemLogic(Knapsack nextKnapsack, Item item) {
        Item itemToRemove = null;

        if (nextKnapsack.getMaxWeight() >= item.getWeight()){
            if (nextKnapsack.getWeightLeft() >= item.getWeight()){
                nextKnapsack.addToList(item);
                itemsNotInKnapsack.remove(item); // ?
                //continue innerLoop;
            } else {
                for (int j = 0; j < nextKnapsack.getItemList().size(); j++) {
                    if (nextKnapsack.getItemList().get(j).getWeight() >= item.getWeight() - nextKnapsack.getWeightLeft()){ // Mellanskillnad
                        itemToRemove = nextKnapsack.removeFromList(j);
                        itemsNotInKnapsack.add(itemToRemove);
                        nextKnapsack.addToList(item);
                        return itemToRemove;
                    }
                }
            }
        }else {
            itemsNotInKnapsack.add(item);
        }
        return itemToRemove;
    }

    private int chooseRandomItem(int size) {
        Random random = new Random();
        return random.nextInt(size);
    }


}
