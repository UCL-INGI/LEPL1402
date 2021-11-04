package module2;

/**
 * A geologist is trying to find the deepest valley and the biggest mountain between 2 locations and he thinks you can help him!
 * He already preprocess the field and give you as input an array of binary values (1 and -1)
 * representing the slope (when looking at the field from left to right).
 * An example of array with the associated relief is shown next
 *
 *     .. image:: /course/LEPL1402/valley/schema.png
 *        :scale: 35%
 *        :align: center
 *        :height: 912px
 *        :width: 1771px
 *
 * He would like to know the depth of the deepest valley as well as the height of the highest mountain.
 * A valley is, as one might expect, a succession of n negative slopes followed by n positive slopes. In the same manner,
 * a mountain is a succession of n positive slopes followed by n negative slopes.
 * The height (resp. depth) of a mountain (resp. valley) is the number of consecutive positive (resp. negative) slop n.
 *
 * In the example above, the highest mountain has a height of 3 and the deepest valley a depth of 5.
 * Thus your method should return the array [5, 3].
 *
 * To spice up a bit the thing, your code should have a :math:`\Theta(m)` temporal complexity where m is the length of the array.
 */
class Valley {

    /**
     * Compute the deepest valley and highest mountain
     *
     * @param slopes A non-empty array of slope
     * @return An array of two element. The first is the
     *         depth of the deepest valley and the second
     *         the height of the highest mountain
     */
    public static int[] valley (int[] slopes){
        return null;
    }
}
