package com.intenstask.codingtask;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;


@SpringBootApplication
public class CodingtaskApplication {

	public static void main(String[] args) {
		SpringApplication.run(CodingtaskApplication.class, args);

//		Calendar cal = Calendar.getInstance();
//		cal.set(1985, 2, 8);
//		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
//		System.out.println(simpleDateFormat.format(cal.getTime()));
//		System.out.printf("%1$s %2$tB %2$td, %2$tY", "Date of birth:", cal.getTime());
//		System.out.println("");
//		Date dateOfBirth = new Date();
		//good!!!
//		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
//		ParsePosition pos = new ParsePosition(0);
//		String dateOfBirth = "1978/08/06";
//		Date date = new Date();
//		date = simpleDateFormat.parse(dateOfBirth, pos);
//
//		Candidate candidate = new Candidate();
//		candidate.setName("Will");
//		candidate.setDateOfBirth(date);
//		candidate.setContactNumber("+3814597625");
//		candidate.setEmail("email@gmail.com");
//		candidate.setSkills(new ArrayList<>());
//		candidate.addSkill(new Skill("English" ));
//		candidate.addSkill(new Skill("German"));

//		candidate.addSkill(new Skill("English" ));
//		candidate.addSkill(new Skill("German"));
//
//		System.out.println(simpleDateFormat.format(date));
//		System.out.println(candidate.toString());
	}

}
