package com.alexa.mimusica.beltran.request;

import com.alexa.mimusica.beltran.utils.Persistencia;
import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.Response;
import com.amazon.ask.model.SessionEndedRequest;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.amazon.ask.request.Predicates.requestType;


@Component
public class EndSession implements RequestHandler {
    private Logger logger = Logger.getLogger(EndSession.class.getName());


    @Override
    public boolean canHandle(HandlerInput input) {
        return input.matches(requestType(SessionEndedRequest.class));
    }

    @Override
    public Optional<Response> handle(HandlerInput input) {
        logger.log(Level.WARNING, "EndSession");

        Persistencia.deletePersistedAttributes(input);

        return input.getResponseBuilder().build();
    }
}
