package com.alexa.mimusica.beltran.utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Convert {

    //convert POJO to Map<String, Object>
    public static Map<String, Object> toMap(Object object) {

        Map<String, Object> map = new HashMap<>();

        Field[] fields = object.getClass().getDeclaredFields();

        try {

            for (int i = 0; i < fields.length; i++) {

                String field = fields[i].getName();

                map.put(field, object.getClass().getMethod("get" + field.substring(0, 1).toUpperCase() + field.substring(1)).invoke(object));
            }

        } catch (NoSuchMethodException e) {

            e.printStackTrace();

        } catch (IllegalAccessException e) {

            e.printStackTrace();

        } catch (InvocationTargetException e) {

            e.printStackTrace();

        }finally {

            return map;
        }

    }

    //Convert List of POJO's to List<Map<String, Object>
    public static List<Map<String, Object>> toMapList(List list) {

        List<Map<String, Object>> mapList = new ArrayList<>();


        list.forEach( e -> {

            mapList.add(toMap(e));

        });

        return mapList;


    }


}
