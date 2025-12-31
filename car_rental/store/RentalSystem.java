package car_rental.store;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import car_rental.user.User;
import car_rental.vehicle.VehicleFactory;
import car_rental.reservation.ReservationManager;
import car_rental.payment.PaymentProcessor;
import car_rental.reservation.Reservation;
import car_rental.vehicle.Vehicle;
import java.util.Date;
import car_rental.payment.PaymentStrategy;
import car_rental.pricing.PricingStrategy;

public class RentalSystem {
    private static RentalSystem instance;
    private List<RentalStore> stores;
    private VehicleFactory vehicleFactory;
    private ReservationManager reservationManager;
    private PaymentProcessor paymentProcessor;
    private Map<Integer, User> users;
    private int nextUserId;

    private RentalSystem() {
        this.stores = new ArrayList<>();
        this.vehicleFactory = new VehicleFactory();
        this.reservationManager = new ReservationManager();
        this.paymentProcessor = new PaymentProcessor();
        this.users = new HashMap<>();
        this.nextUserId = 1;
    }

    public static synchronized RentalSystem getInstance() {
        if (instance == null) {
            instance = new RentalSystem();
        }
        return instance;
    }

    public void addStore(RentalStore store) {
        stores.add(store);
    }

    public RentalStore getStore(int storeId) {
        for (RentalStore store : stores) {
            if (store.getId() == storeId) {
                return store;
            }
        }
        return null;
    }

    public List<RentalStore> getStores() {
        return stores;
    }

    public User getUser(int userId) {
        return users.get(userId);
    }

    public Reservation createReservation(int userId, String vehicleRegistration,
        int pickupStoreId, int returnStoreId, Date startDate, Date endDate, PricingStrategy pricingStrategy) {
        User user = users.get(userId);
        RentalStore pickupStore = getStore(pickupStoreId);
        RentalStore returnStore = getStore(returnStoreId);
        Vehicle vehicle = (pickupStore != null) ? pickupStore.getVehicle(vehicleRegistration): null;

        if (user != null && pickupStore != null && returnStore != null && vehicle != null) {
            return reservationManager.createReservation(
                user, vehicle, pickupStore, returnStore, startDate, endDate, pricingStrategy);
        }
        return null;
    }

    public boolean processPayment(
        int reservationId, PaymentStrategy paymentStrategy) {
        Reservation reservation =
            reservationManager.getReservation(reservationId);
        if (reservation != null) {
            boolean result = paymentProcessor.processPayment(
                reservation.getTotalAmount(), paymentStrategy);
            if (result) {
                reservationManager.confirmReservation(reservationId);
                return true;
            }
        }
        return false;
    }

    public void startRental(int reservationId) {
        reservationManager.startRental(reservationId);
    }

    public void completeRental(int reservationId) {
        reservationManager.completeRental(reservationId);
    }

    public void cancelReservation(int reservationId) {
        reservationManager.cancelReservation(reservationId);
    }

    public void registerUser(User user){
        int userID = user.getId();
        if(users.containsKey(userID)){
            System.out.println("User with id : " + userID + "Already exists in the system");
            return;
        }
        users.put(userID , user);
    }
}
