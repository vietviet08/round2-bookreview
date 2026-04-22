package org.vietquoc.round2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.vietquoc.round2.entity.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
}
