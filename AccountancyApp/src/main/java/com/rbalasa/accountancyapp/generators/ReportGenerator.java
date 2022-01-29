package com.rbalasa.accountancyapp.generators;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.rbalasa.accountancyapp.model.Account;
import com.rbalasa.accountancyapp.model.Transaction;
import com.rbalasa.accountancyapp.model.User;
import com.rbalasa.accountancyapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ReportGenerator {

    private final UserService userService;
    private int nrCrt = 0;

    @Scheduled(cron = "0/60 * * * * ?")
    public void compute() {
        Date dateNow = new Date();
        Date dateOfLastReport = Date
                .from(LocalDateTime.now()
                        .minusMinutes(1)
                        .atZone(ZoneId.systemDefault())
                        .toInstant());
        List<User> users = userService.findAll();

        for (User user : users) {
            createDocument(user, dateNow, dateOfLastReport);
        }
    }

    private void createDocument(User user, Date dateNow, Date dateLastRun) {
        Document document = new Document();
        Double totalExpenses = 0.0;
        Double totalIncome = 0.0;

        String documentName = "Raport_" + user.getName() + "_" + dateLastRun + "_" + dateNow + ".pdf";
        try
        {
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(documentName));
            document.open();
            document.add(new Paragraph("Raport Contabilitate in perioada " + dateLastRun + " - " + dateNow));
            List<Account> accounts = user.getAccounts();
            for (Account account : accounts) {
                Double totalAccountExpenses = 0.0;
                Double totalAccountIncome = 0.0;

                document.add(new Paragraph("Contul " + account.getAccountNo()));
                document.add(new Paragraph("Cheltuieli"));

                PdfPTable expenseTable = getTableHeader();

                List<Transaction> transactions = account.getTransactions().stream()
                        .filter(t -> t.getDate().after(dateLastRun))
                        .filter(t -> t.getDate().before(dateNow))
                        .collect(Collectors.toList());

                transactions.stream()
                        .filter(Transaction::getExpense)
                        .forEach(t -> addCell(t, expenseTable));
                nrCrt = 0;
                totalAccountExpenses += transactions.stream()
                        .filter(Transaction::getExpense)
                        .mapToDouble(Transaction::getAmount)
                        .sum();
                document.add(expenseTable);
                totalExpenses += totalAccountExpenses;
                document.add(new Paragraph("Total " + totalAccountExpenses));

                document.add(new Paragraph("Venituri "));

                PdfPTable incomeTable = getTableHeader();

                transactions.stream()
                        .filter(t -> !t.getExpense())
                        .forEach(t -> addCell(t, incomeTable));
                nrCrt = 0;
                totalAccountIncome += transactions.stream()
                        .filter(t -> !t.getExpense())
                        .mapToDouble(Transaction::getAmount)
                        .sum();
                totalIncome += totalAccountIncome;


                document.add(incomeTable);
            }

            document.add(new Paragraph("Total venituri " + totalIncome));
            document.add(new Paragraph("Total cheltuieli " + totalExpenses));

            document.close();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addCell(Transaction t, PdfPTable table) {
        nrCrt++;
        PdfPCell cell1 = new PdfPCell(new Paragraph(String.valueOf(nrCrt)));
        cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);

        PdfPCell cell2 = new PdfPCell(new Paragraph(t.getDescription()));
        cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell2.setVerticalAlignment(Element.ALIGN_MIDDLE);

        PdfPCell cell3 = new PdfPCell(new Paragraph(t.getAmount().toString()));
        cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell3.setVerticalAlignment(Element.ALIGN_MIDDLE);

        table.addCell(cell1);
        table.addCell(cell2);
        table.addCell(cell3);
    }

    private PdfPTable getTableHeader() throws DocumentException {
        PdfPTable table = new PdfPTable(3);
        table.setWidthPercentage(100);
        table.setSpacingBefore(10f);
        table.setSpacingAfter(10f);

        //Set Column widths
        float[] columnWidths = {1f, 1f, 1f};
        table.setWidths(columnWidths);

        PdfPCell cell1 = new PdfPCell(new Paragraph("Numar"));
        cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);

        PdfPCell cell2 = new PdfPCell(new Paragraph("Descriere"));
        cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell2.setVerticalAlignment(Element.ALIGN_MIDDLE);

        PdfPCell cell3 = new PdfPCell(new Paragraph("Suma"));
        cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell3.setVerticalAlignment(Element.ALIGN_MIDDLE);

        table.addCell(cell1);
        table.addCell(cell2);
        table.addCell(cell3);
        return table;
    }

}
