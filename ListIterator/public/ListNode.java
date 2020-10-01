public class ListNode<Item> {

    private Item item;
    private ListNode<Item> next;

    public ListNode(Item item){
        this.item = item;
        this.next = null;
    }

    public ListNode(Item item, ListNode<Item> next) {
        this.item = item;
        this.next = next;
    }

    /**
     * Return the item of the node
     *
     * @return The item contained in the node
     */
    public Item getItem() {
        return item;
    }

    /**
     * Get the next node in the list
     *
     * @return the next node in the list
     */
    public ListNode<Item> getNext() {
        return this.next;
    }

    /**
     * Set the followers of this node in the list
     *
     * @param next The node to put after this one
     */
    public void setNext(ListNode<Item> next) {
        this.next = next;
    }
}
