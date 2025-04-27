//  Group Project COMP 2080
//  Eduard Kosenko 101480050
//  Evgenii Baldin 101435160
//  Leonard Eriyo 101511592
//  Mariia Shmidt 101470474
//  Mateus Sfeir 101484904

package Structure;

public class MyHashMap<K, V> {
    private static final int INITIAL_CAPACITY = 256;
    private static final double LOAD_FACTOR = 0.75;

    private static class Entry<K, V> {
        K key;
        V value;
        boolean isDeleted;

        Entry(K key, V value) {
            this.key = key;
            this.value = value;
            this.isDeleted = false;
        }
    }

    private Entry<K, V>[] buckets;
    private int size = 0;

    @SuppressWarnings("unchecked")
    public MyHashMap() {
        buckets = (Entry<K, V>[]) new Entry[INITIAL_CAPACITY];
        size = 0;
    }

    @SuppressWarnings("unchecked")
    public MyHashMap(int initialCapacity) {
        buckets = (Entry<K, V>[]) new Entry[initialCapacity];
        size = 0;
    }

    private int getBucketIndex(K key, int capacity) {
        return Math.abs(key.hashCode()) % capacity;
    }

    public boolean containsKey(K key) {
        return get(key) != null;
    }

    public V get(K key) {
        int index = getBucketIndex(key, buckets.length);
        for (int i = 0; i < buckets.length; i++) {
            int probeIndex = (index + i) % buckets.length;
            Entry<K, V> entry = buckets[probeIndex];
            if (entry == null) return null;
            if (!entry.isDeleted && entry.key.equals(key)) {
                return entry.value;
            }
        }
        return null;
    }

    public void put(K key, V value) {
        if ((size + 1) >= buckets.length * LOAD_FACTOR) {
            resize();
        }

        int index = getBucketIndex(key, buckets.length);
        int firstDeletedIndex = -1;

        for (int i = 0; i < buckets.length; i++) {
            int probeIndex = (index + i) % buckets.length;
            Entry<K, V> entry = buckets[probeIndex];

            if (entry == null) {
                if (firstDeletedIndex != -1) {
                    buckets[firstDeletedIndex].key = key;
                    buckets[firstDeletedIndex].value = value;
                    buckets[firstDeletedIndex].isDeleted = false;
                } else {
                    buckets[probeIndex] = new Entry<>(key, value);
                }
                size++;
                return;
            }

            if (entry.isDeleted) {
                if (entry.key.equals(key)) {
                    entry.value = value;
                    entry.isDeleted = false;
                    size++;
                    return;
                }
                if (firstDeletedIndex == -1) {
                    firstDeletedIndex = probeIndex;
                }
            } else if (entry.key.equals(key)) {
                entry.value = value;
                return;
            }
        }

        if (firstDeletedIndex != -1) {
            buckets[firstDeletedIndex] = new Entry<>(key, value);
            size++;
            return;
        }

        System.out.println("Error: HashMap is full (should not happen with resizing).");
    }

    public void remove(K key) {
        int index = getBucketIndex(key, buckets.length);
        for (int i = 0; i < buckets.length; i++) {
            int probeIndex = (index + i) % buckets.length;
            Entry<K, V> entry = buckets[probeIndex];
            if (entry == null) return;
            if (!entry.isDeleted && entry.key.equals(key)) {
                entry.isDeleted = true;
                size--;
                return;
            }
        }
    }

    public void clear() {
        for (int i = 0; i < buckets.length; i++) {
            buckets[i] = null;
        }
        size = 0;
    }

    public int size() {
        return size;
    }

    @SuppressWarnings("unchecked")
    private void resize() {
        Entry<K, V>[] oldBuckets = buckets;
        buckets = (Entry<K, V>[]) new Entry[oldBuckets.length * 2];
        size = 0;

        for (Entry<K, V> entry : oldBuckets) {
            if (entry != null && !entry.isDeleted) {
                put(entry.key, entry.value);
            }
        }
    }
}
