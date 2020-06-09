package com.alexa.mimusica.beltran.persistence;

import com.amazon.ask.attributes.persistence.PersistenceAdapter;
import com.amazon.ask.exception.PersistenceException;
import com.amazon.ask.model.RequestEnvelope;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.FieldValue;
import com.google.cloud.firestore.Firestore;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

@Repository
public class MyPersistenceAdapter implements PersistenceAdapter {

    @Autowired
    private Firestore db;

    @Override
    public Optional<Map<String, Object>> getAttributes(RequestEnvelope envelope) throws PersistenceException {

        Map<String, Object> map = new HashMap<>();

        try{

            DocumentSnapshot persistencia = db.collection("persistencia").document("datos").get().get();
            map = persistencia.getData();

        } catch (InterruptedException | ExecutionException e) {

            e.printStackTrace();

        }finally {

            return Optional.of(map);

        }

    }

    @Override
    public void saveAttributes(RequestEnvelope envelope, Map<String, Object> attributes) throws PersistenceException {
        try {
            db.collection("persistencia").document("datos").set(attributes).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void deleteAttributes(RequestEnvelope envelope) throws PersistenceException {
        try {
            DocumentReference doc = db.collection("persistencia").document("datos");

            Map<String, Object> map = doc.get().get().getData();
            Map<String, Object> delete = new HashMap<>();

            map.forEach((k, v) -> {

                delete.put(k, FieldValue.delete());


            });

            doc.update(delete).get();

        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }
}
