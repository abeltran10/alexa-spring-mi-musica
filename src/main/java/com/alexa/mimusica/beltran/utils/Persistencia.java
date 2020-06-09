package com.alexa.mimusica.beltran.utils;

import com.amazon.ask.attributes.AttributesManager;
import com.amazon.ask.dispatcher.request.handler.HandlerInput;

import java.util.Map;

public class Persistencia {


    public static Map<String, Object> getPersistedAttributes(HandlerInput input) {
        AttributesManager attributesManager = input.getAttributesManager();
        Map<String,Object> attributes = attributesManager.getPersistentAttributes();

        return attributes;
    }

    public static void savePersistedAttributes(HandlerInput input, Map<String, Object> attributes) {
        AttributesManager attributesManager = input.getAttributesManager();

        attributesManager.setPersistentAttributes(attributes);

        attributesManager.savePersistentAttributes();
    }

    public static void deletePersistedAttributes(HandlerInput input) {
        AttributesManager attributesManager = input.getAttributesManager();

        attributesManager.deletePersistentAttributes();
    }

}
