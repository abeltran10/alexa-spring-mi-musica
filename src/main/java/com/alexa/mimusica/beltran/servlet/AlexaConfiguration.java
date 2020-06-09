package com.alexa.mimusica.beltran.servlet;

import com.alexa.mimusica.beltran.audioplayer.*;
import com.alexa.mimusica.beltran.intents.*;
import com.alexa.mimusica.beltran.persistence.MyPersistenceAdapter;
import com.alexa.mimusica.beltran.request.EndSession;
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
    private HelpIntent helpIntent;
    private PlayAudioIntent playAudioIntent;
    private PlayListIntent playListIntent;
    private NextIntent nextIntent;
    private PreviousIntent previousIntent;
    private PauseIntent pauseIntent;
    private ResumeIntent resumeIntent;
    private StopIntent stopIntent;
    private ContinueIntent continueIntent;

    private PlaybackStarted playbackStarted;
    private PlaybackFinished playbackFinished;
    private PlaybackStopped playbackStopped;
    private PlaybackNearlyFinished playbackNearlyFinished;
    private PlaybackFailed playbackFailed;

    private EndSession endSession;
    private FallbackIntent fallbackIntent;
    private MyPersistenceAdapter myPersistenceAdapter;

    @Autowired
    public AlexaConfiguration(LaunchRequestHandler launchRequestHandler, HelpIntent helpIntent, PlayAudioIntent playAudioIntent, PlayListIntent playListIntent
            ,NextIntent nextIntent, PreviousIntent previousIntent, PauseIntent pauseIntent, ResumeIntent resumeIntent, StopIntent stopIntent
            ,ContinueIntent continueIntent, PlaybackStarted playbackStarted, PlaybackFinished playbackFinished, PlaybackStopped playbackStopped
            ,PlaybackNearlyFinished playbackNearlyFinished, PlaybackFailed playbackFailed, EndSession endSession, FallbackIntent fallbackIntent
            ,MyPersistenceAdapter myPersistenceAdapter)
    {

        this.launchRequestHandler = launchRequestHandler;
        this.helpIntent = helpIntent;
        this.playAudioIntent = playAudioIntent;
        this.playListIntent = playListIntent;
        this.nextIntent = nextIntent;
        this.previousIntent = previousIntent;
        this.pauseIntent = pauseIntent;
        this.resumeIntent = resumeIntent;
        this.stopIntent = stopIntent;
        this.continueIntent = continueIntent;
        this.playbackStarted = playbackStarted;
        this.playbackFinished = playbackFinished;
        this.playbackStopped = playbackStopped;
        this.playbackNearlyFinished = playbackNearlyFinished;
        this.playbackFailed = playbackFailed;
        this.endSession = endSession;
        this.fallbackIntent = fallbackIntent;

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
                .addRequestHandler(helpIntent)
                .addRequestHandler(playAudioIntent)
                .addRequestHandler(playListIntent)
                .addRequestHandler(nextIntent)
                .addRequestHandler(previousIntent)
                .addRequestHandler(pauseIntent)
                .addRequestHandler(resumeIntent)
                .addRequestHandler(stopIntent)
                .addRequestHandler(continueIntent)
                .addRequestHandler(playbackStarted)
                .addRequestHandler(playbackFinished)
                .addRequestHandler(playbackStopped)
                .addRequestHandler(playbackNearlyFinished)
                .addRequestHandler(playbackFailed)
                .addRequestHandler(endSession)
                .addRequestHandler(fallbackIntent)

                .withPersistenceAdapter(myPersistenceAdapter)
                .withSkillId(skillId)
                .build();
    }
}