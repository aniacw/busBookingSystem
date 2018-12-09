package main;

public class FareCalculator {

    private static double oneKmCharge;
    private  int distance;
    private static   double fare;

    public FareCalculator(double oneKmCharge, int distance) {
        this.oneKmCharge = oneKmCharge;
        this.distance = distance;
    }

    public static double calculateFare(double oneKmCharge, int distance){
        return fare = oneKmCharge * distance;
    }

    public static double getOneKmCharge() {
        return oneKmCharge;
    }

    public static void setOneKmCharge(double oneKmCharge) {
        FareCalculator.oneKmCharge = oneKmCharge;
    }

    public int getDistance() {
        return distance;
    }

    public double getFare() {
        return fare;
    }

}
