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
public class HelpIntent implements IntentRequestHandler {
    private static Logger logger = Logger.getLogger(HelpIntent.class.getName());

    @Override
    public boolean canHandle(HandlerInput input, IntentRequest intentRequest) {
        return input.matches(intentName("AMAZON.HelpIntent"));
    }

    @Override
    public Optional<Response> handle(HandlerInput input, IntentRequest intentRequest) {
        logger.log(Level.INFO, "HelpIntent");

        Map<String, Object> attributes = Persistencia.getPersistedAttributes(input);
        String usuario = (String) attributes.get("usuario");

        String speech = "Hola " + usuario + ", estás en mi música. Prueba a decir, pon el disco tal de grupo tal";
        String reprompt = "Vamos, di, pon tal cancion de tal grupo";


        return input.getResponseBuilder()
                .withSpeech(speech)
                .withReprompt(reprompt)
                .build();
    }
}
