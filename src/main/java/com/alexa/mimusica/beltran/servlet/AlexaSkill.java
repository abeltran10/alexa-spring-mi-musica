package com.alexa.mimusica.beltran.servlet;

import com.amazon.ask.Skill;
import com.amazon.ask.Skills;
import com.amazon.ask.servlet.SkillServlet;

public class AlexaSkill extends SkillServlet {


    public AlexaSkill() {
        super(getSkill());
    }

    private static Skill getSkill() {
        return Skills.custom().build();
    }
}
