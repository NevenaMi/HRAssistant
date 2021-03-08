package com.intenstask.codingtask;

import com.intenstask.codingtask.dto.CandidateDTO;
import com.intenstask.codingtask.entities.Candidate;
import com.intenstask.codingtask.exceptions.CandidateAlreadyExistsException;
import com.intenstask.codingtask.exceptions.CandidateNotFoundException;
import com.intenstask.codingtask.exceptions.EmailNotFoundException;
import com.intenstask.codingtask.repositories.CandidateRepository;
import com.intenstask.codingtask.repositories.SkillRepository;
import com.intenstask.codingtask.services.CandidateServiceImplementation;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

public class CandidateServiceImplTest {

    @InjectMocks
    CandidateServiceImplementation candidateServiceImplementation;

    @Mock
    private CandidateRepository candidateRepository;
    @Mock
    private SkillRepository skillRepository;

    private CandidateDTO candidateDTO;

    private Candidate candidate;

    private Date date;



    @Before
    public void setUp(){
        candidateServiceImplementation = new CandidateServiceImplementation(candidateRepository, skillRepository);
        MockitoAnnotations.initMocks(this);

        date= new Date();

    }

    @Test
    public void searchCandidatesByNameTest() throws CandidateNotFoundException {
        Candidate candidate1 = new Candidate("Mike", "mike@mail.com");
        Candidate candidate2 = new Candidate("Ann", "ann@mail.com");
        Candidate candidate3 = new Candidate("Michael", "mikel@mail.com");
        Candidate candidate4 = new Candidate("Mike", "mike33@mail.com");
        List<Candidate> list = new ArrayList<Candidate>();
        list.add(candidate1); list.add(candidate2); list.add(candidate3); list.add(candidate4);
        List<Candidate> retList = new ArrayList<>();
        retList.add(candidate1); retList.add(candidate4);

        when(candidateRepository.findCandidateByName("Mike")).thenReturn(retList);
        System.out.println(retList.size());
        List<CandidateDTO> resultListDto = new ArrayList<>();
        resultListDto = candidateServiceImplementation.searchCandidateByName("Mike");
        for(CandidateDTO candidateDTO : resultListDto) {
            assert(candidateDTO.getName().equals("Mike"));
        }
        assert(resultListDto.size() == 2);
    }

    //doesn't work because function candidateRepository.save() returns null; mocking static method "candidateDTOToCandidateEntity" may be good solution but I didn't find a way to do this
//    @Test()
//    public void addCandidateHappyPath() throws CandidateAlreadyExistsException, EmailNotFoundException{
//        candidateDTO = new CandidateDTO("Mike", "mike@mail.com");
//        candidate = new Candidate("Mike", "mike@mail.com");
//        candidate.setId(1L);
//        when(candidateRepository.findCandidateByEmail(candidateDTO.getEmail())).thenReturn(Optional.empty());
//        when(candidateRepository.save(candidate)).thenReturn(candidate);
//        when(CandidateDTO.candidateDTOToCandidateEntity(candidateDTO)).thenReturn(candidate);
//        candidateDTO = candidateServiceImplementation.addCandidate(candidateDTO).get();
//
//        assert(candidateDTO.getEmail().equals("mike@gmail.com"));
//        assert(candidateDTO.getName().equals("Mike"));
//
//    }

    @Test(expected = CandidateAlreadyExistsException.class)
    public void addCandidateWhenCandidateIsAlreadyPresent() throws CandidateAlreadyExistsException, EmailNotFoundException {
        candidateDTO = new CandidateDTO("Mike", "mike@mail.com");
        candidate = new Candidate("Mike", "mike@mail.com");
        when(candidateRepository.findCandidateByEmail(candidateDTO.getEmail())).thenReturn(Optional.of(candidate));
        when(candidateRepository.save(candidate)).thenReturn(candidate);
        candidateDTO = candidateServiceImplementation.addCandidate(candidateDTO).get();

    }

    @Test(expected = EmailNotFoundException.class)
    public void addCandidateMissingEmail() throws Exception{
        candidateDTO = new CandidateDTO();
        candidateDTO.setName("Nenad");

        candidateServiceImplementation.addCandidate(candidateDTO);
    }

}
