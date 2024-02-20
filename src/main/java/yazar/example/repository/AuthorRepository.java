package yazar.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import yazar.example.model.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}