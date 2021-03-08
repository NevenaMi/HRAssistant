package com.intenstask.codingtask.entities;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "candidates")
public class Candidate {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @Temporal(TemporalType.DATE)
    private Date dateOfBirth;
    private String contactNumber;
    @Column(unique = true)
    private String email;

    @ManyToMany
    @JoinTable(name = "candidate_skill",
            joinColumns = @JoinColumn(name = "candidate_id"),
            inverseJoinColumns = @JoinColumn(name = "skill_id"),
    uniqueConstraints = @UniqueConstraint(name = "pk",columnNames = {"candidate_id","skill_id"}))
    private List<Skill> skills;


    public Candidate() {
    }

    public Candidate(String name, String email){
        this.name = name;
        this.email = email;
        this.dateOfBirth = new Date();
        this.skills = new ArrayList<>();
    }

    public Candidate(String name, Date dateOfBirth, String contactNumber, String email, ArrayList<Skill> skills) {
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.contactNumber = contactNumber;
        this.email = email;
        this.skills = skills;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public List<Skill> getSkills() {
        return skills;
    }

    public void setSkills(List<Skill> skills) {
        this.skills = skills;
    }

    public void addSkill(Skill skill){
        this.skills.add(skill);
    }

    public void removeSkill(Skill skill){
        this.skills.remove(skill);
    }

    public String printDate(Date date){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
        return simpleDateFormat.format(date);
    }

    @Override
    public String toString() {
        return "Candidate{ id = " + this.id + ", name = " + this.name + ", date of birth = " + printDate(this.dateOfBirth)
                + ", contact number = " + this.contactNumber + ", email = " + this.email + ", skills = { " + Arrays.toString(skills.toArray()) + " }";
    }
}
