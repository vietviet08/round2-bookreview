package org.vietquoc.round2.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthorDTO {
    private Long id;

    @NotBlank(message = "Author name must not be blank")
    private String name;
}
