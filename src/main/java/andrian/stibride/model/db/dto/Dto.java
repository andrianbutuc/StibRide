package andrian.stibride.model.db.dto;
/**
 * Generic class to build a Data transfer object. The data carried is known by
 * its key.
 *
*/
public class Dto<K> {
    /**
     * Key of the data.
     */
    protected final K key;

    /**
     * Creates a new instance of <code>Dto</code> with the key of the data.
     *
     * @param key key of the data.
     */
    protected Dto(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Clé absente " + key);
        }
        this.key = key;
    }

    /**
     * Returns the key of the data.
     *
     * @return the key of the data.
     */
    public K getKey() {
        return key;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Dto)) return false;

        Dto<?> dto = (Dto<?>) o;

        return getKey().equals(dto.getKey());
    }

    @Override
    public int hashCode() {
        return getKey().hashCode();
    }
}
