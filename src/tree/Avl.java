package tree;


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Avl {

    static class Node {
        String name;
        int height;
        Node leftChild, rightChild;

        Node (String name) {
            this.name = name;
        }
    }

    Node root;

     void addNode(String name) {
        root = addNode(root, name);
    }

     Node getRoot() {
        return root;
    }

    int height(Node n) {
        return n == null ? -1 : n.height;
    }

    void heightUpdate(Node n) {
        n.height = 1 + Math.max(height(n.leftChild), height(n.rightChild));
    }

    int getBalance(Node n) {
        return (n == null) ? 0 : height(n.rightChild) - height(n.leftChild);
    }

    Node rightRotation(Node cy) {
        Node x = cy.leftChild;
        Node z = x.rightChild;
        x.rightChild = cy;
        cy.leftChild = z;
        heightUpdate(cy);
        heightUpdate(x);
        return x;
    }

    Node LeftRotation(Node cy) {
        Node x = cy.rightChild;
        Node z = x.leftChild;
        x.leftChild = cy;
        cy.rightChild = z;
        heightUpdate(cy);
        heightUpdate(x);
        return x;
    }

    Node reBalance(Node z) {
        heightUpdate(z);
        int balance = getBalance(z);
        if (balance > 1) {
            if (height(z.rightChild.rightChild) > height(z.rightChild.leftChild)) {
                z = LeftRotation(z);
            } else {
                z.rightChild = rightRotation(z.rightChild);
                z = LeftRotation(z);
            }
        } else if (balance < -1) {
            if (height(z.leftChild.leftChild) > height(z.leftChild.rightChild))
                z = rightRotation(z);
            else {
                z.leftChild = LeftRotation(z.leftChild);
                z = rightRotation(z);
            }
        }
        return z;
    }
    Node addNode( Node node, String name) {

        if (node == null) {
            return new Node(name);
        }

        int compare = (node.name).compareTo(name);

        if (compare < 0) {
            node.leftChild = addNode(node.leftChild, name);
        } else if (compare > 0) {
            node.rightChild = addNode(node.rightChild, name);
        } else {
            throw new RuntimeException("Key already in use");
        }
        return reBalance(node);
    }

    void inOrderTraversal(Node node) {
        if (node != null){
            inOrderTraversal(node.leftChild);
            System.out.print(node.name + " ");
            inOrderTraversal(node.rightChild);
        }
    }

    void preOrderTraversal(Node node) {
         if (node != null) {
             System.out.print(node.name + " ");
             preOrderTraversal(node.leftChild);
             preOrderTraversal(node.rightChild);
         }
    }

    void postOrderTraversal(Node node) {
        if (node != null) {
            postOrderTraversal(node.leftChild);
            postOrderTraversal(node.rightChild);
            System.out.print(node.name + " ");
        }
    }

    public static void main(String[] args) throws FileNotFoundException {
        List<String> betterStates = new ArrayList<>();
        Scanner sc = new Scanner(new FileReader("betterStates.txt"));
        while (sc.hasNextLine()) {
            betterStates.add(sc.nextLine());
        }

        sc.close();
        Avl tree = new Avl();

        for(String state: betterStates) {
            tree.addNode(state);
        }

        System.out.println("\nIN-ORDER TRAVERSAL:\n");
        tree.inOrderTraversal(tree.root);
        System.out.println();

        System.out.println("\nPRE-ORDER TRAVERSAL:\n");
        tree.preOrderTraversal(tree.root);
        System.out.println();

        System.out.println("\nPOST-ORDER TRAVERSAL:\n");
        tree.postOrderTraversal(tree.root);
        System.out.println();
    }
}
