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
                orderService.createOrder();
                cancellationService.cancelOrder();
                for (Order o : orders.getByState(OrderState.RECEIVED)) {
                    preparationArea.addOrder(o);
                }
                chef.prepareOrders();
                paymentService.addPaymentStatus();
                waitress.serveDeliveries();
                if (currentTick % 2 == 0) {
                    display.show(currentTick);
                }
                break;

            case "ORDER_CREATED":
                state.incrementCreated();
                orders.add(order);
                break;

            case "ORDER_CANCELED":
                order.setOrderState(OrderState.CANCELED);
                state.incrementEarlyCanceled();
                break;

            case "PREPARATION_STARTED":
                if (order.getOrderState() != OrderState.RECEIVED) {
                    throw new InvalidOrderStateException(
                        "Cannot add order #" + order.getId() + " to preparation. State: " + order.getOrderState());
                }
                order.setOrderState(OrderState.IN_PREPARATION);
                System.out.println("[PreparationArea] Order #" + order.getId() +
                    " added to preparation (Prep time: " + order.getPreparationTime() + "s).");
                break;

            case "PREPARE_TICK":
                int prepRemaining = order.getRemainingPrepTime() - 1;
                order.setRemainingPrepTime(prepRemaining);
                if (prepRemaining <= 0) {
                    order.setOrderState(OrderState.PREPARATION_COMPLETED);
                    System.out.println("[Chef] Order #" + order.getId() + " preparation completed.");
                }
                break;

            case "PAYMENT_APPROVED":
                order.setPaymentState(PaymentState.APPROVED);
                waitress.startDelivery(order);
                break;

            case "PAYMENT_FAILED":
                order.setPaymentState(PaymentState.FAILED);
                order.setOrderState(OrderState.CANCELED);
                state.incrementPaymentFailed();
                break;

            case "DELIVERY_STARTED":
                order.setOrderState(OrderState.IN_DELIVERY);
                order.setDeliveryState(DeliveryState.IN_TRANSIT);
                if (ProbabilityCalculator.isDeliveryDelayed()) {
                    order.setDelayed(true);
                    order.setRemainingDeliveryTime(order.getRemainingDeliveryTime() + 2);
                    order.setDeliveryState(DeliveryState.DELAYED);
                    System.out.println("[Waitress] Order #" + order.getId() + " delivery DELAYED (+2s).");
                    state.incrementDelayed();
                } else {
                    System.out.println("[Waitress] Order #" + order.getId() + " out for delivery (" +
                        order.getRemainingDeliveryTime() + "s).");
                }
                break;

            case "DELIVERY_TICK":
                int delRemaining = order.getRemainingDeliveryTime() - 1;
                order.setRemainingDeliveryTime(delRemaining);
                if (delRemaining <= 0) {
                    order.setOrderState(OrderState.DELIVERED);
                    order.setDeliveryState(DeliveryState.DELIVERED);
                    System.out.println("[Waitress] Order #" + order.getId() + " delivered!");
                    state.incrementDelivered();
                    state.addRevenue(order.getPrice());
                }
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