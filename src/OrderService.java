public class OrderService extends RestaurantComponent {

    public OrderService(Restaurant mediator) {
        super(mediator);
    }

    public void createOrder() {
        if (ProbabilityCalculator.shouldGenerateOrder()) {
            double price = ProbabilityCalculator.randomPrice();
            int prepTime = ProbabilityCalculator.randomPrepTime();
            int deliveryTime = ProbabilityCalculator.randomDeliveryTime();
            Order order = new Order(price, prepTime, deliveryTime);
            System.out.println("[OrderService] New order created: Order #" + order.getId() +
                    " (Price: " + String.format("%.2f", price) +
                    ", Prep: " + prepTime + "s, Delivery: " + deliveryTime + "s)");

            mediator.notifyOrderCreated(order);
        }
    }

    @Override
    public String getName() {
        return "OrderService";
    }
}
