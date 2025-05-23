package com.khaikin.qrest.payment;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.khaikin.qrest.combo.Combo;
import com.khaikin.qrest.comboorder.ComboOrder;
import com.khaikin.qrest.food.Food;
import com.khaikin.qrest.foodorder.FoodOrder;
import com.khaikin.qrest.order.Order;
import com.khaikin.qrest.tableorder.TableOrder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.nio.file.Path;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class PdfService {

    @Autowired
    private Path invoiceStoragePath;
    
    @Autowired
    private String baseInvoiceUrl;

    /**
     * Tạo và lưu hóa đơn PDF từ thông tin đơn hàng và thanh toán
     * @param order Thông tin đơn hàng
     * @param payment Thông tin thanh toán
     * @return Đường dẫn tới file PDF đã tạo
     * @throws Exception Nếu có lỗi khi tạo PDF
     */
    public String generateAndSaveInvoice(Order order, Payment payment) throws Exception {
        List<TableOrder> tableOrders = order.getTableOrders();
        List<String> tableName = new ArrayList<>();
        for (TableOrder it : tableOrders) {
            tableName.add(it.getRestaurantTable().getName());
        }
        Document document = new Document();
        
        // Tạo tên file với ngày giờ
        String dateStr = payment.getPaymentTime().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        String fileName = "invoice_" + payment.getId() + "_" + dateStr + ".pdf";
        Path filePath = invoiceStoragePath.resolve(fileName);
        
        PdfWriter.getInstance(document, new FileOutputStream(filePath.toFile()));
        document.open();
        
        // Tiêu đề hóa đơn
        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, BaseColor.BLACK);
        Paragraph title = new Paragraph("PAYMENT INVOICE", titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);
        document.add(Chunk.NEWLINE);
        
        // Thông tin đơn hàng
        document.add(new Paragraph("Payment ID: " + payment.getId()));
        document.add(new Paragraph("Order ID: " + order.getId()));
        document.add(new Paragraph("Time: " + payment.getPaymentTime().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"))));
        document.add(new Paragraph("Tables: " + tableName));
        document.add(new Paragraph("Payment method: " + payment.getPaymentMethod()));
        document.add(Chunk.NEWLINE);
        
        // Danh sách món ăn
        document.add(new Paragraph("FOOD DETAILS", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14)));
        document.add(Chunk.NEWLINE);
        
        // Bảng món ăn đơn lẻ
        if (!order.getFoodOrders().isEmpty()) {
            document.add(new Paragraph("FOOD ITEMS:\n"));
            PdfPTable foodTable = new PdfPTable(4); // 4 cột
            foodTable.setWidthPercentage(100);
            
            // Tiêu đề bảng
            List<String> headers = Arrays.asList("Food name", "Quantity", "Price", "Total price");
            for (String headerText : headers) {
                PdfPCell cell = new PdfPCell(new Phrase(headerText));
                cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
                foodTable.addCell(cell);
            }
            
            // Dữ liệu bảng
            for (FoodOrder foodOrder : order.getFoodOrders()) {
                Food food = foodOrder.getFood();
                foodTable.addCell(food.getName());
                foodTable.addCell(String.valueOf(foodOrder.getQuantity()));
                foodTable.addCell(String.format("%.2f", food.getPrice()) + "$");
                foodTable.addCell(String.format("%.2f", foodOrder.getPrice()) + "$");
            }
            
            document.add(foodTable);
            document.add(Chunk.NEWLINE);
        }
        
        // Bảng combo
        if (!order.getComboOrders().isEmpty()) {
            document.add(new Paragraph("COMBO:\n"));
            PdfPTable comboTable = new PdfPTable(4); // 4 cột
            comboTable.setWidthPercentage(100);
            
            // Tiêu đề bảng
            List<String> headers = Arrays.asList("Combo name", "Quantity", "Price", "Total price");
            for (String headerText : headers) {
                PdfPCell cell = new PdfPCell(new Phrase(headerText));
                cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
                comboTable.addCell(cell);
            }
            
            // Dữ liệu bảng
            for (ComboOrder comboOrder : order.getComboOrders()) {
                Combo combo = comboOrder.getCombo();
                comboTable.addCell(combo.getName());
                comboTable.addCell(String.valueOf(comboOrder.getQuantity()));
                comboTable.addCell(String.format("%.2f", combo.getPrice()) + "$");
                comboTable.addCell(String.format("%.2f", comboOrder.getPrice()) + "$");
            }
            
            document.add(comboTable);
            document.add(Chunk.NEWLINE);
        }
        
        // Tổng tiền
        document.add(new Paragraph("Total price: " + String.format("%.2f", order.getTotalPrice()) + "$", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14)));
        
        document.close();
        
        // Trả về URL tương đối tới file
        String invoiceUrl = baseInvoiceUrl + "/" + fileName;
        return invoiceUrl;
    }
    
    /**
     * Tạo hóa đơn PDF từ thông tin đơn hàng và thanh toán và trả về mảng byte
     * @param order Thông tin đơn hàng
     * @param payment Thông tin thanh toán
     * @return Mảng byte chứa nội dung PDF
     * @throws Exception Nếu có lỗi khi tạo PDF
     */
    public byte[] generateInvoice(Order order, Payment payment) throws Exception {
        List<TableOrder> tableOrders = order.getTableOrders();
        List<String> tableName = new ArrayList<>();
        for (TableOrder it : tableOrders) {
            tableName.add(it.getRestaurantTable().getName());
        }
        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        
        PdfWriter.getInstance(document, out);
        document.open();
        
        // Tiêu đề hóa đơn
        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, BaseColor.BLACK);
        Paragraph title = new Paragraph("PAYMENT INVOICE", titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);
        document.add(Chunk.NEWLINE);
        
        // Thông tin đơn hàng
        document.add(new Paragraph("Payment ID: " + payment.getId()));
        document.add(new Paragraph("Order ID: " + order.getId()));
        document.add(new Paragraph("Time: " + payment.getPaymentTime().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"))));
        document.add(new Paragraph("Tables: " + tableName));
        document.add(new Paragraph("Payment method: " + payment.getPaymentMethod()));
        document.add(Chunk.NEWLINE);
        
        // Danh sách món ăn
        document.add(new Paragraph("FOOD DETAILS", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14)));
        document.add(Chunk.NEWLINE);
        
        // Bảng món ăn đơn lẻ
        if (!order.getFoodOrders().isEmpty()) {
            document.add(new Paragraph("FOOD ITEMS:\n"));
            PdfPTable foodTable = new PdfPTable(4); // 4 cột
            foodTable.setWidthPercentage(100);
            
            // Tiêu đề bảng
            List<String> headers = Arrays.asList("Food name", "Quantity", "Price", "Total price");
            for (String headerText : headers) {
                PdfPCell cell = new PdfPCell(new Phrase(headerText));
                cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
                foodTable.addCell(cell);
            }
            
            // Dữ liệu bảng
            for (FoodOrder foodOrder : order.getFoodOrders()) {
                Food food = foodOrder.getFood();
                foodTable.addCell(food.getName());
                foodTable.addCell(String.valueOf(foodOrder.getQuantity()));
                foodTable.addCell(String.format("%.2f", food.getPrice()) + "$");
                foodTable.addCell(String.format("%.2f", foodOrder.getPrice()) + "$");
            }
            
            document.add(foodTable);
            document.add(Chunk.NEWLINE);
        }
        
        // Bảng combo
        if (!order.getComboOrders().isEmpty()) {
            document.add(new Paragraph("COMBO:\n"));
            PdfPTable comboTable = new PdfPTable(4); // 4 cột
            comboTable.setWidthPercentage(100);
            
            // Tiêu đề bảng
            List<String> headers = Arrays.asList("Combo name", "Quantity", "Price", "Total price");
            for (String headerText : headers) {
                PdfPCell cell = new PdfPCell(new Phrase(headerText));
                cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
                comboTable.addCell(cell);
            }
            
            // Dữ liệu bảng
            for (ComboOrder comboOrder : order.getComboOrders()) {
                Combo combo = comboOrder.getCombo();
                comboTable.addCell(combo.getName());
                comboTable.addCell(String.valueOf(comboOrder.getQuantity()));
                comboTable.addCell(String.format("%.2f", combo.getPrice()) + "$");
                comboTable.addCell(String.format("%.2f", comboOrder.getPrice()) + "$");
            }
            
            document.add(comboTable);
            document.add(Chunk.NEWLINE);
        }
        
        // Tổng tiền
        document.add(new Paragraph("Total price: " + String.format("%.2f", order.getTotalPrice()) + "$", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14)));
        
        document.close();
        
        return out.toByteArray();
    }
} 