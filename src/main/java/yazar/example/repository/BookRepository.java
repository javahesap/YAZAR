package yazar.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import yazar.example.model.Book;

public interface BookRepository extends JpaRepository<Book, Long> {
}