package com.alexa.mimusica.beltran.utils;

import com.alexa.mimusica.beltran.model.Cancion;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Convert {

    public static List<Map<String,Object>> toMapList (List<Cancion> list) {

        List<Map<String,Object>> mapList = new ArrayList<>();

        list.forEach(cancion -> {

            mapList.add(cancion.toMap());

        });

        return mapList;


    }


}
