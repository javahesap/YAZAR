package yazar.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import yazar.example.model.Author;
import yazar.example.model.Book;
import yazar.example.repository.AuthorRepository;
import yazar.example.repository.BookRepository;
import yazar.example.service.BookfileService;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private BookfileService bookService;
    @GetMapping
    public String listBooks(Model model) {
        List<Book> books = bookRepository.findAll();
        model.addAttribute("books", books);
        return "book/list";
    }

    @GetMapping("/new")
    public String newBookForm(Model model) {
        List<Author> authors = authorRepository.findAll();
        model.addAttribute("authors", authors);
        model.addAttribute("book", new Book());
        return "book/new";
    }

    @PostMapping("/new")
    public String newBook(@ModelAttribute Book book) {
        bookRepository.save(book);
        return "redirect:/books";
    }

    @GetMapping("/edit/{id}")
    public String editBookForm(@PathVariable Long id, Model model) {
        Book book = bookRepository.findById(id).orElse(null);
        List<Author> authors = authorRepository.findAll();
        model.addAttribute("book", book);
        model.addAttribute("authors", authors);
        return "book/edit";
    }

    @PostMapping("/edit/{id}")
    public String editBook(@PathVariable Long id, @ModelAttribute Book updatedBook) {
    	
   
       Book book = bookRepository.findById(id).orElse(null);
        if (book != null) {
            book.setTitle(updatedBook.getTitle());
            book.setAuthor(updatedBook.getAuthor());
            bookRepository.save(book);
        }
        return "redirect:/books";
    }

    @GetMapping("/delete/{id}")
    public String deleteBook(@PathVariable Long id) {
        bookRepository.deleteById(id);
        return "redirect:/books";
    }
    
    @GetMapping("/yukle")
    public String showUploadPage() {
        return "book/upload"; // Thymeleaf, "resources/templates/upload.html" sayfasını gösterecek
    }
    


    @PostMapping("/upload")
    public ResponseEntity<List<Book>> uploadExcelFile(@RequestParam("file") MultipartFile file) {
        try {
            List<Book> savedBooks = bookService.processExcelFile(file);
            return ResponseEntity.ok(savedBooks);
        } catch (IOException e) {
            return ResponseEntity.status(500).body(null);
        }
    }
    
    
    
}
