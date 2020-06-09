package com.alexa.mimusica.beltran.servlet;

import com.alexa.mimusica.beltran.intents.PlayAudioIntent;
import com.alexa.mimusica.beltran.persistence.MyPersistenceAdapter;
import com.alexa.mimusica.beltran.request.LaunchRequestHandler;
import com.amazon.ask.Skill;
import com.amazon.ask.Skills;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.servlet.SkillServlet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class AlexaConfiguration {

    @Value("${alexa.skill.id}")
    private String skillId;

    @Value("${alexa.skill.endpoint.url}")
    private String endpoint;

    private LaunchRequestHandler launchRequestHandler;

    private PlayAudioIntent playAudioIntent;

    private MyPersistenceAdapter myPersistenceAdapter;

    @Autowired
    public AlexaConfiguration(LaunchRequestHandler launchRequestHandler, PlayAudioIntent playAudioIntent, MyPersistenceAdapter myPersistenceAdapter) {
        this.launchRequestHandler = launchRequestHandler;
        this.playAudioIntent = playAudioIntent;
        this.myPersistenceAdapter = myPersistenceAdapter;
    }



    @Bean
    public ServletRegistrationBean<SkillServlet> registerServlet(Skill skillInstance) {
        SkillServlet skillServlet = new SkillServlet(skillInstance);
        return new ServletRegistrationBean<>(skillServlet, endpoint);
    }

    @Bean
    public Skill skillInstance() {
        return Skills.custom()
                .addRequestHandler(launchRequestHandler)
                .addRequestHandler(playAudioIntent)

                .withPersistenceAdapter(myPersistenceAdapter)
                .withSkillId(skillId)
                .build();
    }
}