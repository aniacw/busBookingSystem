package main;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class TicketCreator {

    Document ticket = new Document();
    public static String newline = System.getProperty("line.separator");

    public void createTicket(int bookingId, String name, String date, String time, double price, String seats) {
        try {
            PdfWriter.getInstance(ticket, new FileOutputStream("C:\\Users\\Ania\\Desktop\\Ticket.pdf"));
            ticket.open();
            Font fontLogo = FontFactory.getFont(FontFactory.TIMES, 18, BaseColor.BLUE);
            Font font = FontFactory.getFont(FontFactory.TIMES, 14, BaseColor.BLACK);
            Chunk logo = new Chunk("DREAM BUS", fontLogo);

            StringBuilder ticketBody = new StringBuilder();
            ticketBody
                    .append("Booking ID: ")
                    .append(bookingId)
                    .append(newline)
                    .append(" Name: ")
                    .append(name)
                    .append("\n Date and time of  departure: ")
                    .append(date)
                    .append(", ")
                    .append(time)
                    .append(" Seat(s): ")
                    .append(seats)
                    .append("\r\n Price: ")
                    .append(price)
                    .append("\r\n Have a great trip with Dream Buses!");

            Chunk body = new Chunk(ticketBody.toString(), font);

            ticket.add(logo);
            ticket.add(body);
            ticket.close();
        } catch (DocumentException | FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
