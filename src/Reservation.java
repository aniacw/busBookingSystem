import java.sql.Time;
import java.util.Calendar;
import java.util.Date;

public class Reservation {

    private String destination;
    private String departure;
    private Date date;
    private Time time;
    private static int lastReservationNUmber = 0;
    private int reservationNumber;
    private Date reservationDate;

    public Reservation(String destination, String departure, Date date, Time time) {
        this.destination = destination;
        this.departure = departure;
        this.date = date;
        this.time = time;
        reservationNumber = lastReservationNUmber++;
        reservationDate = Calendar.getInstance().getTime();
    }
}
