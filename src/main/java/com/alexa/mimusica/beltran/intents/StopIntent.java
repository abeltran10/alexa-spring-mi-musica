package com.alexa.mimusica.beltran.intents;

import com.alexa.mimusica.beltran.utils.Persistencia;
import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.impl.IntentRequestHandler;
import com.amazon.ask.model.IntentRequest;
import com.amazon.ask.model.Response;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.amazon.ask.request.Predicates.intentName;

@Component
public class StopIntent implements IntentRequestHandler {
    private static Logger logger = Logger.getLogger(StopIntent.class.getName());


    @Override
    public boolean canHandle(HandlerInput input, IntentRequest intentRequest) {
        Map<String, Object> attributes = Persistencia.getPersistedAttributes(input);

        Boolean isPlayback = (Boolean) attributes.get("isPlayback");

        return !isPlayback  && (input.matches(intentName("AMAZON.StopIntent"))
                || input.matches(intentName("AMAZON.CancelIntent"))
                || input.matches(intentName("AMAZON.PauseIntent")));
    }

    @Override
    public Optional<Response> handle(HandlerInput input, IntentRequest intentRequest) {
        logger.log(Level.INFO, "StopIntent");

        String speech = "¿Qué canción quieres escuchar? Si prefieres salir, di, salir";

        return input.getResponseBuilder().withSpeech(speech).withReprompt(speech).build();

    }
}
