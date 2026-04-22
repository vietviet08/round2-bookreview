package org.vietquoc.round2.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.vietquoc.round2.dto.AuthorDTO;
import org.vietquoc.round2.entity.Author;
import org.vietquoc.round2.repository.AuthorRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthorService {
    private final AuthorRepository authorRepository;

    @Transactional(readOnly = true)
    public Page<AuthorDTO> findAll(Pageable pageable) {
        return authorRepository.findAll(pageable).map(this::toDTO);
    }

    @Transactional(readOnly = true)
    public Optional<AuthorDTO> findById(Long id) {
        return authorRepository.findById(id).map(this::toDTO);
    }

    @Transactional
    public AuthorDTO save(AuthorDTO authorDTO) {
        Author author = Author.builder()
                .name(authorDTO.getName())
                .build();
        Author savedAuthor = authorRepository.save(author);
        return toDTO(savedAuthor);
    }

    @Transactional
    public Optional<AuthorDTO> update(Long id, AuthorDTO authorDTO) {
        return authorRepository.findById(id)
                .map(existing -> {
                    existing.setName(authorDTO.getName());
                    Author updatedAuthor = authorRepository.save(existing);
                    return toDTO(updatedAuthor);
                });
    }

    @Transactional
    public void deleteById(Long id) {
        authorRepository.deleteById(id);
    }

    private AuthorDTO toDTO(Author author) {
        return AuthorDTO.builder()
                .id(author.getId())
                .name(author.getName())
                .build();
    }
}
