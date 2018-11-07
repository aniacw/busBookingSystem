package main;

public class FareCalculator {

    private double oneKmCharge;
    private int distance;
    private double fare;

    public FareCalculator(double oneKmCharge, int distance) {
        this.oneKmCharge = oneKmCharge;
        this.distance = distance;
    }

    public double calculateFare(double oneKmCharge, int distance){
        return fare = oneKmCharge * distance;  //mozna tak?
    }
}
