package module1;

/**
 * In this task, we ask you to implement several common methods inspired from java's String API.
 * Java String API : https://docs.oracle.com/javase/8/docs/api/java/lang/String.html#subSequence-int-int-
 *
 * The objective of this task is to get you used to String manipulation in java.
 * In order to succeed this task, you will have to implement four different methods from a class we called StringUtils :
 *
 *     - split(String str, char marker) : separate a String into fragments each time a specific character ``marker`` is encountered.
 *          Note that, for simplicity, marker is a char whereas in java's String it is a String.
 *          For example, calling your method with "Here.I.Go" with marker '.' should return an array of size three with "Here", "I" and "Go" in its cells.
 *
 *     - indexOf(String str, String sub) : returns the index of the first occurrence of the String sub in the String str.
 *          For example indexOf("Hello", "ell") should return 1, indexOf("Hello", "o") should return 4.
 *          If there is no occurrence of sub in str, return -1.
 *
 *     - toLowerCase(String str) : returns a String with the same characters as 'str' except that all upper case characters have been replaced by their lower case equivalent.
 *
 *     - palindrome(String str) : Returns true if the string 'str' is a palindrome : a string that reads the same from left to right AND from right to left (for example, "kayak").
 *          Note that this method does not exist in java's String_ API because it's not very useful
 *          But it is still a good exercise to train yourself to manipulate String objects in java.
 */
public class StringUtils {


    /**
     * Split a string according to a delimiter
     *
     * @param str The string to split
     * @param delimiter The delimiter
     * @return An array containing the substring which fall
     *          between two consecutive occurence of the delimiter.
     *          If there is no occurence of the delimiter, it should
     *          return an array of size 1 with the string at element 0
     */
    public static String [] split(String str, char delimiter){
        return null;
    }


    /**
     * Find the first occurence of a substring in a string
     *
     * @param str The string to look in
     * @param sub The string to look for
     * @return The index of the start of the first appearance of
     *          the substring in str or -1 if sub does not appear
     *          in str
     */
    public static int indexOf(String str, String sub){
        return 0;
    }


    /**
     * Convert a string to lowercase
     *
     * @param str The string to convert
     * @return A new string, same as str but with every
     *          character put to lower case.
     */
    public static String toLowerCase(String str){
        return null;
    }


    /**
     * Check if a string is a palyndrome
     *
     * A palyndrome is a sequence of character that is the
     * same when read from left to right and from right to
     * left.
     *
     * @param str The string to check
     * @return true if str is a palyndrome, false otherwise
     */
    public static boolean palindrome(String str){
        return false;
    }


}
