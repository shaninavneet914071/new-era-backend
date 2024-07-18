package com.nsh.customerservice.services.implementations;

import com.nsh.customerservice.dtos.CustomerDto;
import com.nsh.customerservice.dtos.address.AddressDto;
import com.nsh.customerservice.services.CustomerService;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

@Service
public class ExcelService {

    @Autowired
    private CustomerService customerService;

    public List<String> saveData(MultipartFile file) {
        if (isCsvFile(file)) {
            return saveCSV(file);
        } else {
            return saveExcelData(file);
        }
    }


    public List<String> saveExcelData(MultipartFile file) {
        try (Workbook workbook = new XSSFWorkbook(file.getInputStream())) {
            List<String> customerResponse = new ArrayList<>();

            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rows = sheet.iterator();

            // Read the header row (assuming the first row is header)
            Row headerRow = rows.next();
            Map<String, Integer> columnIndexes = new HashMap<>();
            for (Cell cell : headerRow) {
                columnIndexes.put(cell.getStringCellValue().trim().toLowerCase(), cell.getColumnIndex());
            }

            // Process data rows
            while (rows.hasNext()) {
                Row row = rows.next();
                CustomerDto customerDto =
                        CustomerDto.builder().email(getStringCellValue(row, columnIndexes, "email")).firstName(getStringCellValue(row, columnIndexes, "firstName"))
                                .lastName(getStringCellValue(row, columnIndexes, "lastName")).password(getStringCellValue(row, columnIndexes, "password"))
                                .address(AddressDto.builder().city(getStringCellValue(row, columnIndexes, "city")).pin(getStringCellValue(row, columnIndexes, "pin"))
                                        .state(getStringCellValue(row, columnIndexes, "state"))
                                        .country(getStringCellValue(row, columnIndexes, "country")).build()).build();
                // Save the person to the database
                customerResponse.add(customerService.addUser(customerDto));
            }
            return customerResponse;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String getStringCellValue(Row row, Map<String, Integer> columnIndexes, String columnName) {
        Integer index = columnIndexes.get(columnName.toLowerCase());
        if (index != null) {
            Cell cell = row.getCell(index);
            if (cell != null) {
                DataFormatter dataFormatter = new DataFormatter();
                String value = dataFormatter.formatCellValue(cell);
                return value;
            }

        }
        return null;
    }

    private double getNumericCellValue(Row row, Map<String, Integer> columnIndexes, String columnName) {
        Integer index = columnIndexes.get(columnName.toLowerCase());
        if (index != null) {
            Cell cell = row.getCell(index);
            if (cell != null) {
                return cell.getNumericCellValue();
            }
        }
        return 0.0;
    }

    private boolean isCsvFile(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        return fileName != null && (fileName.endsWith(".csv") || fileName.endsWith(".CSV"));
    }

    //    ========================================================================
    private List<String> saveCSV(MultipartFile file) {
        List<String> customerResponse = new ArrayList<>();
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(file.getInputStream()));
             CSVParser csvParser = new CSVParser(fileReader, CSVFormat.Builder.create().setSkipHeaderRecord(true).build())) {
            // Iterate over CSV records
            for (CSVRecord csvRecord : csvParser) {
                CustomerDto customerDto = CustomerDto.builder()
                        .email(csvRecord.get("email"))
                        .firstName(csvRecord.get("firstName"))
                        .lastName(csvRecord.get("lastName"))
                        .password(csvRecord.get("password"))
                        .address(AddressDto.builder()
                                .city(csvRecord.get("city"))
                                .pin(csvRecord.get("pin"))
                                .state(csvRecord.get("state"))
                                .country(csvRecord.get("country"))
                                .build())
                        .build();

                // Save the customer to the database
                customerResponse.add(customerService.addUser(customerDto));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return customerResponse;
    }
}
