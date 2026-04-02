
import java.util.List;

public class PaymentService extends RestaurantComponent {

    private OrderCollection<Order> orders;

    public PaymentService(Mediator mediator, OrderCollection<Order> orders) {
        super(mediator);
        this.orders = orders;
    }

    public void addPaymentStatus() {
        List<Order> completed = orders.getByState(OrderState.PREPARATION_COMPLETED);
        for (Order order : completed) {
            try {
                processPayment(order);
            } catch (PaymentFailedException e) {
                System.out.println("[PaymentService] " + e.getMessage());
                mediator.notify(this, "PAYMENT_FAILED", order);
            }
        }
    }

    private void processPayment(Order order) throws PaymentFailedException {
        if (ProbabilityCalculator.paymentFails()) {
            throw new PaymentFailedException("Payment failed for Order #" + order.getId());
        }
        System.out.println("[PaymentService] Payment approved for Order #" + order.getId());
        mediator.notify(this, "PAYMENT_APPROVED", order);
    }

    @Override
    public String getName() {
        return "PaymentService";
    }
}
