package system;

public class Node<E>{ // for a doubly linked list
    private E element; // current element
    private Node<E> next; // reference to the next node    

    public Node(){
       this(null, null); 
    }

    public Node(E e, Node<E> n){
        element = e;
        next = n;
    }

    /**
     * @return the element
     */
    public E getElement() {
        return element;
    }

    /**
     * @return the next
     */
    public Node<E> getNext() {
        return next;
    }

    /**
     * @param element the element to set
     */
    public void setElement(E element) {
        this.element = element;
    }

    /**
     * @param next the next to set
     */
    public void setNext(Node<E> next) {
        this.next = next;
    }

    @Override
    public String toString() {
        return element +" "+ next;
    }

}