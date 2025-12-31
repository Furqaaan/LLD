package car_rental.user;

import java.util.List;
import java.util.ArrayList;
import car_rental.reservation.Reservation;

public class User {
    private int id;
    private String name;
    private String email;
    private List<Reservation> reservations;

    public User(int id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.reservations = new ArrayList<>();
    }

    public void addReservation(Reservation reservation) {
        reservations.add(reservation);
    }

    public void deleteReservation(Reservation reservation) {
        reservations.remove(reservation);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}
