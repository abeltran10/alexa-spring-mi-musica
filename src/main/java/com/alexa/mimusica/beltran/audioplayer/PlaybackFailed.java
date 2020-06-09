package com.alexa.mimusica.beltran.audioplayer;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.Response;
import com.amazon.ask.model.interfaces.audioplayer.PlaybackFailedRequest;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.amazon.ask.request.Predicates.requestType;

@Component
public class PlaybackFailed implements RequestHandler {
    Logger logger = Logger.getLogger(PlaybackFailed.class.getName());


    @Override
    public boolean canHandle(HandlerInput input) {
        return input.matches(requestType(PlaybackFailedRequest.class));
    }

    @Override
    public Optional<Response> handle(HandlerInput input) {
        logger.log(Level.INFO, "PlaybackFailed");

        PlaybackFailedRequest playbackFailedRequest = (PlaybackFailedRequest) input.getRequestEnvelope().getRequest();
        logger.log(Level.INFO, String.valueOf(playbackFailedRequest.getError().getType()));

        return input.getResponseBuilder().build();
    }
}
