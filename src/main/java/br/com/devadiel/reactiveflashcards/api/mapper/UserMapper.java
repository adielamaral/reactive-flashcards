package br.com.devadiel.reactiveflashcards.api.mapper;

import br.com.devadiel.reactiveflashcards.api.controller.request.UserRequest;
import br.com.devadiel.reactiveflashcards.api.controller.response.UserResponse;
import br.com.devadiel.reactiveflashcards.domain.document.UserDocument;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDocument toDocument(final UserRequest request);

    UserResponse toResponse(final UserDocument document);

}
