package Yeah_Zero.Subtitle_Highlight.Util;

import java.util.ArrayList;
import java.util.List;

public class SubtitlePool<T> {
    private final List<T> pool;
    private final int maxSize;
    private final ObjectFactory<T> factory;

    public interface ObjectFactory<T> {
        T create();
    }

    public SubtitlePool(int maxSize, ObjectFactory<T> factory) {
        this.pool = new ArrayList<>(maxSize);
        this.maxSize = maxSize;
        this.factory = factory;
    }

    public synchronized T acquire() {
        if (pool.isEmpty()) {
            return factory.create();
        }
        return pool.remove(pool.size() - 1);
    }

    public synchronized void release(T object) {
        if (pool.size() < maxSize) {
            pool.add(object);
        }
    }

    public synchronized void clear() {
        pool.clear();
    }

    public synchronized int size() {
        return pool.size();
    }
}