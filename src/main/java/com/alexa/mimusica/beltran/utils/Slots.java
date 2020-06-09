package com.alexa.mimusica.beltran.utils;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.request.RequestHelper;

public class Slots {


    public Slots() {

    }

    public static String getSlots(HandlerInput input, String slot) {
        RequestHelper requestHelper = RequestHelper.forHandlerInput(input);

        String valor = requestHelper.getSlotValue(slot).isPresent() ? requestHelper.getSlotValue(slot).get() : null;

        return valor;
    }

}
