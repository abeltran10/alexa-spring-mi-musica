package com.alexa.mimusica.beltran.intents;

import com.alexa.mimusica.beltran.dao.CancionDAO;
import com.alexa.mimusica.beltran.model.Cancion;
import com.alexa.mimusica.beltran.utils.Musica;
import com.alexa.mimusica.beltran.utils.Persistencia;
import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.impl.IntentRequestHandler;
import com.amazon.ask.model.IntentRequest;
import com.amazon.ask.model.Response;
import com.amazon.ask.model.interfaces.audioplayer.PlayBehavior;
import com.amazon.ask.model.interfaces.audioplayer.PlayDirective;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.amazon.ask.request.Predicates.intentName;

@Component
public class PreviousIntent implements IntentRequestHandler {
    Logger logger = Logger.getLogger(PreviousIntent.class.getName());


    @Override
    public boolean canHandle(HandlerInput input, IntentRequest intentRequest) {

        Map<String, Object> attributes = Persistencia.getPersistedAttributes(input);

        Boolean isPlayback = (Boolean) attributes.get("isPlayback");

        return isPlayback && input.matches(intentName("AMAZON.PreviousIntent"));
    }

    @Override
    public Optional<Response> handle(HandlerInput input, IntentRequest intentRequest) {
        logger.log(Level.INFO, "PreviousIntent");


        Map<String, Object> attributes = Persistencia.getPersistedAttributes(input);

        //Firebase lo interpreta como Long y no puede hacer cast directo a Integer
        Long numeroCancion = (Long) attributes.get("numeroCancion");

        List<Map<String, Object>> playList = (List<Map<String, Object>>) attributes.get("playList");

        Boolean isEnqueue = (Boolean) attributes.get("isEnqueue");

        Optional<Response> response = input.getResponseBuilder().build();

        if(playList == null || numeroCancion == null)
            return response;

        if(isEnqueue)
            numeroCancion--;

        if((numeroCancion.intValue() - 1) >= 0) {

            numeroCancion--;

            Cancion cancionAnterior = new Cancion(playList.get(numeroCancion.intValue()));

            attributes.put("numeroCancion", numeroCancion);

            PlayDirective playDirective = Musica.playCancion(PlayBehavior.REPLACE_ALL, cancionAnterior, 0L, null);

            response = input.getResponseBuilder().addDirective(playDirective).build();
        }

        attributes.put("isEnqueue", false);

        Persistencia.savePersistedAttributes(input, attributes);

        return response;
    }
}
