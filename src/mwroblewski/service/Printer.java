package mwroblewski.service;

import mwroblewski.model.Node;
import mwroblewski.model.Tree;

import java.util.ArrayList;
import java.util.List;

public class Printer
{

    public static <T> void print(Tree<T> tree)
    {
        List<List<String>> lines = new ArrayList<>();
        List<Node<T>> level = new ArrayList<>();
        List<Node<T>> next = new ArrayList<>();

        level.add(tree.getRoot());
        int nn = 1;
        int widest = 0;
        while (nn != 0) {
            List<String> line = new ArrayList<>();
            nn = 0;
            for (Node<T> n : level) {
                if (n == null) {
                    line.add(null);
                    next.add(null);
                    next.add(null);
                } else {
                    String aa = n.getValue().isPresent() ? n.getValue().get().toString() + ":" + n.getCounter() : String.valueOf(n.getCounter());
                    line.add(aa);
                    if (aa.length() > widest)
                        widest = aa.length();

                    if(n.getLeftChild().isPresent()){
                        next.add(n.getLeftChild().get());
                        nn++;
                    }
                    else
                        next.add(null);

                    if(n.getRightChild().isPresent()){
                        next.add(n.getRightChild().get());
                        nn++;
                    }
                    else
                        next.add(null);
                }
            }

            if (widest % 2 == 1) widest++;

            lines.add(line);

            List<Node<T>> tmp = level;
            level = next;
            next = tmp;
            next.clear();
        }

        int perpiece = lines.get(lines.size() - 1).size() * (widest + 4);
        for (int i = 0; i < lines.size(); i++) {
            List<String> line = lines.get(i);
            int hpw = (int) Math.floor(perpiece / 2f) - 1;

            if (i > 0) {
                for (int j = 0; j < line.size(); j++) {
                    char c = ' ';
                    if (j % 2 == 1) {
                        if (line.get(j - 1) != null) {
                            c = (line.get(j) != null) ? '┴' : '┘';
                        } else {
                            if (j < line.size() && line.get(j) != null) c = '└';
                        }
                    }
                    System.out.print(c);
                    if (line.get(j) == null) {
                        for (int k = 0; k < perpiece - 1; k++) {
                            System.out.print(" ");
                        }
                    } else {

                        for (int k = 0; k < hpw; k++) {
                            System.out.print(j % 2 == 0 ? " " : "─");
                        }
                        System.out.print(j % 2 == 0 ? "┌" : "┐");
                        for (int k = 0; k < hpw; k++) {
                            System.out.print(j % 2 == 0 ? "─" : " ");
                        }
                    }
                }
                System.out.println();
            }

            for (int j = 0; j < line.size(); j++) {
                String f = line.get(j);
                if (f == null) f = "";
                int gap1 = (int) Math.ceil(perpiece / 2f - f.length() / 2f);
                int gap2 = (int) Math.floor(perpiece / 2f - f.length() / 2f);
                for (int k = 0; k < gap1; k++) {
                    System.out.print(" ");
                }
                System.out.print(f);
                for (int k = 0; k < gap2; k++) {
                    System.out.print(" ");
                }
            }

            System.out.println();
            perpiece /= 2;
        }
    }
}