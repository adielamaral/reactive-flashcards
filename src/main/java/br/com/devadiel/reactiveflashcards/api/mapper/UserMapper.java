package br.com.devadiel.reactiveflashcards.api.mapper;

import br.com.devadiel.reactiveflashcards.api.controller.request.UserRequest;
import br.com.devadiel.reactiveflashcards.api.controller.response.UserResponse;
import br.com.devadiel.reactiveflashcards.domain.document.UserDocument;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    UserDocument toDocument(final UserRequest request);

    UserResponse toResponse(final UserDocument document);

}
