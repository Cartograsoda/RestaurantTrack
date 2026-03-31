public abstract class RestaurantComponent {
    protected Mediator mediator;

    public RestaurantComponent(Mediator mediator) {
        this.mediator = mediator;
    }

    public void setMediator(Mediator mediator) {
        this.mediator = mediator;
    }

    public abstract String getName();
}
