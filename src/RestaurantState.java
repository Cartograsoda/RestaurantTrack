public class RestaurantState {
    private int totalCreated;
    private int canceledCount;
    private int delayedCount;
    private int deliveredCount;
    private double totalRevenue;

    public RestaurantState() {
        this.totalCreated = 0;
        this.canceledCount = 0;
        this.delayedCount = 0;
        this.deliveredCount = 0;
        this.totalRevenue = 0.0;
    }

    public void incrementCreated() { totalCreated++; }
    public void incrementCanceled() { canceledCount++; }
    public void incrementDelayed() { delayedCount++; }
    public void incrementDelivered() { deliveredCount++; }
    public void addRevenue(double amount) { totalRevenue += amount; }

    public int getTotalCreated() { return totalCreated; }
    public int getCanceledCount() { return canceledCount; }
    public int getDelayedCount() { return delayedCount; }
    public int getDeliveredCount() { return deliveredCount; }
    public double getTotalRevenue() { return totalRevenue; }
}
