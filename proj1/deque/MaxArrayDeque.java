package deque;

import java.util.Comparator;

public class MaxArrayDeque<T> extends ArrayDeque<T> {

    private T maxi;
    private Comparator<T> comparator;

    public MaxArrayDeque(Comparator<T> c) {
        super();
        comparator = c;
    }

    //use next max
    public T max() {
        return max(comparator);
    }

    public T max(Comparator<T> c) {
        if (this.size() == 0) {
            return null;
        }
        maxi = this.get(0);
        for (int i = 0; i < this.size(); i++) {
            if (c.compare(this.get(i), maxi) > 0) {
                maxi = this.get(i);
            }
        }
        return maxi;
    }
}
