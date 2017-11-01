package system;

public class DoublyLinkedList<E> {
    private static class Node<E> {
        private E element;
        private Node<E> prev;
        private Node<E> next;

        public Node(E e, Node<E> p, Node<E> n) {
            element = e;
            prev = p;
            next = n;
        }
        public E getElement() {
            return element;
        }
        public Node<E> getPrev() {
            return prev;
        }
        public Node<E> getNext() {
            return next;
        }
        public void setNext(Node<E> n) {
            next = n;
        }
        public void setPrev(Node<E> p) {
            prev = p;
        }
    }

    private Node<E> header;
    private Node<E> trailer;
    private int size = 0;

    public DoublyLinkedList() {
        header = new Node<E> (null, null, null);
        trailer = new Node<E> (null, header, null);
        header.setNext(trailer);
    }

    public int size() {
        return size;
    }

    public int getSize() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public E first() {
        return (isEmpty()) ? null : header.getNext().getElement();
    }

    public E last() {
        return (isEmpty()) ? null : trailer.getPrev().getElement();
    }

    public void addFirst(E e) {
        addBetween(e, header, header.getNext());
    }

    public void addLast(E e) {
        addBetween(e, trailer.getPrev(), trailer);
    }

    public E removeFirst() {
        return (isEmpty()) ? null : remove(header.getNext());
    }

    public E removeLast() {
        return (isEmpty()) ? null : remove(trailer.getPrev());
    }

    public void addBetween (E e, Node<E> predecessor, Node<E> successor) {
        Node<E> newNode = new Node<E>(e, predecessor, successor);
        predecessor.setNext(newNode);
        successor.setPrev(newNode);
    }

    public E remove(Node<E> node) {
        Node<E> predecessor = node.getPrev();
        Node<E> successor = node.getNext();
        predecessor.setNext(successor);
        successor.setPrev(predecessor);
        return node.getElement();
    }
}
