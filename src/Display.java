import java.util.List;

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
        System.out.println("Total Orders Created : " + state.getTotalCreated());
        System.out.println("Canceled             : " + state.getCanceledCount());
        System.out.println("Delayed              : " + state.getDelayedCount());
        System.out.println("Delivered            : " + state.getDeliveredCount());
        System.out.println("Total Revenue        : " + String.format("%.2f", state.getTotalRevenue()));
        System.out.println("--- Active Orders ---");
        List<Order> all = orders.getAll();
        if (all.isEmpty()) {
            System.out.println("  (none)");
        } else {
            for (Order order : all) {
                if (order.getOrderState() != OrderState.DELIVERED &&
                    order.getOrderState() != OrderState.CANCELED) {
                    System.out.println("  " + order);
                }
            }
        }
        System.out.println("====================================\n");
    }

    @Override
    public String getName() {
        return "Display";
    }
}
