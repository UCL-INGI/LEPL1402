package templates;


public class FListMerge {

    public static int counter=0;

    public static void resetCounter(){
        counter=0;
    }
    /*
        * This method receives an FList and returns the FList containing the same values but sorted with the smallest value to the highest one.
        *
        */
    public static FList<Integer> mergeSort(FList<Integer> fList){
        counter++;

        @@studentMergeSort@@
    }

    @@studentClass@@
}
