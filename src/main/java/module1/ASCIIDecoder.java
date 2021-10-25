import java.util.ArrayList;

/**
 * For this exercise we want you to make a method able to decode a set of ASCII (decimal) codes into a String.
 * Please, read carefully the signature and the comments above the method's signature below :
 *
 * We also highly suggest you to have a look at the StringBuilder and ArrayList APIs.
 *      - StringBuilder : https://docs.oracle.com/javase/8/docs/api/java/lang/StringBuilder.html
 *      - ArrayList : https://docs.oracle.com/javase/8/docs/api/java/util/ArrayList.html
 * We strongly encourage you to use them for this exercise.
 */
public class ASCIIDecoder {

    /**
     * Forbidden characters are passed as an array of int.
     * Each element of this array correspond to the decimal ASCII code
     * of a forbidden character OR null if there's no forbidden character
     * If you encounter one of these forbidden character
     * you must ignore it when you translate your sentence.
     *
     * the 2D array "sentences" contain a set of decimal ASCII code we want you
     * to translate. Each sub-element of this array is a different sentence.
     * Ex : if we pass this array : [ ["42", "72", "88"], ["98", "99", "111", "47", "55"]]
     * to your decode method, you should return : [ "*HX", "bco/7" ]
     *
     * You should NEVER return null or an array containing null
     */
    public static String [] decode(int[] forbidden, String[][] sentences){
        
    }

}