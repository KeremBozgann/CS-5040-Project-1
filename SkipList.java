import java.util.Random;

class SkipList<K extends Comparable<K>, E extends Comparable<E>> {
    private SkipNode<K, E> head;
    private int level;
    private int size;
    static private Random ran = new Random(); // Hold the Random class object

    public SkipList() {
        head = new SkipNode<K, E>(null, null, 0);
        level = -1;
        size = 0;
    }


    // Return the (first) matching matching element if one exists, null
    // otherwise
    public void find(K key) {
        SkipNode<K, E> x = head; // Dummy header node
        for (int i = level; i >= 0; i--) // For each level...
            while ((x.forward[i] != null) && (x.forward[i].key().compareTo(
                key) < 0)) // go forward
                x = x.forward[i]; // Go one last step

        x = x.forward[0]; // Move to actual record, if it exists
        SkipNode<K, E> x_dup = x;

        System.out.println("");

        if (x != null && x.key().compareTo(key) == 0) {
            System.out.println("Rectangles found:");
            System.out.println("( " + x.key() + ", " + x.element() + " )");

            // check if there are rectangles with the same name
            if (x != null)
                while (x_dup.forward[0] != null && (x_dup.forward[0].key()
                    .compareTo(key) == 0)) {
                    x_dup = x_dup.forward[0];
                    System.out.println("( " + x_dup.key() + ", " + x_dup
                        .element() + " )");
                }
        }
        else
            System.out.println("Rectangle not found: " + "(" + key + ")");

//
// if ((x != null) && (x.key().compareTo(key) == 0)) return x.element(); // Got
// it
// else return null; // Its not there
    }


    // Pick a level using a geometric distribution
    int randomLevel() {
        int lev;
        for (lev = 0; Math.abs(ran.nextInt()) % 2 == 0; lev++) { // ran is
                                                                 // random
                                                                 // generator
            ; // Do nothing
        }
        return lev;
    }


    /** Insert a key, element pair into the skip list */
    public void insert(K key, E elem) {

        int newLevel = randomLevel(); // New node's level
        if (newLevel > level) // If new node is deeper
            adjustHead(newLevel); // adjust the header
        // Track end of level
        SkipNode<K, E>[] update = new SkipNode[level + 1];
        SkipNode<K, E> x = head; // Start at header node
        for (int i = level; i >= 0; i--) { // Find insert position
            while ((x.forward[i] != null) && (x.forward[i].key().compareTo(
                key) < 0))
                x = x.forward[i];
            update[i] = x; // Track end at level i
        }
        x = new SkipNode<K, E>(key, elem, newLevel);
        for (int i = 0; i <= newLevel; i++) { // Splice into list
            x.forward[i] = update[i].forward[i]; // Who x points to
            update[i].forward[i] = x; // Who points to x
        }
        System.out.println("Rectangle inserted: " + "( " + x.key() + ", " + x
            .element() + " )");
        size++; // Increment dictionary size

    }


    public void removeByName(K key) {

        SkipNode<K, E>[] update = new SkipNode[level + 1];
        SkipNode<K, E> x = head;

        for (int i = level; i >= 0; i--) { // Find insert position
            while ((x.forward[i] != null) && (x.forward[i].key().compareTo(
                key) < 0))
                x = x.forward[i];
            update[i] = x; // Track end at level i
        }
        x = x.forward[0];
        if ((x != null) && (x.key().compareTo(key) == 0)) {
            int level_x = x.getLevel();
            for (int i = level_x; i >= 0; i--) { // Splice into list
                update[i].forward[i] = x.forward[i];
            }
            System.out.println();
            System.out.println("Rectangle removed: " + "( " + x.key() + ", " + x
                .element() + " )");
            size--;
        }
        else {
            System.out.println("Key is not in the list.");
            System.out.println("Rectangle not removed: " + key);
        }
    }


    public void removeByCoords(E val) {

        if (this.size == 0) {

            System.out.println("Skiplist is empty");
            System.out.println("Rectangle not removed: " + val);
            return;
        }

        SkipNode<K, E> x = head.forward[0];
        System.out.println("");
        for (int i = 0; i < size; i++) { // Find insert position
            if (x.element().compareTo(val) != 0)
                x = x.forward[0];
            else {
                System.out.println("Rectangle removed: " + "( " + x.key() + ", "
                    + x.element() + " )");
                break;
            }
        }
        // The following means that search reached the end of the skiplist
        // without
        // finding searched coords:
        if (x == null) {
            System.out.println("Reached the end of the list");
            System.out.println("Rectangle not removed: " + val);
            return;
        }

        K key = x.key();
        removeByName(key);
    }


    public void dump() {
        SkipNode<K, E> x = null;
        x = this.head;
        System.out.println("SkipList dump:");

        for (int i = 0; i < this.size + 1; i++) {

            System.out.println("Node has depth " + x.getLevel() + ", "
                + "Value(" + x.key() + ", " + x.element() + ")");
            x = x.forward[0];
        }
        System.out.println("SkipList size is: " + this.size);

    }


    private void adjustHead(int newLevel) {
        SkipNode<K, E> temp = head;
        head = new SkipNode<K, E>(null, null, newLevel);
        for (int i = 0; i <= level; i++)
            head.forward[i] = temp.forward[i];
        level = newLevel;
    }


    // just some public methods to return private variables
    public int getLevel() {
        return this.level;
    }


    public int getSize() {
        return this.size;
    }


    public SkipNode<K, E> getHead() {
        return this.head;
    }
}
