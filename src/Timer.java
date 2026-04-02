public class Timer extends RestaurantComponent {
    private int totalTicks;

    public Timer(Mediator mediator, int totalTicks) {
        super(mediator);
        this.totalTicks = totalTicks;
    }

    public void start() {
        for (int tick = 1; tick <= totalTicks; tick++) {
            System.out.println("\n>>> SECOND " + tick + " <<<");
            mediator.notify(this, "TICK", null);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
        System.out.println("\n>>> SIMULATION ENDED <<<");
    }

    @Override
    public String getName() {
        return "Timer";
    }
}
