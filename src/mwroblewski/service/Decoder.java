package mwroblewski.service;

import mwroblewski.model.Node;
import mwroblewski.model.Tree;

import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.util.LinkedList;

public class Decoder<T> {

    public LinkedList<T> decode(Tree<T> tree, String codes) {
        final StringCharacterIterator codesIterator = new StringCharacterIterator(codes);
        final LinkedList<T> items = new LinkedList<>();
        while (codesIterator.current() != CharacterIterator.DONE) {
            items.add(this.buildItem(tree, codesIterator));
        }
        return items;
    }

    private T buildItem(Tree<T> tree, StringCharacterIterator codesIterator) {
        Node<T> node = tree.getRoot();
        while (!node.isLeaf() && codesIterator.current() != CharacterIterator.DONE){
            node = codesIterator.current() == '0' ? node.getLeftChild().get() : node.getRightChild().get();
            codesIterator.next();
        }
        return node.getValue().get();
    }

}
