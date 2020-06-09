package com.alexa.mimusica.beltran.utils;

import com.alexa.mimusica.beltran.model.Cancion;
import com.amazon.ask.model.interfaces.audioplayer.*;


public class Musica {

    public Musica() {

    }

    public static PlayDirective playCancion (PlayBehavior playBehavior, Cancion cancion, Long offset, String expectedPreviousToken) {
        Stream stream = Stream.builder()
                .withUrl(cancion.getUbicacion())
                .withToken(cancion.getToken())
                .withOffsetInMilliseconds(offset)
                .withExpectedPreviousToken(expectedPreviousToken)
                .build();

        AudioItem item = AudioItem.builder().withStream(stream).build();

        PlayDirective playDirective = PlayDirective.builder()
                .withPlayBehavior(playBehavior)
                .withAudioItem(item)
                .build();

        return playDirective;
    }

    public static StopDirective stop() {
            return StopDirective.builder().build();
    }

}
