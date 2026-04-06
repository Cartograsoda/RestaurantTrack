import java.util.ArrayList;
import java.util.List;

public class Display extends RestaurantComponent {
    private RestaurantState state;
    private List<Order> displayedOrders;

    public Display(Restaurant mediator, RestaurantState state) {
        super(mediator);
        this.state = state;
        this.displayedOrders = new ArrayList<>();
    }

    public void show(int tick) {
        if (tick % 2 == 0) {
            System.out.println("\n========== DISPLAY (Tick " + tick + ") ==========");
            System.out.println("Total Orders Created       : " + state.getTotalCreated());
            System.out.println("Number of Canceled Orders  : " + state.getCanceledCount() + " (Early Cancellation: " + state.getEarlyCanceledCount() + ", Payment Failed: " + state.getPaymentFailedCount() + ")");
            System.out.println("Number of Delayed Orders   : " + state.getDelayedCount());
            System.out.println("Number of Delivered Orders : " + state.getDeliveredCount());
            System.out.println("Currently Being Prepared   : " + state.getPreparedCount());
            System.out.println("Waiting (Prep Completed)   : " + state.getWaitingCount());
            System.out.println("Currently In Delivery      : " + state.getInDelivery());
            List<Order> activeOrders = displayedOrders.stream()
                    .filter(o -> o.getOrderState() != OrderState.DELIVERED
                            && o.getOrderState() != OrderState.CANCELED)
                    .toList();
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
    }

    public void setDisplayedOrders(List<Order> orders) {
        displayedOrders = orders;
    }

    @Override
    public String getName() {
        return "Display";
    }
}
