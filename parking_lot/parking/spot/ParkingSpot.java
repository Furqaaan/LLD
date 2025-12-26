package parking_lot.parking.spot;

import parking_lot.vehicle.Vehicle;
import parking_lot.enums.VehicleType;

public abstract class ParkingSpot {
    private int spotNumber;
    private boolean isOccupied;
    private Vehicle vehicle;
    private VehicleType spotType;

    public ParkingSpot(int spotNumber, VehicleType spotType) {
        this.spotNumber = spotNumber;
        this.spotType = spotType;
        this.isOccupied = false;
    }

    public boolean isOccupied() {
        return isOccupied;
    }

    public boolean canPark(Vehicle vehicle) {
        return !isOccupied() && vehicle.getType().equals(spotType);
    }

    public void park(Vehicle vehicle) {
        if (isOccupied()) {
            throw new IllegalArgumentException("Spot is already occupied");
        }

        if (!canPark(vehicle)) {
            throw new IllegalArgumentException("Vehicle cannot park in this spot");
        }

        setVehicle(vehicle);
    }

    public void vacate() {
        if (!isOccupied()) {
            throw new IllegalArgumentException("Spot is not occupied");
        }

        clearVehicle();
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
        this.isOccupied = true;
    }

    public void clearVehicle() {
        this.vehicle = null;
        this.isOccupied = false;
    }

    public void setOccupied(boolean isOccupied) {
        this.isOccupied = isOccupied;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public VehicleType getSpotType() {
        return spotType;
    }

    public int getSpotNumber() {
        return spotNumber;
    }

}
