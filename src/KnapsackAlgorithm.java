import java.io.*;

public class KnapsackAlgorithm {

    public KnapsackAlgorithm() throws IOException {
        String filePath = "C:/Users/elisa/OneDrive/Dokument/GitHub/lab2_knapsack/src/InputFiles/input1";
        //String filePath = "C:/Users/elisa/OneDrive/Dokument/GitHub/lab2_knapsack/src/InputFiles/input2";
        //String filePath = "C:/Users/elisa/OneDrive/Dokument/GitHub/lab2_knapsack/src/InputFiles/input3";

        BufferedReader reader = new BufferedReader((new FileReader(filePath)));
        int maxWeight = Integer.parseInt(reader.readLine());
        int n = Integer.parseInt(reader.readLine());

        System.out.println("Max weight of knapsack: " + maxWeight);
        for(int i = 0; i < n ; i++){
            String[] line = reader.readLine().split("\\s+");
            int value = Integer.parseInt(line[0]);
            int weight = Integer.parseInt(line[1]);

            System.out.println("Item " + (i + 1) + ": Value = " + value + ", Weight = " + weight);
        }

    }

    public void greedyApproach(){

    }

    public void neighboursearchApproach(){

    }

    public void neighboursearchTaboApproach(){

    }
}
