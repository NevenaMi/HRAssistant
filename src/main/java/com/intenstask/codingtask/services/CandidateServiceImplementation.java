package com.intenstask.codingtask.services;

import com.intenstask.codingtask.dto.CandidateDTO;
import com.intenstask.codingtask.entities.Candidate;
import com.intenstask.codingtask.entities.Skill;
import com.intenstask.codingtask.exceptions.CandidateAlreadyExistsException;
import com.intenstask.codingtask.exceptions.CandidateNotFoundException;
import com.intenstask.codingtask.exceptions.EmailNotFoundException;
import com.intenstask.codingtask.exceptions.SkillNotFoundException;
import com.intenstask.codingtask.repositories.CandidateRepository;
import com.intenstask.codingtask.repositories.SkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CandidateServiceImplementation implements CandidateService{

    private CandidateRepository candidateRepository;
    private SkillRepository skillRepository;

    @Autowired
    public CandidateServiceImplementation(CandidateRepository candidateRepository, SkillRepository skillRepository) {
        this.candidateRepository = candidateRepository;
        this.skillRepository = skillRepository;
    }

    private Optional<Candidate> candidate;

    @Override
    @Transactional
    public List<CandidateDTO> searchCandidateByName(String name) throws CandidateNotFoundException {
        List<Candidate> listCandidates = candidateRepository.findCandidateByName(name);
        if(listCandidates.isEmpty()){
            throw new CandidateNotFoundException("Candidate not found");
        }
        List<CandidateDTO> listCandidatesDTO = new ArrayList<CandidateDTO>();
       listCandidatesDTO = listCandidateToListCandidateDTO(listCandidates);
        return listCandidatesDTO;
    }

    @Override
    @Transactional
    public Optional<CandidateDTO> addCandidate(CandidateDTO candidateDTO) throws EmailNotFoundException, CandidateAlreadyExistsException {
        if(candidateDTO.getEmail() == null){
            throw new EmailNotFoundException("There is no email entered.");
        }
        Optional<Candidate> candidateByEmail = candidateRepository.findCandidateByEmail(candidateDTO.getEmail());
        if(!candidateByEmail.isPresent()) {
            Candidate candidate = CandidateDTO.candidateDTOToCandidateEntity(candidateDTO);
            this.candidate = Optional.of(candidateRepository.save(candidate));
        }else {
            throw new CandidateAlreadyExistsException("Candidate already exists.");
        }
        return Optional.of(CandidateDTO.candidateEntityToCandidateDTO(this.candidate.get()));
    }

    @Override
    @Transactional
    public void removeCandidate(String email) throws CandidateNotFoundException {
        this.candidate = candidateRepository.findCandidateByEmail(email);
        if(!this.candidate.isPresent()){
            throw new CandidateNotFoundException("Candidate not found");
        }
        candidateRepository.delete(this.candidate.get());
    }

    @Override
    @Transactional
    public List<CandidateDTO> findAll(){
        List<Candidate> candidateList = candidateRepository.findAll();
        List<CandidateDTO> candidateDTOList = listCandidateToListCandidateDTO(candidateList);
        return candidateDTOList;
    }

    @Override
    @Transactional
    public List<CandidateDTO> searchCandidatesBySkills(List<String> skillNames) throws CandidateNotFoundException{
        List<Skill> skills = skillRepository.findSkillsByNameIn(skillNames);
        List<Candidate> listCandidates = candidateRepository.findCandidatesBySkillsIn(skills);
        listCandidates = editCandidatesList(listCandidates);
        if(listCandidates.isEmpty()){
            throw new CandidateNotFoundException("Candidate not found");
        }
        List<CandidateDTO> listCandidatesDTO = new ArrayList<CandidateDTO>();
        listCandidatesDTO = listCandidateToListCandidateDTO(listCandidates);
        return listCandidatesDTO;

    }

    @Override
    @Transactional
    public Optional<CandidateDTO> updateCandidateWithSkill(CandidateDTO candidateDto, String skillName) throws CandidateNotFoundException, SkillNotFoundException{
        Optional<Skill> skill = skillRepository.findSkillByName(skillName);
        if(!skill.isPresent()){
            throw new SkillNotFoundException("Skill not found");
        }
        this.candidate = candidateRepository.findCandidateByEmail(candidateDto.getEmail());
        if(!this.candidate.isPresent()){
            throw new CandidateNotFoundException("Candidate not found");
        }
        this.candidate.get().addSkill(skill.get());
        return Optional.of(CandidateDTO.candidateEntityToCandidateDTO(this.candidate.get()));
    }

    @Override
    @Transactional
    public Optional<CandidateDTO> removeSkillFromCandidate(CandidateDTO candidateDto, String skillName)throws CandidateNotFoundException, SkillNotFoundException {
        Optional<Skill> skill = skillRepository.findSkillByName(skillName);
        if(!skill.isPresent()){
            throw new SkillNotFoundException("Skill not found");
        }
        this.candidate = candidateRepository.findCandidateByEmail(candidateDto.getEmail());
        if(!this.candidate.isPresent()){
            throw new CandidateNotFoundException("Candidate not found");
        }
        this.candidate.get().removeSkill(skill.get());
        return Optional.of(CandidateDTO.candidateEntityToCandidateDTO(this.candidate.get()));
    }

    private List<CandidateDTO> listCandidateToListCandidateDTO(List<Candidate> listCandidates){
        List<CandidateDTO> listCandidatesDTO = new ArrayList<CandidateDTO>();
        for(int i = 0; i < listCandidates.size(); i++){
            listCandidatesDTO.add(i,CandidateDTO.candidateEntityToCandidateDTO(listCandidates.get(i)));
        }
        return listCandidatesDTO;
    }

    private List<Candidate> editCandidatesList(List<Candidate> listCandidates){
        int listSize = listCandidates.size();
        for(int i = 0; i < listSize; i++){
            for(int j = i+1; j < listSize; j++){
                if(listCandidates.get(i).getEmail().equals(listCandidates.get(j).getEmail())){
                    listCandidates.remove(j);
                    j--;
                    listSize = listSize - 1;
                }
            }
        }
        return listCandidates;
    }

    private void printList(List<Candidate> list){
        List<CandidateDTO> candidateDto = listCandidateToListCandidateDTO(list);
        for(int j = 0; j <candidateDto.size(); j++){
            System.out.println(candidateDto.get(j).toString());
            }
    }


}
