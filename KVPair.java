
/**
 * reference: taken from dsa code in section 15.1. Skip Lists
 * ODSATag: KVPair
 * KVPair class definition
 * 
 * @author Kerem Bozgan kerembozgan
 * @version 2022-09-03
 * @param <K>
 *            key
 * @param <E>
 *            value
 */
public class KVPair<K extends Comparable<K>, E>
    implements Comparable<KVPair<K, E>> {
    private K theKey;
    private E theVal;

    /**
     * @param k
     *            key
     * @param v
     *            value
     */
    KVPair(K k, E v) {
        theKey = k;
        theVal = v;
    }


    /**
     * Compare KVPairs
     * 
     * @param it
     *            a KVPair
     * @return compare result as integer
     */
    public int compareTo(KVPair<K, E> it) {
        return theKey.compareTo(it.key());
    }


    /**
     * Compare against a key
     * 
     * @param it
     *            a key
     * @return compare result as integer
     */
    public int compareTo(K it) {
        return theKey.compareTo(it);
    }


    /**
     * 
     * @return outside access to key
     */
    public K key() {
        return theKey;
    }


    /**
     * 
     * @return outside access to val
     */
    public E value() {
        return theVal;
    }


    /**
     * @return combine key and val in a string
     */
    public String toString() {
        return theKey.toString() + ", " + theVal.toString();
    }
}
/* *** ODSAendTag: KVPair *** */
