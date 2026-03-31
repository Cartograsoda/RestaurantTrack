import java.util.List;

public class CancellationService extends RestaurantComponent {
    private OrderCollection<Order> orders;

    public CancellationService(Mediator mediator, OrderCollection<Order> orders) {
        super(mediator);
        this.orders = orders;
    }

    public void tick() {
        List<Order> receivedOrders = orders.getByState(OrderState.RECEIVED);
        for (Order order : receivedOrders) {
            if (ProbabilityCalculator.shouldCancelEarly()) {
                System.out.println("[CancellationService] Order #" + order.getId() + " canceled early.");
                mediator.notify(this, "ORDER_CANCELED", order);
            }
        }
    }

    @Override
    public String getName() {
        return "CancellationService";
    }
}
