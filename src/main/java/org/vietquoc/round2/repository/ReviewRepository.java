package org.vietquoc.round2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.vietquoc.round2.entity.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
}
