/**
 * reference: taken from dsa code in section 15.1. Skip Lists
 * 
 * @author Kerem Bozgan kerembozgan
 * @version 2022-09-03
 * @param <K>
 *            key
 * @param <E>
 *            value
 */
class SkipNode<K extends Comparable<K>, E> {
    private KVPair<K, E> rec;
    private int level;
    private SkipNode<K, E>[] forward;

    /**
     * @return rectangle value
     */
    public E element() {
        return rec.value();
    }


    /**
     * @return rectangle key
     */
    public K key() {
        return rec.key();
    }


    /**
     * @param key
     *            key
     * @param elem
     *            value
     * @param level
     *            level of the rectangle in skipList
     *            set up node
     */
    public SkipNode(K key, E elem, int level) {

        rec = new KVPair<K, E>(key, elem);
        @SuppressWarnings("unchecked")
        SkipNode<K, E>[] newForwardList = new SkipNode[level + 1];
        forward = newForwardList;
        this.level = level;

        for (int i = 0; i < level; i++)
            forward[i] = null;

    }


    /**
     * @return combine rec key and rec values to a string
     */
    public String toString() {
        return rec.toString();
    }


    /**
     * @return access rec level
     */
    public int getLevel() {
        return this.level;
    }


    /**
     * @return links of the node
     */
    public SkipNode<K, E>[] getForward() {
        return this.forward;
    }


    /**
     * @return node's kvpair
     */
    public KVPair<K, E> getRec() {
        return this.rec;
    }

}
