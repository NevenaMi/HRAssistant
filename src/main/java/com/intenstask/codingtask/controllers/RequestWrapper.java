package com.intenstask.codingtask.controllers;

import com.intenstask.codingtask.entities.Candidate;
import com.intenstask.codingtask.entities.Skill;

public class RequestWrapper {

    private Candidate candidate;
    private Skill skill;

    public Candidate getCandidate() {
        return candidate;
    }

    public Skill getSkill() {
        return skill;
    }
}
