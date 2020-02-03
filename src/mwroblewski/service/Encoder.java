package mwroblewski.service;

import mwroblewski.model.Node;
import mwroblewski.model.Tree;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static java.util.Collections.sort;

public class Encoder<T> {

    private Tree<T> tree;
    private LinkedList<Node<T>> leavesList;
    private Map<T,Integer> frequencyMap;
    private Supplier<LinkedList<T>> itemsSupplier;

    public Encoder(Supplier<LinkedList<T>> itemsSupplier) {
        this.itemsSupplier = itemsSupplier;
        this.frequencyMap = this.buildFrequencyMap(this.itemsSupplier.get());
        this.leavesList = buildLeavesList(this.frequencyMap);
        this.tree = this.buildTree(new LinkedList<>(this.leavesList));
    }

    public String encode() {
        StringBuilder codesBuilder = new StringBuilder();
        this.itemsSupplier.get().forEach(i ->
            codesBuilder.append(this.buildCode(i)));
        return codesBuilder.toString();
    }

    private Map<T,Integer> buildFrequencyMap(LinkedList<T> items) {
        final Map<T,Integer> frequencyMap = new HashMap<>();
        items.forEach(i -> {
            Integer counter = frequencyMap.get(i);
            if(counter == null)
                frequencyMap.put(i, 1);
            else
                frequencyMap.put(i, ++counter);
        });
        return frequencyMap;
    }

    private LinkedList<Node<T>> buildLeavesList(Map<T,Integer> frequencyMap) {
        return frequencyMap.entrySet()
                .stream()
                .map(e -> new Node<>(e.getKey(),e.getValue()))
                .sorted()
                .collect(Collectors.toCollection(LinkedList::new));
    }

    private Tree<T> buildTree(LinkedList<Node<T>> nodesList) {
        while(nodesList.size() >= 2) {
            final Node<T> node1 = nodesList.poll();
            final Node<T> node2 = nodesList.poll();
            final Node<T> node3 = new Node<T>(null, node1.getCounter() + node2.getCounter())
                    .withLeftChild(node1)
                    .withRightChild(node2);
            node1.withParent(node3);
            node2.withParent(node3);
            nodesList.add(node3);
            sort(nodesList);
        }
        return new Tree<>(nodesList.pop());
    }

    private Node<T> findLeaf(T item) {
        return this.leavesList
                .stream()
                .filter(l -> item.equals(l.getValue().get()))
                .findFirst()
                .orElse(null);
    }

    private String buildCode(T item) {
        Node<T> node = this.findLeaf(item);
        final StringBuilder codeBuilder = new StringBuilder();
        while (!node.isRoot()) {
            final Node<T> parent = node.getParent().get();
            codeBuilder.append(parent.getLeftChild().isPresent() && parent.getLeftChild().get() == node ? '0' : '1');
            node = parent;
        }
        return codeBuilder.reverse().toString();
    }

    public Tree<T> getTree() {
        return tree;
    }

    public LinkedList<Node<T>> getLeavesList() {
        return leavesList;
    }

    public Map<T, Integer> getFrequencyMap() {
        return frequencyMap;
    }

    public Supplier<LinkedList<T>> getItemsSupplier() {
        return itemsSupplier;
    }

}
