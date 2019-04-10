package solutions.thinkbiz.grocery;

/**
 * Created by User on 03-Apr-19.
 */

public interface myMethod<TData> {
    long UpdateQty(String cartId, String newqty);
    void deleteMethod(String cartId);
}
