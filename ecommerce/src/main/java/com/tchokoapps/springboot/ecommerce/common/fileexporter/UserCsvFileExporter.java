package com.tchokoapps.springboot.ecommerce.common.fileexporter;

import com.tchokoapps.springboot.ecommerce.backend.entity.User;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.Objects;

@Component
public class UserCsvFileExporter implements CsvFileExporter<User> {
    @Override
    public void exportToCsv(List<User> users, Writer writer) throws IOException {
        Objects.requireNonNull(users, "users cannot be NULL");
        Objects.requireNonNull(writer, "writer cannot be NULL");
        CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT);
        csvPrinter.printRecord("ID", "E-Mail", "First Name", "Last Name", "Roles", "Enabled");
        for (User user : users) {
            csvPrinter.printRecord(user.getId(), user.getEmail(), user.getFirstName(), user.getLastName(), user.isEnabled());
        }
    }
}
