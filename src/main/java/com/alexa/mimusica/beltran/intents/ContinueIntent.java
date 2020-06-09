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
public class ContinueIntent implements IntentRequestHandler {
    private static Logger logger = Logger.getLogger(ContinueIntent.class.getName());



    @Override
    public boolean canHandle(HandlerInput input, IntentRequest intentRequest) {
        Map<String, Object> attributes = Persistencia.getPersistedAttributes(input);

        Boolean isEnd = (Boolean) attributes.get("isEnd");

        return (isEnd && input.matches(intentName("AMAZON.ResumeIntent")));
    }

    @Override
    public Optional<Response> handle(HandlerInput input, IntentRequest intentRequest) {
        logger.log(Level.INFO, "ContinueIntent");

        String speech = "¿Qué canción quires escuchar ahora?";
        String reprompt = "¿Qué quieres escuchar?";

        return input.getResponseBuilder().withSpeech(speech).withReprompt(reprompt).build();
    }
}
