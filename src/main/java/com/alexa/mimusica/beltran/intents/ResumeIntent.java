package com.alexa.mimusica.beltran.intents;

import com.alexa.mimusica.beltran.model.Cancion;
import com.alexa.mimusica.beltran.utils.Musica;
import com.alexa.mimusica.beltran.utils.Persistencia;
import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.impl.IntentRequestHandler;
import com.amazon.ask.model.IntentRequest;
import com.amazon.ask.model.Response;
import com.amazon.ask.model.interfaces.audioplayer.PlayBehavior;
import com.amazon.ask.model.interfaces.audioplayer.PlayDirective;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.amazon.ask.request.Predicates.intentName;

@Component
public class ResumeIntent implements IntentRequestHandler {
    private static Logger logger = Logger.getLogger(ResumeIntent.class.getName());


    @Override
    public boolean canHandle(HandlerInput input, IntentRequest intentRequest) {
        Map<String, Object> attributes = Persistencia.getPersistedAttributes(input);

        Boolean isPlayback = (Boolean) attributes.get("isPlayback");
        Boolean isEnd = (Boolean) attributes.get("isEnd");

        return !isPlayback && !isEnd && input.matches(intentName("AMAZON.ResumeIntent"));
    }

    @Override
    public Optional<Response> handle(HandlerInput input, IntentRequest intentRequest) {

        logger.log(Level.INFO, "ResumeIntent");

        Map<String, Object> attributes = Persistencia.getPersistedAttributes(input);

        Long offset = (Long) attributes.get("offset");

        Map<String, Object> map = (Map<String, Object>) attributes.get("cancion");
        logger.log(Level.INFO, map.toString());

        Cancion cancion = new Cancion(map);

        PlayDirective playDirective = Musica.playCancion(PlayBehavior.REPLACE_ALL, cancion, offset, null);

        return input.getResponseBuilder()
                .withSpeech("Reanudando " + cancion.getTitulo() + " de " + cancion.getAutor())
                .addDirective(playDirective)
                .withShouldEndSession(true)
                .build();

    }
}
