package com.alexa.mimusica.beltran.intents;

import com.alexa.mimusica.beltran.dao.CancionDAO;
import com.alexa.mimusica.beltran.model.Cancion;
import com.alexa.mimusica.beltran.utils.Convert;
import com.alexa.mimusica.beltran.utils.Musica;
import com.alexa.mimusica.beltran.utils.Persistencia;
import com.alexa.mimusica.beltran.utils.Slots;
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
public class PlayListIntent implements IntentRequestHandler {

    private static Logger logger = Logger.getLogger(PlayListIntent.class.getName());

    @Autowired
    private CancionDAO cancionDAO;

    @Override
    public boolean canHandle(HandlerInput input, IntentRequest intentRequest) {
        return input.matches(intentName("PlayListIntent"));
    }

    @Override
    public Optional<Response> handle(HandlerInput input, IntentRequest intentRequest) {
        logger.log(Level.INFO, "PlayListIntent");

        String usuario = Slots.getSlots(input, "usuario");

        logger.log(Level.INFO, "usuario " + usuario);

        Integer numeroCancion = 0;

        Map<String, Object> attributes = Persistencia.getPersistedAttributes(input);
        attributes.put("usuario", usuario);

        List<Cancion> playList = cancionDAO.getPlaylistByUsuario(usuario);

        Cancion cancion = playList.get(numeroCancion);

        attributes.put("numeroCancion", numeroCancion);
        attributes.put("playList", Convert.toMapList(playList));
        attributes.put("cancion", Convert.toMap(cancion));

        Persistencia.savePersistedAttributes(input,attributes);

        PlayDirective playDirective = Musica.playCancion(PlayBehavior.REPLACE_ALL, cancion, 0L, null);

        String speech = "Reproduciendo la lista de canciones de " + cancion.getPropietario();

        return input.getResponseBuilder()
                        .withSpeech(speech)
                        .addDirective(playDirective)
                        .withShouldEndSession(true)
                        .build();
    }
}
