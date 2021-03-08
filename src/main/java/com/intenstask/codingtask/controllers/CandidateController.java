package com.intenstask.codingtask.controllers;

import com.intenstask.codingtask.dto.CandidateDTO;
import com.intenstask.codingtask.exceptions.CandidateAlreadyExistsException;
import com.intenstask.codingtask.exceptions.CandidateNotFoundException;
import com.intenstask.codingtask.exceptions.EmailNotFoundException;
import com.intenstask.codingtask.exceptions.SkillNotFoundException;
import com.intenstask.codingtask.services.CandidateService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/candidates")
@Api(value = "candidates", description = "Api for handling candidates")
public class CandidateController {

    @Autowired
    private CandidateService candidateService;

    @Autowired
    public CandidateController(CandidateService candidateService) {
        this.candidateService = candidateService;
    }


    @GetMapping("/{name}")
    public ResponseEntity<Object> searchCandidateByName(@PathVariable String name) {
        try {
            return ResponseEntity.ok(candidateService.searchCandidateByName(name));
        } catch (CandidateNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Candidate not found.");
        }
    }

    @PostMapping("/add")
    @ApiOperation(value = "Add candidates", consumes = "application/json", produces = "application/json", httpMethod = "POST")
    public ResponseEntity<Object> addCandidate(@RequestBody CandidateDTO candidateDto) {
        try {
            candidateDto = candidateService.addCandidate(candidateDto).get();
            return ResponseEntity.ok(candidateDto);
        } catch (EmailNotFoundException emailEx) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please enter your email.");
        } catch (CandidateAlreadyExistsException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Candidate already exists.");
        }
    }

    @DeleteMapping("/delete/{email}")
    public ResponseEntity deleteCandidate(@PathVariable String email) {
        try {
            candidateService.removeCandidate(email);
            return ResponseEntity.ok().build();
        } catch (CandidateNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Candidate not found.");
        }
    }

    @GetMapping("/all")
    public ResponseEntity findAllCandidates() {
        return ResponseEntity.ok(candidateService.findAll());
    }

    @PostMapping("/searchbyskills")
    public ResponseEntity<Object> searchCandidatesBySkills(@RequestBody List<String> skills) throws CandidateNotFoundException {
        try {
            return ResponseEntity.ok(candidateService.searchCandidatesBySkills(skills));
        } catch (CandidateNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Candidate not found.");
        }
    }

    @RequestMapping(value = "/addskill/{skillName}", method = RequestMethod.PUT)
    public ResponseEntity<Object> addSkillToCandidate(@RequestBody CandidateDTO candidateDto, @PathVariable String skillName) {
        try {
            candidateDto = candidateService.updateCandidateWithSkill(candidateDto, skillName).get();
            return ResponseEntity.ok(candidateDto);
        } catch (CandidateNotFoundException candidateEx) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Candidate not found.");
        } catch (SkillNotFoundException skillEx) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Skill not found.");
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Candidate already has this skill.");

        }
    }

    @RequestMapping(value = "/removeskill/{skillName}", method = RequestMethod.PUT)
    public ResponseEntity<Object> removeSkillFromCandidate(@RequestBody CandidateDTO candidateDto, @PathVariable String skillName) {
        try {
            candidateDto = candidateService.removeSkillFromCandidate(candidateDto, skillName).get();
            return ResponseEntity.ok(candidateDto);
        } catch (CandidateNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Candidate not found.");
        } catch (SkillNotFoundException skillEx) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Skill not found.");
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Candidate does not have this skill.");

        }
    }


}
