import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class ExcelExporter {

    /**
     * Exports the subject plan to an Excel file.
     *
     * @param filePath    Path to save the Excel file
     * @param subjectPlan List of semesters, each containing a list of subjects
     * @param studentName Name of the student
     * @throws IOException if an error occurs while creating the file
     */
    public static void exportSubjectPlan(String filePath, List<List<String>> subjectPlan, String studentName) throws IOException {
        Workbook workbook = new XSSFWorkbook(); // Create an Excel workbook
        Sheet sheet = workbook.createSheet("Subject Plan"); // Create a sheet

        // Create a title row
        Row titleRow = sheet.createRow(0);
        Cell titleCell = titleRow.createCell(0);
        titleCell.setCellValue("Subject Plan for " + studentName);
        titleCell.setCellStyle(createTitleCellStyle(workbook));
        sheet.addMergedRegion(new org.apache.poi.ss.util.CellRangeAddress(0, 0, 0, 1)); // Merge title cells

        // Create a header row
        Row headerRow = sheet.createRow(1);
        headerRow.createCell(0).setCellValue("Semester");
        headerRow.createCell(1).setCellValue("Subjects");

        // Populate the subject plan
        for (int i = 0; i < subjectPlan.size(); i++) {
            Row row = sheet.createRow(i + 2);
            row.createCell(0).setCellValue("Semester " + (i + 1));
            row.createCell(1).setCellValue(String.join(", ", subjectPlan.get(i)));
        }

        // Auto-size the columns for better readability
        sheet.autoSizeColumn(0);
        sheet.autoSizeColumn(1);

        // Write the workbook to the file
        try (FileOutputStream outputStream = new FileOutputStream(filePath)) {
            workbook.write(outputStream);
            System.out.println("[INFO] Excel file created successfully at: " + filePath);
        } finally {
            workbook.close(); // Ensure the workbook is closed to free resources
        }
    }

    /**
     * Creates a title cell style for the Excel file.
     *
     * @param workbook The workbook where the style is applied
     * @return A CellStyle object for the title
     */
    private static CellStyle createTitleCellStyle(Workbook workbook) {
        Font font = workbook.createFont();
        font.setFontHeightInPoints((short) 16);
        font.setBold(true);

        CellStyle style = workbook.createCellStyle();
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.CENTER);

        return style;
    }
}
