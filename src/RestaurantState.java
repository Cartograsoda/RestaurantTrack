public class RestaurantState {
    private int totalCreated;
    private int delayedCount;
    private int deliveredCount;
    private double totalRevenue;

    private int preparedCount;
    private int waitingCount;
    private int inDelivery;
    private int earlyCanceledCount;
    private int paymentFailedCount;


    public RestaurantState() {
        this.totalCreated = 0;
        this.delayedCount = 0;
        this.deliveredCount = 0;
        this.totalRevenue = 0.0;

        this.preparedCount = 0;
        this.waitingCount = 0;
        this.inDelivery = 0;
    }

    public void incrementCreated() { totalCreated++; }
    public void incrementDelayed() { delayedCount++; }
    public void incrementDelivered() { deliveredCount++; }
    public void addRevenue(double amount) { totalRevenue += amount; }

    public int getTotalCreated() { return totalCreated; }
    public int getCanceledCount() { return earlyCanceledCount + paymentFailedCount; }
    public int getDelayedCount() { return delayedCount; }
    public int getDeliveredCount() { return deliveredCount; }
    public double getTotalRevenue() { return totalRevenue; }

    public int getPreparedCount() { return preparedCount; }
    public int getWaitingCount() { return waitingCount; }
    public int getInDelivery() { return inDelivery; }
    public int getEarlyCanceledCount() { return this.earlyCanceledCount; }
    public int getPaymentFailedCount() { return this.paymentFailedCount; }

    public void incrementPreparedCount() { this.preparedCount++; }
    public void incrementWaitingCount() { this.waitingCount++; }
    public void incrementInDelivery() { this.inDelivery++; }
    public void decrementPreparedCount() { this.preparedCount--; }
    public void decrementWaitingCount() { this.waitingCount--; }
    public void decrementInDelivery() { this.inDelivery--; }

    public void incrementEarlyCanceledCount() { this.earlyCanceledCount++; }
    public void incrementPaymentFailedCount() { this.paymentFailedCount++; }
}
