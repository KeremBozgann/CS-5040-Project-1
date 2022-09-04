// reference: taken from dsa code in section 15.1. Skip Lists

class SkipNode<K extends Comparable<K>, E> {
    private KVPair<K, E> rec;
    private int level;
    private SkipNode<K, E>[] forward;

    public E element() {
        return rec.value();
    }


    public K key() {
        return rec.key();
    }


    @SuppressWarnings("unchecked")
    public SkipNode(K key, E elem, int level) {
        rec = new KVPair<K, E>(key, elem);
        forward = new SkipNode[level + 1];
        this.level = level;

        for (int i = 0; i < level; i++)
            forward[i] = null;
    }


    public String toString() {
        return rec.toString();
    }


    public int getLevel() {
        return this.level;
    }


    public SkipNode<K, E>[] getForward() {
        return this.forward;
    }
    
    public KVPair<K, E> getRec() {
        return this.rec;
    }

}
