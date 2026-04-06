import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Chef extends RestaurantComponent {
    private List<Order> ordersInPreparation;

    public Chef(Restaurant mediator) {
        super(mediator);
        ordersInPreparation = new ArrayList<>();
    }

    public void startCooking(Order order) {
        if (order.getOrderState() != OrderState.IN_PREPARATION) {
            throw new InvalidOrderStateException(
                    "Cannot start cooking order #" + order.getId() + ". State: " + order.getOrderState());
        }
        ordersInPreparation.add(order);
    }

    public void cook() {
        Iterator<Order> it = ordersInPreparation.iterator();
        while (it.hasNext()) {
            Order order = it.next();
            int remaining = order.getRemainingPrepTime() - 1;
            order.setRemainingPrepTime(remaining);
            if (remaining <= 0) {
                order.setOrderState(OrderState.PREPARATION_COMPLETED);
                System.out.println("[Chef] Order #" + order.getId() + " preparation completed.");

                it.remove();
                mediator.notifyPreparationDone(order);
            }
        }
    }

    @Override
    public String getName() {
        return "Chef";
    }
}
