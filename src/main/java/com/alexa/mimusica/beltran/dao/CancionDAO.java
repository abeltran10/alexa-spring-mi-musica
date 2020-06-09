package com.alexa.mimusica.beltran.dao;

import com.alexa.mimusica.beltran.model.Cancion;
import com.google.cloud.firestore.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Repository
public class CancionDAO {

    @Autowired
    private Firestore db;

    public List<Cancion> getCancionesByGrupo(String grupo) {

        List<Cancion> canciones = new ArrayList<>();

        try {

            Iterator<DocumentReference> docRef = db.collection(grupo).listDocuments().iterator();

            while (docRef.hasNext()) {
                DocumentSnapshot cancion = docRef.next().get().get();

                canciones.add(cancion.toObject(Cancion.class));
            }

        }catch (ExecutionException | InterruptedException e) {

            e.printStackTrace();

        }finally {

            return canciones;

        }

    }

    public List<Cancion> getCancionesByGrupoDisco(String grupo, String disco) {

        List<Cancion> canciones = new ArrayList<>();

        try {

            Query query =  db.collection(grupo).whereEqualTo("disco", disco);

            QuerySnapshot snapshot = query.get().get();

            for (DocumentSnapshot doc : snapshot.getDocuments()){
                canciones.add(doc.toObject(Cancion.class));
            }

        }catch (ExecutionException | InterruptedException e) {

            e.printStackTrace();

        }finally {

            return canciones;

        }





    }


    public Cancion getCancionByAutorTituloDisco(String autor, String titulo, String disco) {

        Cancion cancion = null;

        try {

            Query query = db.collection(autor);

            if(disco != null)
                query = query.whereEqualTo("disco", disco);

            query = query.whereEqualTo("titulo", titulo);

            QuerySnapshot snapshot = query.get().get();

            for(DocumentSnapshot doc : snapshot.getDocuments()) {

                cancion = doc.toObject(Cancion.class);

                break;
            }


        }catch (ExecutionException | InterruptedException e) {

            e.printStackTrace();

        }finally {

            return cancion;

        }




    }

    public List<Cancion> getPlaylistByUsuario(String usuario) {

        List<Cancion> playList = new ArrayList<>();

        try {

            Iterator<CollectionReference> iterator = db.listCollections().iterator();

            while (iterator.hasNext()) {

                QuerySnapshot query = iterator.next().whereEqualTo("propietario", usuario).get().get();

                for (DocumentSnapshot doc : query.getDocuments()) {

                    playList.add(doc.toObject(Cancion.class));

                }

            }

            Collections.sort(playList);

        }catch (ExecutionException | InterruptedException e) {

            e.printStackTrace();

        }finally {

            return playList;

        }


    }


}
