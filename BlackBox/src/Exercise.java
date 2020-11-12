package src;

import java.util.stream.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Exercise {

    public static int count = 0;
    private static BinarySearchFlavour currentFlavour;

    // Add manually instances of your flavours here !
    private static BinarySearchFlavour[] allFlavours;

    public static int nBugs; // the number of flavours that are incorrect

    public static BinarySearchFlavour getInstance(String className) {
        try {
            return (BinarySearchFlavour) Class.forName(className).getDeclaredConstructors()[0].newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Exercise() {
        try {
            Stream<String> stream = Files.lines(Paths.get("src/BinarySearchs.java"));
            String [] fls = stream.filter(line -> line.startsWith("class"))
                .map(line -> line.split(" ")[1]).toArray(String []::new);
            stream.close();
            allFlavours = new BinarySearchFlavour[fls.length];
            nBugs = 0;
            for (int i = 0; i < allFlavours.length; i++) {
                allFlavours[i] = getInstance("src." + fls[i]);
                if (!allFlavours[i].correctness())
                    nBugs += 1;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int nbImplem() {
        return allFlavours.length;
    }

    public static int binarySearch(int [] arr, int low, int high, int elem) {
        currentFlavour = allFlavours[count];
        int retVal = currentFlavour.binarySearch(arr, low, high, elem);
        return retVal;
    }


    public boolean correctness() {
        return allFlavours[count].correctness();
    }

    public String feedbackMessage() {
        return allFlavours[count].feedbackMessage();
    }

}
