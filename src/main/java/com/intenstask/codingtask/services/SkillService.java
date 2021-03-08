package com.intenstask.codingtask.services;

import com.intenstask.codingtask.dto.SkillDTO;
import com.intenstask.codingtask.exceptions.SkillNotFoundException;

import java.util.List;
import java.util.Optional;

public interface SkillService {

    List<SkillDTO> getAllSkills()throws SkillNotFoundException;
    Optional<SkillDTO> addSkill(SkillDTO skillDto);
    void deleteSkill(String skillName) throws SkillNotFoundException;

}
