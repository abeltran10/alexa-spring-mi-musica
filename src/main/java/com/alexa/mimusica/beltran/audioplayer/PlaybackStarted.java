package com.alexa.mimusica.beltran.audioplayer;

import com.alexa.mimusica.beltran.dao.CancionDAO;
import com.alexa.mimusica.beltran.utils.Persistencia;
import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.Response;
import com.amazon.ask.model.interfaces.audioplayer.PlaybackStartedRequest;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.amazon.ask.request.Predicates.requestType;

@Component
public class PlaybackStarted implements RequestHandler {
    private static Logger logger = Logger.getLogger(PlaybackStarted.class.getName());


    @Override
    public boolean canHandle(HandlerInput input) {
        return input.matches(requestType(PlaybackStartedRequest.class));
    }

    @Override
    public Optional<Response> handle(HandlerInput input) {
        logger.log(Level.INFO, "PlaybackStarted");

        PlaybackStartedRequest request = (PlaybackStartedRequest) input.getRequestEnvelope().getRequest();

        Map<String, Object> attributes = Persistencia.getPersistedAttributes(input);

        String token = request.getToken();

        List<Map<String, Object>> playList = (List<Map<String, Object>>) attributes.get("playList");

        Long index = (Long) attributes.get("numeroCancion");

        if(index == null || index.equals(0L))
            attributes.put("isEnd", false);

        if(playList != null) {
            Map<String, Object> cancionActual = playList.stream().filter( c -> c.get("token").equals(token)).findAny().get();

            attributes.put("cancion", cancionActual);
        }

        attributes.put("isEnqueue", false);
        attributes.put("isPlayback", true);

        Persistencia.savePersistedAttributes(input, attributes);


        return input.getResponseBuilder().build();
    }
}
