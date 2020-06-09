package com.alexa.mimusica.beltran.audioplayer;

import com.alexa.mimusica.beltran.dao.CancionDAO;
import com.alexa.mimusica.beltran.model.Cancion;
import com.alexa.mimusica.beltran.utils.Musica;
import com.alexa.mimusica.beltran.utils.Persistencia;
import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.Response;
import com.amazon.ask.model.interfaces.audioplayer.PlayBehavior;
import com.amazon.ask.model.interfaces.audioplayer.PlayDirective;
import com.amazon.ask.model.interfaces.audioplayer.PlaybackNearlyFinishedRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.amazon.ask.request.Predicates.requestType;

@Component
public class PlaybackNearlyFinished implements RequestHandler {
    private static Logger logger = Logger.getLogger(PlaybackNearlyFinished.class.getName());

    @Autowired
    private CancionDAO cancionDAO;


    @Override
    public boolean canHandle(HandlerInput input) {
        return input.matches(requestType(PlaybackNearlyFinishedRequest.class));
    }

    @Override
    public Optional<Response> handle(HandlerInput input) {
        logger.log(Level.INFO, "PlaybackNearlyFinished");

        Map<String, Object> attributes = Persistencia.getPersistedAttributes(input);

        //Firebase lo interpreta como Long y no puede hacer cast directo a Integer
        Long numeroCancion = (Long) attributes.get("numeroCancion");

        Map<String, Object> cancionMap = (Map<String, Object>) attributes.get("cancion");
        Cancion cancionActual = new Cancion(cancionMap);

        List<Map<String, Object>> playList = (List<Map<String,Object>>) attributes.get("playList");

        Optional<Response> response = input.getResponseBuilder().build();

        if(playList == null || numeroCancion == null)
            return response;


        if((numeroCancion.intValue() + 1) < playList.size()) {

            numeroCancion++;

            Cancion cancionSiguiente = new Cancion(playList.get(numeroCancion.intValue()));

            PlayDirective playDirective = Musica.playCancion(PlayBehavior.ENQUEUE, cancionSiguiente, 0L, cancionActual.getToken());

            attributes.put("isEnqueue", true);

            response = input.getResponseBuilder()
                    .addDirective(playDirective)
                    .build();
        }
        else
            numeroCancion = null;

        attributes.put("numeroCancion", numeroCancion);

        Persistencia.savePersistedAttributes(input, attributes);

        return response;
    }

}
