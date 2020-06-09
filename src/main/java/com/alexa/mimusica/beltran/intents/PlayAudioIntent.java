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
public class PlayAudioIntent implements IntentRequestHandler {

    private static Logger logger = Logger.getLogger(PlayAudioIntent.class.getName());

    @Autowired
    private CancionDAO cancionDAO;


    @Override
    public boolean canHandle(HandlerInput input, IntentRequest intentRequest) {
        return input.matches(intentName("PlayAudioIntent"));
    }

    @Override
    public Optional<Response> handle(HandlerInput input, IntentRequest intentRequest) {

        logger.log(Level.INFO, "PlayAudioIntent");

        String disco = Slots.getSlots(input, "disco");
        String grupo = Slots.getSlots(input, "grupo");
        String titulo = Slots.getSlots(input,"titulo");

        Map<String, Object> attributes = Persistencia.getPersistedAttributes(input);

        Cancion cancion = null;
        List<Cancion> playList = null;

        Integer numeroCancion = null;

        String speech;
        String reprompt = "¿Qué quieres escuchar?";

        if(disco != null) {
            logger.log(Level.INFO, "disco " +  disco);

            playList = cancionDAO.getCancionesByGrupoDisco(grupo, disco);

            if (playList.isEmpty())
                speech = "No existe el disco " + disco + " de " + grupo + " ¿Qué quieres escuchar?";

            else {

                numeroCancion = 0;

                cancion = playList.get(numeroCancion);

                speech = "Reproduciendo el disco " + cancion.getDisco() + " de " + cancion.getAutor();
            }

        }
        else if(titulo != null ) {
            logger.log(Level.INFO, "cancion");

            cancion = cancionDAO.getCancionByAutorTituloDisco(grupo,titulo, null);

            logger.log(Level.INFO, "titulo: " + titulo + " - grupo: " + grupo);

            if (cancion == null)
                speech = "No existe la canción " + titulo + " de " + grupo + " ¿Qué quieres escuchar?";

            else
                speech = "Reproduciendo la canción " + cancion.getTitulo() + " de " + cancion.getAutor();

        }
        else {
            logger.log(Level.INFO, "grupo");

            playList = cancionDAO.getCancionesByGrupo(grupo);

            if(playList.isEmpty())
                speech = "No existe el grupo " + grupo + " ¿Qué quieres escuchar?";

            else {
                numeroCancion = 0;

                cancion = playList.get(numeroCancion);

                speech = "Reproduciendo canciones de " + cancion.getAutor();
            }

        }


        if(cancion == null)
            return input.getResponseBuilder()
                    .withSpeech(speech)
                    .withReprompt(reprompt)
                    .build();

        attributes.put("numeroCancion", numeroCancion);

        List<Map<String, Object>> mapList = (playList != null) ? Convert.toMapList(playList) : null;
        attributes.put("playList", mapList);

        attributes.put("cancion", Convert.toMap(cancion));

        Persistencia.savePersistedAttributes(input, attributes);

        logger.log(Level.INFO, cancion.getTitulo());

        PlayDirective playDirective = Musica.playCancion(PlayBehavior.REPLACE_ALL, cancion, 0L, null);

        return input.getResponseBuilder()
                .withSpeech(speech)
                .addDirective(playDirective)
                .withShouldEndSession(true)
                .build();
    }


}
