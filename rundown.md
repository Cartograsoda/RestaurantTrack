Final Class & Interface Design

  Interfaces

  Mediator
  interface Mediator {
      void notify(RestaurantComponent sender, String event, Order order);
      void start(); // kicks off the simulation via Timer
  }

  Abstract Classes

  RestaurantComponent
  abstract class RestaurantComponent {
      protected Mediator mediator;

      RestaurantComponent(Mediator mediator);
      void setMediator(Mediator mediator);
      abstract String getName();
  }

  Enums

  OrderState — RECEIVED, IN_PREPARATION, PREPARATION_COMPLETED, IN_DELIVERY, DELIVERED, CANCELED

  PaymentState — PENDING, APPROVED, FAILED

  DeliveryState — WAITING, IN_TRANSIT, DELAYED, DELIVERED

  Core Classes

  Order (plain data, no mediator reference)
  - id: int
  - price: double
  - orderState: OrderState
  - paymentState: PaymentState
  - deliveryState: DeliveryState
  - preparationTime: int        // random 1-4
  - deliveryTime: int           // random 1-4
  - remainingPrepTime: int      // countdown
  - remainingDeliveryTime: int  // countdown
  - delayed: boolean

  Restaurant implements Mediator
  - components registered internally
  - notify() routes events:
      "ORDER_CREATED"       → forwards to PreparationArea
      "ORDER_CANCELED"      → updates RestaurantState
      "PREPARATION_DONE"    → forwards to PaymentService
      "PAYMENT_APPROVED"    → forwards to Waitress
      "PAYMENT_FAILED"      → updates RestaurantState (cancel)
      "DELIVERY_COMPLETE"   → updates RestaurantState (revenue)
      "DELIVERY_DELAYED"    → updates RestaurantState (delayed count)
  - start() → tells Timer to begin ticking

  Components (all extend RestaurantComponent)

  OrderService — generates orders with 65% probability each tick. Creates Order with random price (50-200) and random
  prepTime (1-4). Notifies mediator "ORDER_CREATED".

  CancellationService — each tick, checks orders in RECEIVED state with 8% cancel probability. Notifies mediator
  "ORDER_CANCELED".

  PreparationArea — container holding active orders. Uses OrderCollection<T extends Order> (generic collection —
  satisfies the generics requirement). Adds/removes orders, can query by state.

  Chef — each tick, decrements remainingPrepTime for all IN_PREPARATION orders. When an order hits 0, notifies mediator
  "PREPARATION_DONE".

  PaymentService — processes payment for PREPARATION_COMPLETED orders. 10% fail → sets paymentState = FAILED, notifies
  "PAYMENT_FAILED". 90% success → paymentState = APPROVED, notifies "PAYMENT_APPROVED".

  Waitress — each tick, decrements remainingDeliveryTime for all IN_DELIVERY orders. On entry: 15% chance sets delayed =
   true and adds +2 to delivery time. When delivery hits 0, notifies "DELIVERY_COMPLETE".

  Display — every 2 ticks, mediator pushes a snapshot to Display. Prints all required stats.

  Timer — drives the simulation loop. Each second: notifies mediator "TICK". Mediator then orchestrates the tick order.
  Runs for 10 seconds.

  Utility / Support

  RestaurantState (renamed from StateOfRestaurant — cleaner)
  - totalCreated: int
  - canceledCount: int
  - delayedCount: int
  - deliveredCount: int
  - totalRevenue: double
  + methods to increment each, getters for Display

  ProbabilityCalculator (static utility)
  + shouldGenerateOrder(): boolean     // 65%
  + shouldCancelEarly(): boolean       // 8%
  + paymentFails(): boolean            // 10%
  + isDeliveryDelayed(): boolean       // 15%
  + randomPrepTime(): int              // 1-4
  + randomDeliveryTime(): int          // 1-4
  + randomPrice(): double              // 50-200

  OrderCollection<T extends Order> (generic container — satisfies generics requirement)
  - orders: List<T>
  + add(T order): void
  + remove(T order): void
  + getByState(OrderState state): List<T>
  + size(): int

  Exceptions

  PaymentFailedException extends Exception — thrown when payment fails (10%)

  InvalidOrderStateException extends RuntimeException — thrown on illegal state transitions

  Entry Point

  Main — creates Restaurant, registers all components, calls restaurant.start()

  ---
  Tick Order (each second inside Mediator):

  1. OrderService     → maybe creates order (65%)
  2. CancellationService → maybe cancels RECEIVED orders (8%)
  3. Chef             → progresses preparation countdowns
  4. PaymentService   → processes PREPARATION_COMPLETED orders
  5. Waitress         → progresses delivery countdowns
  6. Display          → prints status (every 2nd tick only)

  ---