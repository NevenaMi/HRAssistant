package com.intenstask.codingtask.dto;

import com.intenstask.codingtask.entities.Candidate;
import com.intenstask.codingtask.entities.Skill;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class CandidateDTO {

    private Long id;
    @NotNull
    @NotEmpty
    @NotBlank
    private String name;
    private Date dateOfBirth;
    private String contactNumber;
    @Email
    private String email;
    private List<SkillDTO> skillsDto;

    public CandidateDTO(String name, Date dateOfBirth, String contactNumber, String email, List<SkillDTO> skills) {
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.contactNumber = contactNumber;
        this.email = email;
        this.skillsDto = skills;
    }

    public CandidateDTO(@NotNull String name, @Email String email) {
        this.name = name;
        this.email = email;
        this.skillsDto = new ArrayList<>();
    }

    public CandidateDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<SkillDTO> getSkills() {
        return skillsDto;
    }

    public void setSkills(List<SkillDTO> skills) {
        this.skillsDto = skills;
    }

    @Override
    public String toString() {
        return "CandidateDTO{" +
                " name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", skillsDto=" + skillsDto +
                '}';
    }

    public static CandidateDTO candidateEntityToCandidateDTO(Candidate candidate){
        
        CandidateDTO candidateDTO = new CandidateDTO();
        candidateDTO.setName(candidate.getName());
        candidateDTO.setDateOfBirth(candidate.getDateOfBirth());
        candidateDTO.setContactNumber(candidate.getContactNumber());
        candidateDTO.setEmail(candidate.getEmail());
        candidateDTO.setSkills(skillsToSkillsDTO(candidate));

        return candidateDTO;
    }

    public static Candidate candidateDTOToCandidateEntity(CandidateDTO candidateDTO){

        Candidate candidate = new Candidate();
        candidate.setName(candidateDTO.getName());
        candidate.setDateOfBirth(candidateDTO.getDateOfBirth());
        candidate.setContactNumber(candidateDTO.getContactNumber());
        candidate.setEmail(candidateDTO.getEmail());
        candidate.setSkills(skillsDTOToSkills(candidateDTO));

        return candidate;
    }

    public static List<SkillDTO> skillsToSkillsDTO(Candidate candidate){
        List<SkillDTO> skillsDto = new ArrayList<SkillDTO>();
        if(candidate.getSkills() == null){
            return null;
        }
        for(int i = 0; i < candidate.getSkills().size(); i++){
            skillsDto.add(SkillDTO.skillEntityToSkillDTO(candidate.getSkills().get(i)));
        }
        return skillsDto;
    }

    public static List<Skill> skillsDTOToSkills(CandidateDTO candidateDto){
        List<Skill> skills = new ArrayList<Skill>();
        if(candidateDto.getSkills() == null){
            return null;
        }
        for(int i = 0; i < candidateDto.getSkills().size(); i++){
            skills.add(SkillDTO.skillDTOToSkillEntity(candidateDto.getSkills().get(i)));
        }
        return skills;
    }


}
