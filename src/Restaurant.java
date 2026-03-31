public class Restaurant implements Mediator {
    private OrderService orderService;
    private CancellationService cancellationService;
    private PreparationArea preparationArea;
    private Chef chef;
    private PaymentService paymentService;
    private Waitress waitress;
    private Display display;
    private Timer timer;

    private RestaurantState state;
    private OrderCollection<Order> orders;
    private int currentTick;

    public Restaurant() {
        this.state = new RestaurantState();
        this.orders = new OrderCollection<>();
        this.currentTick = 0;

        this.orderService = new OrderService(this);
        this.cancellationService = new CancellationService(this, orders);
        this.preparationArea = new PreparationArea(this, orders);
        this.chef = new Chef(this, orders);
        this.paymentService = new PaymentService(this, orders);
        this.waitress = new Waitress(this, orders);
        this.display = new Display(this, state, orders);
        this.timer = new Timer(this, 10);
    }

    @Override
    public void notify(RestaurantComponent sender, String event, Order order) {
        switch (event) {
            case "TICK":
                currentTick++;
                orderService.tick();
                cancellationService.tick();
                chef.tick();
                paymentService.tick();
                waitress.tick();
                if (currentTick % 2 == 0) {
                    display.show(currentTick);
                }
                break;

            case "ORDER_CREATED":
                state.incrementCreated();
                preparationArea.addOrder(order);
                break;

            case "ORDER_CANCELED":
                order.setOrderState(OrderState.CANCELED);
                state.incrementCanceled();
                break;

            case "PREPARATION_DONE":
                // PaymentService will pick it up on its tick
                break;

            case "PAYMENT_APPROVED":
                waitress.startDelivery(order);
                break;

            case "PAYMENT_FAILED":
                state.incrementCanceled();
                break;

            case "DELIVERY_COMPLETE":
                state.incrementDelivered();
                state.addRevenue(order.getPrice());
                break;

            case "DELIVERY_DELAYED":
                state.incrementDelayed();
                break;

            default:
                System.out.println("[Restaurant] Unknown event: " + event);
        }
    }

    @Override
    public void start() {
        System.out.println("=== Restaurant Simulation Starting ===");
        timer.start();
        display.show(currentTick);
        System.out.println("=== Restaurant Simulation Complete ===");
    }
}
