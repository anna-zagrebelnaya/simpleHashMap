import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;

import static org.junit.Assert.assertEquals;

public class SimpleHashMapTest {

    private SimpleHashMap simpleHashMap;

    @Before
    public void beforeTest() {
        simpleHashMap = new SimpleHashMap(2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateMapWithIllegalInitialCapacity() {
        new SimpleHashMap(-1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateMapWithIllegalLoadFactor() {
        new SimpleHashMap(1, -1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateMapWithNanLoadFactor() {
        new SimpleHashMap(1, Float.NaN);
    }

    @Test
    public void testResize() {
        simpleHashMap.put(1, 1);
        assertEquals(2, getCapacity(simpleHashMap));

        simpleHashMap.put(2, 2);
        assertEquals(4, getCapacity(simpleHashMap));

        simpleHashMap.put(3, 3);
        assertEquals(8, getCapacity(simpleHashMap));

        simpleHashMap.put(4, 4);
        simpleHashMap.put(5, 5);
        simpleHashMap.put(6, 6);
        assertEquals(16, getCapacity(simpleHashMap));
    }

    private int getCapacity(SimpleHashMap map) {
        try {
            Field f = simpleHashMap.getClass().getDeclaredField("capacity");
            f.setAccessible(true);
            return f.getInt(map);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Test(expected = IllegalStateException.class)
    public void testGetFromEmptyMap() {
        assertEquals(0, simpleHashMap.get(0));
    }

    @Test
    public void testChangeValue() {
        simpleHashMap.put(1, 1);
        simpleHashMap.put(2, 2);
        simpleHashMap.put(3, 3);
        assertEquals(3, simpleHashMap.get(3));

        simpleHashMap.put(3, 4);
        assertEquals(4, simpleHashMap.get(3));
        assertEquals(3, simpleHashMap.size());
    }

    @Test(expected = IllegalStateException.class)
    public void testGetByInvalidKey() {
        simpleHashMap.put(1, 1);
        simpleHashMap.get(2);
    }

    @Test(expected = IllegalStateException.class)
    public void testReachMaximumCapacity() {
        for (int i = 0; i < (1 << 10) + 1; i++) {
            simpleHashMap.put(i, i);
        }
    }
}