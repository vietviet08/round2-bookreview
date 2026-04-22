package org.vietquoc.round2.service;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.vietquoc.round2.dto.BookDTO;
import org.vietquoc.round2.entity.Author;
import org.vietquoc.round2.entity.Book;
import org.vietquoc.round2.repository.AuthorRepository;
import org.vietquoc.round2.repository.BookRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    @Transactional(readOnly = true)
    public Page<BookDTO> findAll(Pageable pageable) {
        return bookRepository.findAll(pageable).map(this::toDTO);
    }

    @Transactional(readOnly = true)
    public Optional<BookDTO> findById(Long id) {
        return bookRepository.findById(id).map(this::toDTO);
    }

    @Transactional
   public BookDTO save(BookDTO bookDTO) {
        Author author = bookDTO.getAuthorId() != null ? authorRepository.findById(bookDTO.getAuthorId()).orElse(null) : null;

        Book book = Book.builder()
                .title(bookDTO.getTitle())
                .quantity(bookDTO.getQuantity())
                .author(author)
                .build();

        return toDTO(bookRepository.save(book));
   }

   @Transactional
   public Optional<BookDTO> update(Long id, BookDTO bookDTO) {
        return bookRepository.findById(id)
                .map(existing -> {
                    existing.setTitle(bookDTO.getTitle());
                    existing.setQuantity(bookDTO.getQuantity());

                    if (bookDTO.getAuthorId() != null) {
                        Author author = authorRepository.findById(bookDTO.getAuthorId()).orElse(null);
                        existing.setAuthor(author);
                    } else {
                        existing.setAuthor(null);
                    }

                    return toDTO(bookRepository.save(existing));
                });
   }

   @Transactional
   public void delete(Long id) {
        bookRepository.deleteById(id);
   }

    private BookDTO toDTO(Book book) {
        return BookDTO.builder()
                .id(book.getId())
                .title(book.getTitle())
                .quantity(book.getQuantity())
                .authorId(book.getAuthor().getId() != null ? book.getAuthor().getId() : null)
                .authorName(book.getAuthor().getName() != null ? book.getAuthor().getName() : null)
                .build();
    }

}
