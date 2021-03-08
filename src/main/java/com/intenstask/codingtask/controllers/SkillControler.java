package com.intenstask.codingtask.controllers;

import com.intenstask.codingtask.dto.SkillDTO;
import com.intenstask.codingtask.exceptions.SkillNotFoundException;
import com.intenstask.codingtask.services.SkillService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/skills")
@Api(value = "candidates", description = "Api for handling skills")
public class SkillControler {

    private SkillService skillService;

    @Autowired
    public SkillControler(SkillService skillService) {
        this.skillService = skillService;
    }

    @GetMapping("/all")
    public ResponseEntity<Object> getAllSkills() {
        try {
            return ResponseEntity.ok(skillService.getAllSkills());
        } catch (SkillNotFoundException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Skill not found.");
        }
    }

    @PostMapping("/add")
    public ResponseEntity<Object> addSkill(@RequestBody SkillDTO skillDto){
        try {
            skillDto = skillService.addSkill(skillDto).get();
            return ResponseEntity.ok(skillDto);
        } catch (RuntimeException ex){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Skill already exists.");
        }
    }

    @DeleteMapping("/delete/{skillName}")
    public ResponseEntity<Object> deleteSkill(@PathVariable String skillName) {
        try{
            skillService.deleteSkill(skillName);
            return ResponseEntity.ok(skillService.getAllSkills());
        } catch (SkillNotFoundException ex){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Skill not found.");
        }

    }



}
