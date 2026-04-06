import java.util.List;

public class Restaurant {
    private OrderService orderService;
    private CancellationService cancellationService;
    private PreparationArea preparationArea;
    private Chef chef;
    private PaymentService paymentService;
    private Waitress waitress;
    private Display display;
    private Timer timer;

    private RestaurantState state;

    public Restaurant() {
        this.state = new RestaurantState();

        this.orderService = new OrderService(this);
        this.cancellationService = new CancellationService(this);
        this.preparationArea = new PreparationArea(this);
        this.chef = new Chef(this);
        this.paymentService = new PaymentService(this);
        this.waitress = new Waitress(this);
        this.display = new Display(this, state);
        this.timer = new Timer(this, 10);
    }

    public void onTick() {
        waitress.deliver();
        chef.cook();
        orderService.createOrder();
        display.setDisplayedOrders(getOrders());
        display.show(timer.getCurrentTick());
    }

    public void notifyOrderCreated(Order order) {
        state.incrementCreated();
        state.incrementWaitingCount();
        cancellationService.checkCancellation(order);
        if (preparationArea.addOrder(order)) {
            chef.startCooking(order);
            state.incrementPreparedCount();
        }
    }

    public void notifyOrderCanceled(Order order) {
        state.decrementWaitingCount();
        state.incrementEarlyCanceledCount();
    }

    public void notifyPreparationDone(Order order) {
        paymentService.receiveOrderForPayment(order);
        state.decrementPreparedCount();
    }

    public List<Order> getOrders() {
        return preparationArea.getOrders();
    }

    public void notifyPaymentApproved(Order order) {
        waitress.startDelivery(order);
        state.incrementInDelivery();
        state.decrementWaitingCount();
    }

    public void notifyPaymentFailed(Order order) {
        state.decrementWaitingCount();
        state.incrementPaymentFailedCount();
    }

    public void notifyDeliveryDelayed(Order order) {
        state.incrementDelayed();
    }

    public void notifyDeliveryCompleted(Order order) {
        state.decrementInDelivery();
        state.incrementDelivered();
        state.addRevenue(order.getPrice());
    }

    public void start() {
        System.out.println("=== Restaurant Simulation Starting ===");
        timer.start();
        System.out.println("=== Restaurant Simulation Complete ===");
    }
}
