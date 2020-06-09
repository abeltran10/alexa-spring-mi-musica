package com.alexa.mimusica.beltran.firestore;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@Configuration
public class FireConfig {

    @Bean
    public Firestore getDb() {
        return FirestoreClient.getFirestore();
    }


    @PostConstruct
    public void init() throws IOException {

        InputStream inputStream = FireConfig.class.getClassLoader().getResourceAsStream("mi-app-alexa-musica.json");
        GoogleCredentials credentials = GoogleCredentials.fromStream(inputStream);
//          GoogleCredentials credentials = GoogleCredentials.getApplicationDefault();
        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(credentials)
                .build();

        FirebaseApp.initializeApp(options);

    }

}
