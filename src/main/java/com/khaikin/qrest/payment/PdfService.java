package com.khaikin.qrest.payment;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.khaikin.qrest.order.Order;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@Service
public class PdfService {

    public byte[] generateInvoice(Order order, Payment payment) throws DocumentException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Document document = new Document();
        PdfWriter.getInstance(document, out);
        document.open();

        // Add header
        Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
        Paragraph header = new Paragraph("Restaurant Invoice", headerFont);
        header.setAlignment(Element.ALIGN_CENTER);
        document.add(header);
        document.add(new Paragraph("\n"));

        // Add order details
        document.add(new Paragraph("Order ID: " + order.getId()));
        document.add(new Paragraph("Date: " + payment.getPaymentTime().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)));
        document.add(new Paragraph("Table: " + Objects.requireNonNull(
                order.getTableOrders().get(0).getRestaurantTable().getName())));
        document.add(new Paragraph("\n"));

        // Create items table
        PdfPTable table = new PdfPTable(3);
        table.setWidthPercentage(100);
        
        // Add table headers
        String[] headers = {"Item", "Quantity", "Price"};
        for (String headerText : headers) {
            PdfPCell cell = new PdfPCell(new Phrase(headerText));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
            table.addCell(cell);
        }

        // Add food items
        order.getFoodOrders().forEach(foodOrder -> {
            table.addCell(foodOrder.getFood().getName());
            table.addCell(String.valueOf(foodOrder.getQuantity()));
            table.addCell(String.valueOf(foodOrder.getPrice()));
        });

        // Add combo items
        order.getComboOrders().forEach(comboOrder -> {
            table.addCell(comboOrder.getCombo().getName());
            table.addCell(String.valueOf(comboOrder.getQuantity()));
            table.addCell(String.valueOf(comboOrder.getPrice()));
        });

        document.add(table);
        document.add(new Paragraph("\n"));

        // Add total
        document.add(new Paragraph("Total Amount: " + payment.getTotalPrice()));
        document.add(new Paragraph("Payment Method: " + payment.getPaymentMethod()));
        
        document.close();
        return out.toByteArray();
    }
} 