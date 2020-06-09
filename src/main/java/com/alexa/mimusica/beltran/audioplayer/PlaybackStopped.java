package com.alexa.mimusica.beltran.audioplayer;

import com.alexa.mimusica.beltran.utils.Persistencia;
import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.Response;
import com.amazon.ask.model.interfaces.audioplayer.PlaybackStoppedRequest;

import org.springframework.stereotype.Component;


import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.amazon.ask.request.Predicates.requestType;


@Component
public class PlaybackStopped implements RequestHandler {
    private static Logger logger = Logger.getLogger(PlaybackStopped.class.getName());


    @Override
    public boolean canHandle(HandlerInput input) {
        return input.matches(requestType(PlaybackStoppedRequest.class));
    }

    @Override
    public Optional<Response> handle(HandlerInput input) {
        logger.log(Level.INFO, "PlaybackStopped");

        PlaybackStoppedRequest request = (PlaybackStoppedRequest) input.getRequestEnvelope().getRequest();
        Long offset = request.getOffsetInMilliseconds();

        Map<String, Object> attributes = Persistencia.getPersistedAttributes(input);

        attributes.put("offset", offset);
        attributes.put("isPlayback", false);

        Persistencia.savePersistedAttributes(input, attributes);

        return input.getResponseBuilder().build();
    }
}
