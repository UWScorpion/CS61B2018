@SuppressWarnings("unchecked")
public class ArrayDeque <T>{

    private T[] arrayDQ;
    /** head will point to the position of the front item
     *  tail will point to the position of the end item
     *  size is the total item in the Deque
     *  capacity is the length of the array
     *  */
    int head, tail, size, capacity;

    // A constructor to build an ArrayDeque
    public ArrayDeque(){
        capacity = 4;
        arrayDQ = (T[]) new Object[capacity];
        head = 0;
        tail = 0;
        size = 0;
    }
    public void reSize1 (){ // double the capacity
        int curr = (head - 1 + capacity) % capacity;
        int newcapacity = capacity * 2;
        T[] newDQ = (T[]) new Object[newcapacity];
        head = newcapacity-1;
        int newcurr = (head - 1 + newcapacity) % newcapacity;
        while(curr != tail){
            newDQ[newcurr] = arrayDQ[curr];
            curr = (curr -1 + capacity) % capacity;
            newcurr = newcurr -1;
        }
        newDQ[newcurr] = arrayDQ[curr];
        tail = newcurr;
        arrayDQ = newDQ;
        capacity = newcapacity;
    }
    public void reSize2 (){ // half the capacity
        capacity = capacity / 2;
    }
    /** Adds an item of type T to the front of the deque */
    public void addFirst(T item){
        if (size + 1 > capacity){
            reSize1();
        }
        arrayDQ[head] = item;
        head = (head + 1) % capacity;
        size ++;
    }

    /** Adds an item of type T to the back of the deque*/
    public void addLast(T item){
        if (size + 1 > capacity){
            reSize1();
        }
        tail = (tail - 1 + capacity) % capacity;
        arrayDQ[tail] = item;
        size ++;
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
        int curr = (head - 1 + capacity) % capacity;
        while(curr != tail){
            System.out.print(arrayDQ[curr] + " ");
            curr = (curr -1 + capacity) % capacity;
        }
        System.out.print(arrayDQ[curr] + " ");
    }
    /**Removes and returns the item at the front of the deque. If no such item exists, returns null*/
    public T removeFirst(){
        head = (head - 1 + capacity) % capacity;
        size--;
        return arrayDQ[head];
    }
    /**Removes and returns the item at the back of the deque. If no such item exists, returns null*/
    public T removeLast(){
        int end = tail;
        tail = (tail + 1) % capacity;
        size--;
        return arrayDQ[end];
    }
    /**Gets the item at the given index, where 0 is the front, 1 is the next item, and so forth.
     * If no such item exists, returns null. Must not alter the deque*/
    public T get(int index){
        if (index > size -1 || index < 0){
            return null;
        }
        int curr = (head - 1 + capacity) % capacity;
        while(index > 0){
            curr = (curr -1 + capacity) % capacity;
            index--;
        }
        return arrayDQ[curr];

    }
    /**Same as get, but uses recursion*/
    public T getRecursive(int index){
        return null;
    }


}