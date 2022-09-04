
import java.util.Random;

/**
 * Reference: Significant amount of this taken from OpenDSA code
 * from the section 15.1. Skip Lists
 * 
 * @param <K>
 *            key
 * @param <E>
 *            value
 * @author Kerem Bozgan kerembozgan
 * @version 2022-09-03
 */
class SkipList<K extends Comparable<K>, E extends Comparable<E>> {
    private SkipNode<K, E> head;
    private int level;
    private int size;
    static private Random ran = new Random(); // Hold the Random class object

    /**
     * set up the head node
     */
    public SkipList() {
        head = new SkipNode<K, E>(null, null, 1);
        level = 1;
        size = 0;
    }


    /**
     * 
     * Return the (first) matching matching element if one exists, null
     * otherwise
     * 
     * @param key
     *            find the rectangles with this key
     * @return info string to be used in test validation
     */
    public String find(K key) {

        SkipNode<K, E> x = head; // Dummy header node
        String message;
        for (int i = level; i >= 0; i--) // For each level...
            while ((x.getForward()[i] != null) && (x.getForward()[i].key()
                .compareTo(key) < 0)) // go forward
                x = x.getForward()[i]; // Go one last step

        x = x.getForward()[0]; // Move to actual record, if it exists
        SkipNode<K, E> xDup = x;

        if (x != null) {
            System.out.println("Rectangles found:");
            System.out.println("(" + x.key() + ", " + x.element() + ")");
            message = "Found";
            // check if there are rectangles with the same name, if there are,
            // print them too
            while (xDup.getForward()[0] != null && (xDup.getForward()[0].key()
                .compareTo(key) == 0)) {
                xDup = xDup.getForward()[0];
                System.out.println("(" + xDup.key() + ", " + xDup.element()
                    + ")");
                message = "MultipleFound";
            }
            return message;
        }
        else
            System.out.println("Rectangle not found: " + "(" + key + ")");
        return "NotFound";
    }


    /**
     * 
     * Pick a level using a geometric distribution
     * 
     * @return lev level of the new rectangle
     */

    int randomLevel() {
        int lev = 0;
        int a = 0;
        while (true) {
            a = Math.abs(ran.nextInt()) % 2;
            if (a == 0)
                lev++;
            else if (a == 1)
                break;
            else
                return -1;
        }
        return lev;
    }


    /**
     * Insert a key, element pair into the skip list
     * 
     * @param key
     *            rectangle name
     * @param elem
     *            rectangle values
     * @return info about execution
     */
    public String insert(K key, E elem) {

        int newLevel = randomLevel(); // New node's level
        if (newLevel > level) // If new node is deeper
            adjustHead(newLevel); // adjust the header
        // Track end of level

        SkipNode<K, E> x = head; // Start at header node
        @SuppressWarnings("unchecked")
        SkipNode<K, E>[] update = new SkipNode[level + 1];
        for (int i = level; i >= 0; i--) { // Find insert position
            while ((x.getForward()[i] != null) && (x.getForward()[i].key()
                .compareTo(key) < 0))
                x = x.getForward()[i];
            update[i] = x; // Track end at level i
        }

        x = new SkipNode<K, E>(key, elem, newLevel);
        for (int i = 0; i <= newLevel; i++) { // Splice into list
            x.getForward()[i] = update[i].getForward()[i]; // Who x points to
            update[i].getForward()[i] = x; // Who points to x
        }
        System.out.println("Rectangle inserted: " + "(" + x.key() + ", " + x
            .element() + ")");
        size++; // Increment dictionary size
        return "Inserted";

    }


    /**
     * 
     * remove rectangle with given key if it exists
     * 
     * @param key
     *            rectangle name to be removed
     * @return info string
     */

    public String removeByName(K key) {
        @SuppressWarnings("unchecked")
        SkipNode<K, E>[] update = new SkipNode[level + 1];
        SkipNode<K, E> x = head;

        // search for key and prepare update:
        for (int i = level; i >= 0; i--) { // Find insert position
            while ((x.getForward()[i] != null) && (x.getForward()[i].key()
                .compareTo(key) < 0))
                x = x.getForward()[i];
            update[i] = x; // Track end at level i
        }
        x = x.getForward()[0];
        // if final node we arrived matches key, remove links from this node and
        // rearrange links to point to next element in skiplist
        if ((x != null) && x.key().compareTo(key) == 0) {
            int levelX = x.getLevel();
            for (int i = levelX; i >= 0; i--) { // Splice into list
                update[i].getForward()[i] = x.getForward()[i];
            }
            System.out.println("Rectangle removed: " + "(" + x.key() + ", " + x
                .element() + ")");
            size--;
            return "RemovedByName";
        }
        else {
            System.out.println("Rectangle not removed: " + "(" + key + ")");
            return "NotInList";
        }
    }


    /**
     * 
     * remove rectangle with given coords if it exists
     * 
     * @param val
     *            rectangle coordinates
     * @return info string
     * 
     */
    public String removeByCoords(E val) {

        // if size is zero, cannot remove anything:
        if (this.size == 0) {
            return "ListEmpty";
        }

        SkipNode<K, E> x = head.getForward()[0];
        // search for the coords starting from beginning:
        for (int i = 0; i < size; i++) {
            if (x.element().compareTo(val) != 0)
                x = x.getForward()[0];
            else {
                // Actually, we could remove the rectangle inside this
                // else statement, instead of calling removeByName below.
                // But for the sake of readability, I decided to make an
                // additional
                // call to removebyname below. Since skipList is fast
                // for finding names, it does not cause much of a computational
                // overhead
                break;
            }
        }
        // The following means that search reached the end of the skiplist
        // without
        // finding searched coords, report it:
        if (x == null) {
            System.out.println("Rectangle not removed: " + val);
            return "EOL";
        }

        K key = x.key();
        removeByName(key);
        return "RemovedByCoords";
    }


    /**
     * 
     * dump all rectangles and the head
     * 
     */
    public void dump() {
        SkipNode<K, E> x = null;
        x = this.head;

        System.out.println("SkipList dump:");
        // special format for head node:
        System.out.println("Node has depth " + x.getLevel() + ", " + "Value ("
            + x.key() + ")");
        x = x.getForward()[0];

        for (int i = 1; i < this.size + 1; i++) {

            System.out.println("Node has depth " + x.getLevel() + ", "
                + "Value (" + x.key() + ", " + x.element() + ")");
            x = x.getForward()[0];
        }
        System.out.println("SkipList size is: " + this.size);

    }


    /**
     * 
     * if a rectangle with a level larger than the head to be inserted, adjust
     * head level
     * 
     * @param newLevel
     *            level of the new rectangle
     */
    private void adjustHead(int newLevel) {
        SkipNode<K, E> temp = head;
        head = new SkipNode<K, E>(null, null, newLevel);
        for (int i = 0; i <= level; i++)
            head.getForward()[i] = temp.getForward()[i];
        level = newLevel;
    }


    /**
     * 
     * get head level
     * 
     * @return level
     */
    public int getLevel() {
        return this.level;
    }


    /**
     * 
     * get size of the skip list
     * 
     * @return size
     */
    public int getSize() {
        return this.size;
    }


    /**
     * 
     * return head node
     * 
     * @return head
     */
    public SkipNode<K, E> getHead() {
        return this.head;
    }
}
