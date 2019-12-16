package wang.ismy.seeaw4.common.promise;

/**
 * @author MY
 * @date 2019/12/16 22:06
 */
public class IdGenerator {

    private volatile long next = 0;

    public synchronized long next() {
        next++;
        return next;
    }

    public synchronized String nextStr() {
        next++;
        return String.valueOf(next);
    }
}
