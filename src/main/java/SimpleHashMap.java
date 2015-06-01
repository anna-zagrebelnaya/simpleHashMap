/**
 * Hash table with open addressing and linear probing
 */
public class SimpleHashMap {

    private static final int DEFAULT_CAPACITY = 1 << 4;
    private static final int MAXIMUM_CAPACITY = 1 << 10;
    private static final float DEFAULT_LOAD_FACTOR = 0.75f;

    /**
     * The number of key-value mappings contained in this map.
     */
    private int size;
    /**
     * The maximum number of key-value mappings that could be contained in this map.
     */
    private int capacity;
    private final float loadFactor;

    private class Entry {

        private int key;
        private long value;

        public Entry(int key, long value) {
            this.key = key;
            this.value = value;
        }

        public int getKey() {
            return key;
        }

        public long getValue() {
            return value;
        }

        public void setValue(long value) {
            this.value = value;
        }
    }

    private Entry[] entries;

    public SimpleHashMap() {
        this(DEFAULT_CAPACITY, DEFAULT_LOAD_FACTOR);
    }

    public SimpleHashMap(int capacity) {
        this(capacity, DEFAULT_LOAD_FACTOR);
    }

    public SimpleHashMap(int initialCapacity, float loadFactor) {
        if (initialCapacity < 0) {
            throw new IllegalArgumentException("Illegal initial capacity:" +
                    initialCapacity);
        }
        if (initialCapacity > MAXIMUM_CAPACITY)
            initialCapacity = MAXIMUM_CAPACITY;
        if (loadFactor <= 0 || Float.isNaN(loadFactor))
            throw new IllegalArgumentException("Illegal load factor: " +
                    loadFactor);
        size = 0;
        capacity = initialCapacity;
        this.loadFactor = loadFactor;
        entries = new Entry[this.capacity];
    }

    public void put(int key, long value) {
        if (size == MAXIMUM_CAPACITY) {
            throw new IllegalStateException("The map is full!");
        }
        for (int i = index(hash(key)); ; i++) {
            if (i == capacity) {
                i = 0;
            }
            Entry entry = entries[i];
            if (entry == null) {
                entries[i] = new Entry(key, value);
                size++;
                if (size >= capacity*loadFactor) {
                    resize(capacity*2);
                }
                return;
            } else if (entry.getKey() == key) {
                entry.setValue(value);
                return;
            }
        }
    }

    public long get(int key) {
        if (size == 0) {
            throw new IllegalStateException("The map is empty!");
        }
        for (int i = index(hash(key)); ; i++) {
            if (i == capacity) {
                i = 0;
            }
            Entry entry = entries[i];
            if (entry == null) {
                throw new IllegalStateException("No such key!");
            }
            if (entry.getKey() == key) {
                return entry.getValue();
            }
        }
    }

    public int size() {
        return this.size;
    }

    private int hash(int key) {
        return (key >> 15) ^ key;
    }

    private int index(int hash) {
        return index(hash, capacity);
    }

    private int index(int hash, int capacity) {
        return Math.abs(hash) % capacity;
    }

    private void resize(int newCapacity) {
        Entry[] newEntries = new Entry[newCapacity];
        transfer(newEntries);
        entries = newEntries;
        capacity = newCapacity;
    }

    private void transfer(Entry[] newEntries) {
        for (int i=0; i<capacity; i++) {
            Entry oldEntry = entries[i];
            if (oldEntry != null) {
                putIntoEntries(oldEntry.getKey(), oldEntry.getValue(), newEntries);
            }
        }
    }

    private void putIntoEntries(int key, long value, Entry[] entries) {
        for (int i = index(hash(key), entries.length); ; i++) {
            if (i == entries.length) {
                i = 0;
            }
            Entry newEntry = entries[i];
            if (newEntry == null) {
                entries[i] = new Entry(key, value);
                return;
            }
        }
    }
}
