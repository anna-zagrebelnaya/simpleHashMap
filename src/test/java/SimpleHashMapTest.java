import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class SimpleHashMapTest {

    private static final int CAPACITY = 2;

    private SimpleHashMap simpleHashMap;

    @Before
    public void beforeTest() {
        simpleHashMap = new SimpleHashMap(CAPACITY);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateMapWithZeroCapacity() {
        new SimpleHashMap(0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateMapWithNegativeCapacity() {
        new SimpleHashMap(-1);
    }

    @Test
    public void testSizeIsNotMoreThanCapacity() {
        Assert.assertEquals(simpleHashMap.size(), 0);

        simpleHashMap.put(1, 1);
        simpleHashMap.put(2, 2);

        Assert.assertEquals(simpleHashMap.size(), 2);

        try {
            simpleHashMap.put(3, 3);
        } catch (RuntimeException e) {}

        Assert.assertEquals(simpleHashMap.size(), 2);
    }

    @Test(expected = IllegalStateException.class)
    public void testPutInFullMap() {
        simpleHashMap.put(1, 1);
        simpleHashMap.put(2, 2);
        simpleHashMap.put(3, 3);
    }

    @Test(expected = IllegalStateException.class)
    public void testGetFromEmptyMap() {
        Assert.assertEquals(simpleHashMap.get(0), 0);
    }

    @Test
    public void testChangeValue() {
        simpleHashMap.put(1, 1);
        Assert.assertEquals(simpleHashMap.get(1), 1);

        simpleHashMap.put(1, 2);
        Assert.assertEquals(simpleHashMap.get(1), 2);
    }

    @Test(expected = IllegalStateException.class)
    public void testGetByInvalidKey() {
        simpleHashMap.put(1, 1);
        simpleHashMap.get(2);
    }

}