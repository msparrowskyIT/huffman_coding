package mwroblewski.model;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class Node<T> implements Comparable<Node<T>> {

    private double counter;
    private final Optional<T> value;
    private Optional<Node<T>> parent = Optional.empty();
    private Optional<Node<T>> leftChild = Optional.empty();
    private Optional<Node<T>> rightChild = Optional.empty();

    public Node(T value, double counter){
        this.value = Optional.ofNullable(value);
        this.counter = counter;
    }

    public Node(T value) {
        this(value, 1.0);
    }

    public void incrementCounter() {
        this.counter++;
    }

    public void decrementCounter() {
        this.counter--;
        if(counter < 0.0)
            throw new RuntimeException("Counter for node with value " + this.value + " has negative counter !!!");
    }

    public double getCounter() {
        return this.counter;
    }

    public Optional<T> getValue() {
        return this.value;
    }

    public Optional<Node<T>> getParent() {
        return this.parent;
    }

    public Optional<Node<T>> getLeftChild() {
        return this.leftChild;
    }

    public Optional<Node<T>> getRightChild() {
        return this.rightChild;
    }

    public Collection<Node<T>> getChildren() {
        return Stream.of(this.leftChild,this.rightChild)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(toList());
    }

    public Node<T> withCounter(double counter) {
        this.counter = counter;
        return this;
    }

    public Node<T> withParent(Node<T> parent) {
        this.parent = Optional.ofNullable(parent);
        return this;
    }

    public Node<T> withLeftChild(Node<T> leftChild) {
        this.leftChild = Optional.ofNullable(leftChild);
        return this;
    }

    public Node<T> withRightChild(Node<T> rightChild) {
        this.rightChild = Optional.ofNullable(rightChild);
        return this;
    }

    public boolean isRoot() {
        return !this.parent.isPresent();
    }

    public boolean isLeaf() {
        return !(this.leftChild.isPresent() || this.rightChild.isPresent());
    }

    @Override
    public int compareTo(Node<T> o) {
        return (int) (this.counter - o.getCounter());
    }

}
