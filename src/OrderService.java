public class OrderService extends RestaurantComponent {

    public OrderService(Mediator mediator) {
        super(mediator);
    }

    public void createOrder() {
        if (ProbabilityCalculator.shouldGenerateOrder()) { // 65% chance to generate a new order each second
            double price = ProbabilityCalculator.randomPrice();
            int prepTime = ProbabilityCalculator.randomPrepTime();
            int deliveryTime = ProbabilityCalculator.randomDeliveryTime();
            Order order = new Order(price, prepTime, deliveryTime);
            System.out.println("[OrderService] New order created: Order #" + order.getId() +
                    " (Price: " + String.format("%.2f", price) +
                    ", Prep: " + prepTime + "s, Delivery: " + deliveryTime + "s)");
            mediator.notify(this, "ORDER_CREATED", order);
        }
    }

    @Override
    public String getName() {
        return "OrderService";
    }
}
