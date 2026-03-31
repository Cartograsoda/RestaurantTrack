import java.util.List;

public class PreparationArea extends RestaurantComponent {
    private OrderCollection<Order> orders;

    public PreparationArea(Mediator mediator, OrderCollection<Order> orders) {
        super(mediator);
        this.orders = orders;
    }

    public void addOrder(Order order) {
        if (order.getOrderState() != OrderState.RECEIVED) {
            throw new InvalidOrderStateException(
                    "Cannot add order #" + order.getId() + " to preparation. State: " + order.getOrderState());
        }
        order.setOrderState(OrderState.IN_PREPARATION);
        orders.add(order);
        System.out.println("[PreparationArea] Order #" + order.getId() +
                " added to preparation (Prep time: " + order.getPreparationTime() + "s).");
    }

    public OrderCollection<Order> getOrders() {
        return orders;
    }

    @Override
    public String getName() {
        return "PreparationArea";
    }
}
