
import java.util.List;
import java.util.stream.Collectors;

public class Display extends RestaurantComponent {

    private RestaurantState state;
    private OrderCollection<Order> orders;

    public Display(Mediator mediator, RestaurantState state, OrderCollection<Order> orders) {
        super(mediator);
        this.state = state;
        this.orders = orders;
    }

    public void show(int tick) {
        System.out.println("\n========== DISPLAY (Tick " + tick + ") ==========");
        System.out.println("Total Orders Created       : " + state.getTotalCreated());
        System.out.println("Number of Canceled Orders  : " + state.getCanceledCount() + " (Early Cancellation: " + state.getEarlyCanceledCount() + ", Payment Failed: " + state.getPaymentFailedCount() + ")");
        System.out.println("Number of Delayed Orders   : " + state.getDelayedCount());
        System.out.println("Number of Delivered Orders : " + state.getDeliveredCount());
        System.out.println("Currently Being Prepared   : " + orders.getByState(OrderState.IN_PREPARATION).size());
        System.out.println("Waiting (Prep Completed)   : " + orders.getByState(OrderState.PREPARATION_COMPLETED).size());
        System.out.println("Currently In Delivery      : " + orders.getByState(OrderState.IN_DELIVERY).size());
        List<Order> activeOrders = orders.getAll().stream()
                .filter(o -> o.getOrderState() != OrderState.DELIVERED
                && o.getOrderState() != OrderState.CANCELED)
                .collect(Collectors.toList());
        System.out.println("Number of Active Orders    : " + activeOrders.size());
        System.out.println("State of Active Orders     :");
        if (activeOrders.isEmpty()) {
            System.out.println("  (none)");
        } else {
            for (Order order : activeOrders) {
                System.out.println("  " + order);
            }
        }
        System.out.println("Total Revenue             : " + String.format("%.2f", state.getTotalRevenue()));
        System.out.println("====================================\n");
    }



    @Override
    public String getName() {
        return "Display";
    }
}
