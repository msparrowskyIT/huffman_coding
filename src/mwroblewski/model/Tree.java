package mwroblewski.model;

public class Tree<T> {

    private final Node<T> root;

    public Tree(Node<T> root) {
        this.root = root;
    }

    public Node<T> getRoot() {
        return this.root;
    }

}
