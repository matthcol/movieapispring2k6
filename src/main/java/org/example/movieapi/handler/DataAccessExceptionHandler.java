package org.example.movieapi.handler;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE + 10)
public class DataAccessExceptionHandler {

    // Solution n°1 (la plus simple) --> Choix du statut et du message:

//    @ExceptionHandler(DataAccessException.class)
//    @ResponseStatus(code = HttpStatus.CONFLICT, reason = "Data Access Exception")
//    public void handleDataAccessException(){}

    //Solution n°2 --> Choisir une réponse JSON personnalisée:

    @ExceptionHandler(DataAccessException.class)
    public ProblemDetail handleDataAccessException(){
        return ProblemDetail.forStatusAndDetail(
                HttpStatus.CONFLICT,
                "Data Access Exception" //On peut mettre n'importe quel DTO de description d'erreur
        );
    }
}
