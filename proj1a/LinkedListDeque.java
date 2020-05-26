import java.util.List;

public class LinkedListDeque <T>{
    private class ListNode {
        public T item;
        public ListNode prev;
        public ListNode next;

        //ListNode Constructor

        public ListNode (T item){
            this.item = item;
            this.prev = null;
            this.next = null;
        }
    }

    private ListNode head;
    private ListNode tail;
    private int size;
    //LinkedListDeque constructor
    public LinkedListDeque(){
        head = null;
        tail = null;
        size = 0;

    }

    /** Adds an item of type T to the front of the deque */
    public void addFirst(T item){
        if (head == null) {
            head = new ListNode(item);
            tail = head;
        }else{
            ListNode temp = head; // temp to store the old head
            head = new ListNode(item);// create the new head
            head.next = temp;
            temp.prev = head;
        }
        size ++;
    }

    /** Adds an item of type T to the back of the deque*/
    public void addLast(T item){
        if (tail == null){
            tail = new ListNode(item);
            head = tail;
        } else{
            tail.next = new ListNode(item);
            tail.next.prev = tail;
            tail = tail.next;
        }
        size++;
    }
    /**Returns true if deque is empty, false otherwise*/
    public boolean isEmpty(){
        return size == 0;
    }
    /**Returns the number of items in the deque*/
    public int size(){
        return size;
    }
    /**Prints the items in the deque from first to last, separated by a space*/
    public void printDeque(){
        ListNode curr = head;
        while(curr != null){
            System.out.print(curr.item + " ");
            curr = curr.next;
        }
    }
    /**Removes and returns the item at the front of the deque. If no such item exists, returns null*/
    public T removeFirst(){
        if (head == null){ // edge case head == null
            return null;
        }
        ListNode front = head;
        head = head.next;
        front.next = null;
        if (head != null){
            head.prev =null;
        }
        size--;
        return front.item;
    }
    /**Removes and returns the item at the back of the deque. If no such item exists, returns null*/
    public T removeLast(){
        if (tail == null){ // edge case tail == null
            return null;
        }
        ListNode end = tail;
        tail = tail.prev;
        end.prev = null;
        if (tail != null){
            tail.next = null;
        }
        size--;
        return end.item;
    }
    /**Gets the item at the given index, where 0 is the front, 1 is the next item, and so forth.
     * If no such item exists, returns null. Must not alter the deque*/
    public T get(int index){
        if (index > size -1 || index < 0){
            return null;
        }
        ListNode curr = this.head;
        while (index > 0){
            curr = curr.next;
            index--;
        }

        return curr.item;

    }
    /**Same as get, but uses recursion*/
    public T getRecursive(int index){
        if (index > size -1 || index < 0){
            return null;
        }
        ListNode curr= head;
        return getHelper(curr, index);
    }
    public T getHelper(ListNode curr, int index){
        if (index == 0){
            return curr.item;
        }else{
            return getHelper(curr.next, index-1);
        }
    }
}