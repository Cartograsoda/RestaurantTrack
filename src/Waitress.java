import java.util.List;

public class Waitress extends RestaurantComponent {
    private OrderCollection<Order> orders;

    public Waitress(Mediator mediator, OrderCollection<Order> orders) {
        super(mediator);
        this.orders = orders;
    }

    public void startDelivery(Order order) {
        if (order.getOrderState() != OrderState.PREPARATION_COMPLETED) {
            throw new InvalidOrderStateException(
                    "Cannot deliver order #" + order.getId() + ". State: " + order.getOrderState());
        }
        order.setOrderState(OrderState.IN_DELIVERY);
        order.setDeliveryState(DeliveryState.IN_TRANSIT);

        if (ProbabilityCalculator.isDeliveryDelayed()) {
            order.setDelayed(true);
            order.setRemainingDeliveryTime(order.getRemainingDeliveryTime() + 2);
            order.setDeliveryState(DeliveryState.DELAYED);
            System.out.println("[Waitress] Order #" + order.getId() + " delivery DELAYED (+2s).");
            mediator.notify(this, "DELIVERY_DELAYED", order);
        } else {
            System.out.println("[Waitress] Order #" + order.getId() + " out for delivery (" +
                    order.getRemainingDeliveryTime() + "s).");
        }
    }

    public void tick() {
        List<Order> inDelivery = orders.getByState(OrderState.IN_DELIVERY);
        for (Order order : inDelivery) {
            int remaining = order.getRemainingDeliveryTime() - 1;
            order.setRemainingDeliveryTime(remaining);
            if (remaining <= 0) {
                order.setOrderState(OrderState.DELIVERED);
                order.setDeliveryState(DeliveryState.DELIVERED);
                System.out.println("[Waitress] Order #" + order.getId() + " delivered!");
                mediator.notify(this, "DELIVERY_COMPLETE", order);
            }
        }
    }

    @Override
    public String getName() {
        return "Waitress";
    }
}
