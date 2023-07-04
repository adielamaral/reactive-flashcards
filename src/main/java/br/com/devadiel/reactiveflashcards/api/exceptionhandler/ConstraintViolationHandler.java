package br.com.devadiel.reactiveflashcards.api.exceptionhandler;

import br.com.devadiel.reactiveflashcards.api.controller.response.ErrorFieldResponse;
import br.com.devadiel.reactiveflashcards.api.controller.response.ProblemResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.ConstraintViolationException;

import static br.com.devadiel.reactiveflashcards.domain.exception.BaseErrorMessage.GENERIC_BAD_REQUEST;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Slf4j
public class ConstraintViolationHandler extends AbstractHandleException<ConstraintViolationException> {

    public ConstraintViolationHandler(ObjectMapper objectMapper) {
        super(objectMapper);
    }

    @Override
    Mono<Void> handlerException(ServerWebExchange exchange, ConstraintViolationException ex) {
        return Mono.fromCallable(() -> {
                    prepareExchange(exchange, BAD_REQUEST);
                    return GENERIC_BAD_REQUEST.getMessage();
                }).map(message -> buildError(BAD_REQUEST, message))
                .flatMap(response -> buildRequestErrorMessage(response, ex))
                .doFirst(() -> log.error("==== ConstraintViolationException ", ex))
                .flatMap(problemResponse -> writeResponse(exchange, problemResponse));
    }

    private Mono<ProblemResponse> buildRequestErrorMessage(final ProblemResponse response, final ConstraintViolationException ex) {
        return Flux.fromIterable(ex.getConstraintViolations())
                .map(constraintViolation -> ErrorFieldResponse.builder()
                        .name(((PathImpl) constraintViolation.getPropertyPath()).getLeafNode().toString())
                        .message(constraintViolation.getMessage()).build())
                .collectList()
                .map(problemResponses -> response.toBuilder().fields(problemResponses).build());
    }
}
