public interface Mediator {
    void notify(RestaurantComponent sender, String event, Order order);
    void start();
}
