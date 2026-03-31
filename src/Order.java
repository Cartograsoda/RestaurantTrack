public class Order {
    private static int idCounter = 0;

    private int id;
    private double price;
    private OrderState orderState;
    private PaymentState paymentState;
    private DeliveryState deliveryState;
    private int preparationTime;
    private int deliveryTime;
    private int remainingPrepTime;
    private int remainingDeliveryTime;
    private boolean delayed;

    public Order(double price, int preparationTime, int deliveryTime) {
        this.id = ++idCounter;
        this.price = price;
        this.orderState = OrderState.RECEIVED;
        this.paymentState = PaymentState.PENDING;
        this.deliveryState = DeliveryState.WAITING;
        this.preparationTime = preparationTime;
        this.deliveryTime = deliveryTime;
        this.remainingPrepTime = preparationTime;
        this.remainingDeliveryTime = deliveryTime;
        this.delayed = false;
    }

    public int getId() { return id; }
    public double getPrice() { return price; }

    public OrderState getOrderState() { return orderState; }
    public void setOrderState(OrderState orderState) { this.orderState = orderState; }

    public PaymentState getPaymentState() { return paymentState; }
    public void setPaymentState(PaymentState paymentState) { this.paymentState = paymentState; }

    public DeliveryState getDeliveryState() { return deliveryState; }
    public void setDeliveryState(DeliveryState deliveryState) { this.deliveryState = deliveryState; }

    public int getPreparationTime() { return preparationTime; }
    public int getDeliveryTime() { return deliveryTime; }

    public int getRemainingPrepTime() { return remainingPrepTime; }
    public void setRemainingPrepTime(int remainingPrepTime) { this.remainingPrepTime = remainingPrepTime; }

    public int getRemainingDeliveryTime() { return remainingDeliveryTime; }
    public void setRemainingDeliveryTime(int remainingDeliveryTime) { this.remainingDeliveryTime = remainingDeliveryTime; }

    public boolean isDelayed() { return delayed; }
    public void setDelayed(boolean delayed) { this.delayed = delayed; }

    @Override
    public String toString() {
        return "Order #" + id + " [" + orderState + ", Payment: " + paymentState +
               ", Delivery: " + deliveryState + ", Price: " + String.format("%.2f", price) + "]";
    }
}
