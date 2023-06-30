package br.com.devadiel.reactiveflashcards.domain.service;

import br.com.devadiel.reactiveflashcards.domain.document.UserDocument;
import br.com.devadiel.reactiveflashcards.domain.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Slf4j
@Service
public class UserService {

    private final UserRepository userRepository;

    public Mono<UserDocument> save(final UserDocument document) {
        return userRepository.save(document)
                .doFirst(() -> log.info("==== try to save a follow document {}", document));
    }

}
