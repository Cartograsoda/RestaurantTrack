import java.util.Random;

public class ProbabilityCalculator {
    private static final Random random = new Random();

    public static boolean shouldGenerateOrder() {
        return random.nextDouble() < 0.65;
    }

    public static boolean shouldCancelEarly() {
        return random.nextDouble() < 0.08;
    }

    public static boolean paymentFails() {
        return random.nextDouble() < 0.10;
    }

    public static boolean isDeliveryDelayed() {
        return random.nextDouble() < 0.15;
    }

    public static int randomPrepTime() {
        return random.nextInt(4) + 1;
    }

    public static int randomDeliveryTime() {
        return random.nextInt(4) + 1;
    }

    public static double randomPrice() {
        return 50 + (150 * random.nextDouble());
    }
}
