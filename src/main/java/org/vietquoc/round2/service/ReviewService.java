package org.vietquoc.round2.service;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.vietquoc.round2.dto.BookDTO;
import org.vietquoc.round2.dto.ReviewDTO;
import org.vietquoc.round2.entity.Book;
import org.vietquoc.round2.entity.Review;
import org.vietquoc.round2.repository.BookRepository;
import org.vietquoc.round2.repository.ReviewRepository;

import javax.swing.text.html.Option;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final BookRepository bookRepository;

    @Transactional(readOnly = true)
    public Page<ReviewDTO> findAll(final Pageable pageable) {
        return reviewRepository.findAll(pageable).map(this::toDTO);
    }

    @Transactional(readOnly = true)
    public Optional<ReviewDTO> findById(final Long id) {
        return reviewRepository.findById(id).map(this::toDTO);
    }

    @Transactional
    public ReviewDTO save(final ReviewDTO reviewDTO) {
        Book book = reviewDTO.getBookId() != null ? bookRepository.findById(reviewDTO.getBookId()).orElse(null) : null;

        Review review = Review.builder()
                .content(reviewDTO.getContent())
                .book(book)
                .build();

        return toDTO(reviewRepository.save(review));
    }

    @Transactional
    public Optional<ReviewDTO> update(Long id, ReviewDTO reviewDTO) {
        return reviewRepository.findById(id)
                .map(existing -> {
                    existing.setContent(reviewDTO.getContent());

                    if (reviewDTO.getBookId() != null) {
                        Book book = bookRepository.findById(reviewDTO.getBookId()).orElse(null);
                        existing.setBook(book);
                    } else {
                        existing.setBook(null);
                    }

                    return toDTO(reviewRepository.save(existing));
                });

    }

    @Transactional
    public void deleteById(Long id) {
        reviewRepository.deleteById(id);
    }

    private ReviewDTO toDTO(Review review) {
        return ReviewDTO.builder()
                .id(review.getId())
                .content(review.getContent())
                .bookId(review.getBook() != null ? review.getBook().getId() : null)
                .bookTitle(review.getBook() != null ? review.getBook().getTitle() : null)
                .build();
    }
}
