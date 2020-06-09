package com.alexa.mimusica.beltran.intents;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.impl.IntentRequestHandler;
import com.amazon.ask.model.IntentRequest;
import com.amazon.ask.model.Response;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.amazon.ask.request.Predicates.intentName;

@Component
public class FallbackIntent implements IntentRequestHandler {
    private Logger logger = Logger.getLogger(FallbackIntent.class.getName());


    @Override
    public boolean canHandle(HandlerInput input, IntentRequest intentRequest) {
        return input.matches(intentName("AMAZON.FallbackIntent"));
    }

    @Override
    public Optional<Response> handle(HandlerInput input, IntentRequest intentRequest) {
        logger.log(Level.INFO, "FallbackIntent");


        return input.getResponseBuilder().withSpeech("No te he entendido. ¿Qué quieres decir?").withShouldEndSession(false).build();
    }
}
