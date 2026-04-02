
import java.util.List;

public class Chef extends RestaurantComponent {

    private OrderCollection<Order> orders;

    public Chef(Mediator mediator, OrderCollection<Order> orders) {
        super(mediator);
        this.orders = orders;
    }

    public void prepareOrders() {
        List<Order> inPrep = orders.getByState(OrderState.IN_PREPARATION);
        for (Order order : inPrep) {
            mediator.notify(this, "PREPARE_TICK", order);
        }
    }

    @Override
    public String getName() {
        return "Chef";
    }
}
