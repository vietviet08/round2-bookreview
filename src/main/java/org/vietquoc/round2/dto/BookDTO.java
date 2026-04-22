package org.vietquoc.round2.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookDTO {
    private Long id;

    @NotBlank(message = "Book title must not be blank")
    private String title;

    private Integer quantity;

    private Long authorId;

    private String authorName;
}
