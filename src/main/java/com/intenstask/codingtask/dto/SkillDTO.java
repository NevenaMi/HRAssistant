package com.intenstask.codingtask.dto;

import com.intenstask.codingtask.entities.Skill;

import javax.validation.constraints.NotBlank;

public class SkillDTO {

    private Long id;
    @NotBlank
    private String name;

    public SkillDTO(@NotBlank String name) {
        this.name = name;
    }

    public SkillDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static SkillDTO skillEntityToSkillDTO(Skill skill){

        SkillDTO skillDTO = new SkillDTO();
        skillDTO.setName(skill.getName());

        return skillDTO;
    }

    public static Skill skillDTOToSkillEntity(SkillDTO skillDTO){

        Skill skill = new Skill();
        skill.setName(skillDTO.getName());

        return skill;
    }
}
