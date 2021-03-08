package com.intenstask.codingtask.repositories;


import com.intenstask.codingtask.entities.Candidate;
import com.intenstask.codingtask.entities.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CandidateRepository extends JpaRepository<Candidate, Long> {

    List<Candidate> findCandidateByName(String name);
    Optional<Candidate> findCandidateByEmail(String email);
    List<Candidate> findCandidatesBySkillsIn(List<Skill> skills);
}
