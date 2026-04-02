
public class PreparationArea extends RestaurantComponent {

    private OrderCollection<Order> orders;

    public PreparationArea(Mediator mediator, OrderCollection<Order> orders) {
        super(mediator);
        this.orders = orders;
    }

    public void addOrder(Order order) {
        mediator.notify(this, "PREPARATION_STARTED", order);
    }

    public OrderCollection<Order> getOrders() {
        return orders;
    }

    @Override
    public String getName() {
        return "PreparationArea";
    }
}
