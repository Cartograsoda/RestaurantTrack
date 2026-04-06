import java.util.List;

public class CancellationService extends RestaurantComponent {
    public CancellationService(Restaurant mediator) {
        super(mediator);
    }

    public void checkCancellation(Order order) {
        if (ProbabilityCalculator.shouldCancelEarly()) {
            System.out.println("[CancellationService] Order #" + order.getId() + " canceled early.");
            order.setOrderState(OrderState.CANCELED);
            mediator.notifyOrderCanceled(order);
        }
    }

    @Override
    public String getName() {
        return "CancellationService";
    }
}
