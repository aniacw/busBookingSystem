import java.util.ArrayList;
import java.util.List;

public class DataBase {

    private List<Ticket> tickets;
    private List<Bus> buses;
    private List<User> users;
    private List<Reservation> reservations;

    public DataBase() {
        tickets = new ArrayList<>();
        buses = new ArrayList<>();
        users = new ArrayList<>();
        reservations = new ArrayList<>();
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public List<Bus> getBuses() {
        return buses;
    }

    public List<User> getUsers() {
        return users;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }
}
