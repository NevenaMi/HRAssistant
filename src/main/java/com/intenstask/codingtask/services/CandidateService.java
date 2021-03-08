package com.intenstask.codingtask.services;

import com.intenstask.codingtask.dto.CandidateDTO;
import com.intenstask.codingtask.exceptions.CandidateAlreadyExistsException;
import com.intenstask.codingtask.exceptions.CandidateNotFoundException;
import com.intenstask.codingtask.exceptions.EmailNotFoundException;
import com.intenstask.codingtask.exceptions.SkillNotFoundException;

import java.util.List;
import java.util.Optional;

public interface CandidateService {

    List<CandidateDTO> findAll();
    List<CandidateDTO> searchCandidateByName(String name) throws CandidateNotFoundException;
    Optional<CandidateDTO>  addCandidate(CandidateDTO candidateDTO) throws EmailNotFoundException, CandidateAlreadyExistsException;
    void removeCandidate(String email) throws CandidateNotFoundException;
    List<CandidateDTO> searchCandidatesBySkills(List<String> skillNames) throws CandidateNotFoundException;
    Optional<CandidateDTO> updateCandidateWithSkill(CandidateDTO candidateDto, String skillName) throws CandidateNotFoundException, SkillNotFoundException;
    Optional<CandidateDTO> removeSkillFromCandidate(CandidateDTO candidateDto, String skillName) throws CandidateNotFoundException, SkillNotFoundException;

}
