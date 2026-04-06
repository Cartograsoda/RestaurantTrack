public class PaymentService extends RestaurantComponent {
    public PaymentService(Restaurant mediator) {
        super(mediator);
    }

    public void receiveOrderForPayment(Order order) {
        try {
            processPayment(order);
        } catch (PaymentFailedException e) {
            System.out.println("[PaymentService] " + e.getMessage());
            order.setPaymentState(PaymentState.FAILED);
            order.setOrderState(OrderState.CANCELED);
            mediator.notifyPaymentFailed(order);
        }
    }

    private void processPayment(Order order) throws PaymentFailedException {
        if (ProbabilityCalculator.paymentFails()) {
            throw new PaymentFailedException("Payment failed for Order #" + order.getId());
        }
        order.setPaymentState(PaymentState.APPROVED);
        System.out.println("[PaymentService] Payment approved for Order #" + order.getId());
        mediator.notifyPaymentApproved(order);
    }

    @Override
    public String getName() {
        return "PaymentService";
    }
}
