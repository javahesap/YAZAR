package yazar.example.service;

import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import yazar.example.model.Book;
import yazar.example.repository.BookRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class BookfileService {

    @Autowired
    private BookRepository bookRepository;

    public List<Book> processExcelFile(MultipartFile file) throws IOException {
        List<Book> bookList = new ArrayList<>();

        try (Workbook workbook = WorkbookFactory.create(file.getInputStream())) {
            Sheet sheet = workbook.getSheetAt(0); // Assuming there is only one sheet

            Iterator<Row> rowIterator = sheet.iterator();

            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                if (row.getRowNum() == 0) {
                    // Skip the header row
                    continue;
                }

                Book book = new Book();
                Cell titleCell = row.getCell(0);
                if (titleCell != null) {
                    if (titleCell.getCellType() == CellType.STRING) {
                        book.setTitle(titleCell.getStringCellValue());
                    } else if (titleCell.getCellType() == CellType.NUMERIC) {
                        // If the cell contains a numeric value, you might convert it to a string
                        book.setTitle(String.valueOf(titleCell.getNumericCellValue()));
                    }
                }

                Cell authorIdCell = row.getCell(1);
                if (authorIdCell != null) {
                    if (authorIdCell.getCellType() == CellType.NUMERIC) {
                        book.setAuthorId((long) authorIdCell.getNumericCellValue());
                    } else if (authorIdCell.getCellType() == CellType.STRING) {
                        // If the cell contains a string representation of a numeric value, you might convert it to a long
                        book.setAuthorId(Long.parseLong(authorIdCell.getStringCellValue()));
                    }
                }

                // Add the book to the list
                bookList.add(book);
            }
        }

        // Save the books to the database
        return bookRepository.saveAll(bookList);
    }

}
