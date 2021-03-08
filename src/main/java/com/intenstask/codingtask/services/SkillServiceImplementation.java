package com.intenstask.codingtask.services;

import com.intenstask.codingtask.dto.SkillDTO;
import com.intenstask.codingtask.entities.Skill;
import com.intenstask.codingtask.exceptions.SkillNotFoundException;
import com.intenstask.codingtask.repositories.SkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SkillServiceImplementation implements SkillService {

    private SkillRepository skillRepository;

    @Autowired
    public SkillServiceImplementation(SkillRepository skillRepository){
        this.skillRepository = skillRepository;
    }

    private Optional<Skill> skill;

    @Override
    public List<SkillDTO> getAllSkills() throws SkillNotFoundException{
        List<Skill> listSkills = skillRepository.findAll();
        if(listSkills.isEmpty()){
            throw new SkillNotFoundException("Skill not found");
        }
        List<SkillDTO> listSkillDTO = new ArrayList<>();
        for(int i = 0; i < listSkills.size(); i++){
            listSkillDTO.add(i,SkillDTO.skillEntityToSkillDTO(listSkills.get(i)));
        }
        return listSkillDTO;
    }

    @Override
    public Optional<SkillDTO> addSkill(SkillDTO skillDto) {
        Optional<Skill> skillByName = skillRepository.findSkillByName(skillDto.getName());
        if(!skillByName.isPresent()) {
            Skill skill = SkillDTO.skillDTOToSkillEntity(skillDto);
            this.skill = Optional.of(skillRepository.save(skill));
        }else {
            throw new RuntimeException("Skill already exists.");
        }
        return Optional.of(SkillDTO.skillEntityToSkillDTO(this.skill.get()));
    }

    @Override
    public void deleteSkill(String skillName) throws SkillNotFoundException{
        Optional<Skill> skillByName = skillRepository.findSkillByName(skillName);
        if(skillByName.isPresent()) {
            skillRepository.delete(skillByName.get());
        }else {
            throw new SkillNotFoundException("Skill not found");
        }
    }
}


