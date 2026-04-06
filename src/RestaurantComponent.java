public abstract class RestaurantComponent {
    protected Restaurant mediator;

    public RestaurantComponent(Restaurant mediator) {
        this.mediator = mediator;
    }

    public void setMediator(Restaurant mediator) {
        this.mediator = mediator;
    }

    public abstract String getName();
}
