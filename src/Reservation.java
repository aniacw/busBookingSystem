import java.sql.Time;
import java.util.Date;

public class Reservation {

    private String destination;
    private String departure;
    private Date date;
    private Time time;
    private int reservationNumber;

    public Reservation(String destination, String departure, Date date, Time time) {
        this.destination = destination;
        this.departure = departure;
        this.date = date;
        this.time = time;
        //reservationNumber
    }
}
