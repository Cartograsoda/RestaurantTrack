import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Waitress extends RestaurantComponent {
    private List<Order> ordersInDelivery;

    public Waitress(Restaurant mediator) {
        super(mediator);
        this.ordersInDelivery = new ArrayList<>();
    }

    public void startDelivery(Order order) {
        if (order.getOrderState() != OrderState.PREPARATION_COMPLETED) {
            throw new InvalidOrderStateException(
                    "Cannot deliver order #" + order.getId() + ". State: " + order.getOrderState());
        }
        order.setOrderState(OrderState.IN_DELIVERY);
        order.setDeliveryState(DeliveryState.IN_TRANSIT);
        ordersInDelivery.add(order);

        if (ProbabilityCalculator.isDeliveryDelayed()) {
            order.setDelayed(true);
            order.setRemainingDeliveryTime(order.getRemainingDeliveryTime() + 2);
            order.setDeliveryState(DeliveryState.DELAYED);
            System.out.println("[Waitress] Order #" + order.getId() + " delivery DELAYED (+2s).");

            mediator.notifyDeliveryDelayed(order);
        } else {
            System.out.println("[Waitress] Order #" + order.getId() + " out for delivery (" +
                    order.getRemainingDeliveryTime() + "s).");
        }
    }

    public void deliver() {
        Iterator<Order> it = ordersInDelivery.iterator();
        while (it.hasNext()) {
            Order order = it.next();
            int remaining = order.getRemainingDeliveryTime() - 1;
            order.setRemainingDeliveryTime(remaining);

            if (remaining <= 0) {
                order.setOrderState(OrderState.DELIVERED);
                order.setDeliveryState(DeliveryState.DELIVERED);
                System.out.println("[Waitress] Order #" + order.getId() + " delivered!");
                it.remove();
                mediator.notifyDeliveryCompleted(order);
            }
        }
    }

    @Override
    public String getName() {
        return "Waitress";
    }
}
