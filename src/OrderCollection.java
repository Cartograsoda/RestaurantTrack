import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class OrderCollection<T extends Order> {
    private List<T> orders;

    public OrderCollection() {
        this.orders = new ArrayList<>();
    }

    public void add(T order) {
        orders.add(order);
    }

    public void remove(T order) {
        orders.remove(order);
    }

    public List<T> getByState(OrderState state) {
        return orders.stream()
                .filter(o -> o.getOrderState() == state)
                .collect(Collectors.toList());
    }

    public List<T> getAll() {
        return new ArrayList<>(orders);
    }

    public int size() {
        return orders.size();
    }
}
