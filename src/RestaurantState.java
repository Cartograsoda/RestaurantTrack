
public class RestaurantState {

    private int totalCreated;
    private int delayedCount;
    private int deliveredCount;
    private double totalRevenue;
    private int earlyCanceledCount;
    private int paymentFailedCount;

    public RestaurantState() {
        this.totalCreated = 0;
        this.delayedCount = 0;
        this.deliveredCount = 0;
        this.totalRevenue = 0.0;
        this.earlyCanceledCount = 0;
        this.paymentFailedCount = 0;
    }

    public void incrementEarlyCanceled() {
        earlyCanceledCount++;
    }

    public void incrementPaymentFailed() {
        paymentFailedCount++;
    }

    public void incrementCreated() {
        totalCreated++;
    }

    public void incrementDelayed() {
        delayedCount++;
    }

    public void incrementDelivered() {
        deliveredCount++;
    }

    public void addRevenue(double amount) {
        totalRevenue += amount;
    }

    public int getTotalCreated() {
        return totalCreated;
    }

    public int getCanceledCount() {
        return earlyCanceledCount + paymentFailedCount;
    }

    public int getDelayedCount() {
        return delayedCount;
    }

    public int getDeliveredCount() {
        return deliveredCount;
    }

    public double getTotalRevenue() {
        return totalRevenue;
    }

    public int getEarlyCanceledCount() {
        return earlyCanceledCount;
    }

    public int getPaymentFailedCount() {
        return paymentFailedCount;
    }
}
