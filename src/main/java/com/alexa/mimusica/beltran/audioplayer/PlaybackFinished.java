package com.alexa.mimusica.beltran.audioplayer;

import com.alexa.mimusica.beltran.utils.Persistencia;
import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.Response;
import com.amazon.ask.model.interfaces.audioplayer.PlaybackFinishedRequest;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.amazon.ask.request.Predicates.requestType;

@Component
public class PlaybackFinished implements RequestHandler {
    private static Logger logger = Logger.getLogger(PlaybackFinished.class.getName());

    @Override
    public boolean canHandle(HandlerInput input) {
        return input.matches(requestType(PlaybackFinishedRequest.class));
    }

    @Override
    public Optional<Response> handle(HandlerInput input) {
        logger.log(Level.INFO, "PlaybackFinished");

        Map<String, Object> attributes = Persistencia.getPersistedAttributes(input);

        Long index = (Long) attributes.get("numeroCancion");

        attributes.put("isPlayback", false);

        if(index == null)
            attributes.put("isEnd", true);

        Persistencia.savePersistedAttributes(input, attributes);

        return input.getResponseBuilder().build();
    }
}
