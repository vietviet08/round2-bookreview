package org.vietquoc.round2.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewDTO {

    private Long id;

    @NotBlank(message = "Review content must not be blank")
    private String content;

    private Long bookId;
    private String bookTitle;
}
