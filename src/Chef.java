import java.util.List;

public class Chef extends RestaurantComponent {
    private OrderCollection<Order> orders;

    public Chef(Mediator mediator, OrderCollection<Order> orders) {
        super(mediator);
        this.orders = orders;
    }

    public void tick() {
        List<Order> inPrep = orders.getByState(OrderState.IN_PREPARATION);
        for (Order order : inPrep) {
            int remaining = order.getRemainingPrepTime() - 1;
            order.setRemainingPrepTime(remaining);
            if (remaining <= 0) {
                order.setOrderState(OrderState.PREPARATION_COMPLETED);
                System.out.println("[Chef] Order #" + order.getId() + " preparation completed.");
                mediator.notify(this, "PREPARATION_DONE", order);
            }
        }
    }

    @Override
    public String getName() {
        return "Chef";
    }
}
