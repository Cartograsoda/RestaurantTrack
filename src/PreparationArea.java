import java.util.ArrayList;
import java.util.List;

public class PreparationArea extends RestaurantComponent {
    private List<Order> orders;

    public PreparationArea(Restaurant mediator) {
        super(mediator);
        this.orders = new ArrayList<>();
    }

    public boolean addOrder(Order order) {
        if (order.getOrderState() != OrderState.RECEIVED) {
            return false;
        }
        order.setOrderState(OrderState.IN_PREPARATION);
        orders.add(order);
        System.out.println("[PreparationArea] Order #" + order.getId() +
                " added to preparation (Prep time: " + order.getPreparationTime() + "s).");
        return true;
    }

    public List<Order> getOrders() {
        return orders;
    }

    @Override
    public String getName() {
        return "PreparationArea";
    }
}
