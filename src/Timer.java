public class Timer extends RestaurantComponent {
    private int totalTicks;
    private int currentTick;

    public Timer(Restaurant mediator, int totalTicks) {
        super(mediator);
        this.totalTicks = totalTicks;
    }

    public void start() {
        for (int tick = 1; tick <= totalTicks; tick++) {
            System.out.println("\n>>> TICK " + tick + " <<<");
            currentTick = tick;
            mediator.onTick();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
        System.out.println("\n>>> SIMULATION ENDED <<<");
    }

    public int getCurrentTick() {
        return currentTick;
    }

    @Override
    public String getName() {
        return "Timer";
    }
}
