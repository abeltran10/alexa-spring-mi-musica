package com.alexa.mimusica.beltran.request;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.LaunchRequest;
import com.amazon.ask.model.Response;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.amazon.ask.request.Predicates.requestType;

@Component
public class LaunchRequestHandler implements RequestHandler {
    private static Logger logger = Logger.getLogger(LaunchRequestHandler.class.getName());


    @Override
    public boolean canHandle(HandlerInput input) {
        return input.matches(requestType(LaunchRequest.class));
    }

    @Override
    public Optional<Response> handle(HandlerInput input) {
        logger.log(Level.INFO, "LaunchRequestHandler");

        String speechText = "Saludos, ¿qué quieres escuchar?";
        String reprompt = "Disculpa, no te he oido. ¿Qué quires escuchar?";

        return input.getResponseBuilder()
                .withSpeech(speechText)
                .withReprompt(reprompt)
                .build();
    }
}
