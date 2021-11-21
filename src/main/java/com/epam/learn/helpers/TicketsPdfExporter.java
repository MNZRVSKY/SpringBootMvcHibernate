package com.epam.learn.helpers;

import com.epam.learn.storage.EventEntity;
import com.epam.learn.storage.TicketEntity;
import com.epam.learn.storage.UserEntity;
import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.*;

import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TicketsPdfExporter {

    private List<TicketEntity> tickets;
    private List<UserEntity> users;
    private List<EventEntity> events;

    public TicketsPdfExporter(List<UserEntity> users, List<TicketEntity> tickets, List<EventEntity> events) {
        this.users = users;
        this.tickets = tickets;
        this.events = events;
    }

    private void writeTableHeader(PdfPTable table) {

        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(Color.BLUE);
        cell.setPadding(5);

        Font font = FontFactory.getFont(FontFactory.HELVETICA);
        font.setColor(Color.WHITE);

        cell.setPhrase(new Phrase("ID", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Event", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("User", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Email", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Category", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Place", font));
        table.addCell(cell);
    }

    private void writeTableData(PdfPTable table) {

        Map<Integer, String> userNames = users.stream().collect(Collectors.toMap(UserEntity::getId, UserEntity::getName) );
        Map<Integer, String> userEmails = users.stream().collect(Collectors.toMap(UserEntity::getId, UserEntity::getEmail) );
        Map<Integer, String> eventTitles = events.stream().collect(Collectors.toMap(EventEntity::getId, EventEntity::getTitle) );

        for (TicketEntity ticket : tickets) {
            table.addCell(String.valueOf(ticket.getId()));
            table.addCell(eventTitles.get(ticket.getEvent().getId()));
            table.addCell(userNames.get(ticket.getUser().getId()));
            table.addCell(userEmails.get(ticket.getUser().getId()));
            table.addCell(ticket.getCategory().toString());
            table.addCell(String.valueOf(ticket.getPlace()));
        }
    }

    public void export(HttpServletResponse response) throws DocumentException, IOException {

        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();
        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        font.setSize(18);
        font.setColor(Color.BLUE);

        Paragraph p = new Paragraph("List of tickets", font);
        p.setAlignment(Paragraph.ALIGN_CENTER);

        document.add(p);

        PdfPTable table = new PdfPTable(6);
        table.setWidthPercentage(100f);
        table.setWidths(new float[] {0.7f, 3.5f, 3.0f, 3.5f, 3.0f, 1.2f});
        table.setSpacingBefore(10);

        writeTableHeader(table);
        writeTableData(table);

        document.add(table);

        document.close();
    }
}