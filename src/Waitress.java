
import java.util.List;

public class Waitress extends RestaurantComponent {

    private OrderCollection<Order> orders;

    public Waitress(Mediator mediator, OrderCollection<Order> orders) {
        super(mediator);
        this.orders = orders;
    }

    public void startDelivery(Order order) {
        mediator.notify(this, "DELIVERY_STARTED", order);
    }

    public void serveDeliveries() {
        List<Order> inDelivery = orders.getByState(OrderState.IN_DELIVERY);
        for (Order order : inDelivery) {
            mediator.notify(this, "DELIVERY_TICK", order);
        }
    }

    @Override
    public String getName() {
        return "Waitress";
    }
}
