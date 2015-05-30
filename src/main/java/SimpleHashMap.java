
public class SimpleHashMap {

    /**
     * The number of key-value mappings contained in this map.
     */
    private int size;
    /**
     * The maximum number of key-value mappings that could be contained in this map.
     */
    private int capacity;

    private class Entry {

        private static final long EMPTY_VALUE = Long.MIN_VALUE;

        private int key;
        private long value;

        public Entry(int key) {
            this(key, EMPTY_VALUE);
        }

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

    public SimpleHashMap(int capacity) {
        if (capacity < 1) {
            throw new RuntimeException("Capacity can be only more than 1!");
        }
        this.size = 0;
        this.capacity = capacity;
        entries = new Entry[capacity];
    }

    public void put(int key, long value) {
        if (size == capacity) {
            throw new RuntimeException("The map is full!");
        }
        for (int i = index(hash(key)); ; i++) {
            if (i == capacity) {
                i = 0;
            }
            Entry entry = entries[i];
            if (entry == null) {
                entry = new Entry(key);
                entries[i] = entry;
                size++;
            }
            if (entry.getKey() == key) {
                entry.setValue(value);
                return;
            }
        }
    }

    public long get(int key) {
        if (size == 0) {
            throw new RuntimeException("The map is empty!");
        }
        for (int i = index(hash(key)); ; i++) {
            if (i == capacity) {
                i = 0;
            }
            Entry entry = entries[i];
            if (entry == null) {
                throw new RuntimeException("No such key!");
            }
            if (entry.getKey() == key) {
                return entry.getValue();
            }
        }
    }

    public int size() {
        return this.size;
    }

    private int hash(int x) {
        return (x >> 15) ^ x;
    }

    private int index(int hash) {
        return Math.abs(hash) % capacity;
    }
}
