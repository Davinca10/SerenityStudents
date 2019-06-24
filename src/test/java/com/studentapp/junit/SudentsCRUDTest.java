package com.studentapp.junit;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import com.studentapp.cucumber.serenity.StudentSerenitySteps;
import com.studentapp.model.StudentClass;
import com.studentapp.testbase.TestBase;
import com.studentapp.utils.ReuseableSpecifications;
import com.studentapp.utils.TestUtils;

import io.restassured.http.ContentType;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.annotations.Title;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

@RunWith(SerenityRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SudentsCRUDTest extends TestBase {
	
	static String firstName = "Susan"+TestUtils.getRandomValue();
	static String lastName = "Damarys"+TestUtils.getRandomValue();
	static String programme = "Nurce"+TestUtils.getRandomValue();
	static String email    =	 TestUtils.getRandomValue()+"123www@gmailcom";
	static int studentId;
	
	@Steps
	StudentSerenitySteps steps;
	@Title("This test will create a new student")
	@Test
	public void test001() {
		
		ArrayList<String>courses = new ArrayList<String>();
		courses.add("Java");
		courses.add("C++");
		
		steps.createStudent(firstName, lastName, email, programme, courses)
		.statusCode(201)
		.spec(ReuseableSpecifications.getGenericResponseSpec());
	}
	@Title("Verify if the student was added to the application")
	@Test
	public void test002() {
		
		HashMap <String,Object> value = steps.getStudentInfoByFirstName(firstName);
		
		assertThat(value,hasValue(firstName));
		studentId=  (int) value.get("id");
	}
	
	@Title ("Update user information and verify the update infomation!")
	@Test
	public void test003() {
		
	
		ArrayList<String>courses = new ArrayList<String>();
		courses.add("Java");
		courses.add("C++");
		
		firstName = firstName+"_update"; 
		
		steps.updateStudent(studentId, firstName, lastName, email, programme, courses);
		
		HashMap <String,Object> value = steps.getStudentInfoByFirstName(firstName);
				
				assertThat(value,hasValue(firstName));
				studentId=  (int) value.get("id");
		
	}
	
	@Title ("Delete the student and verify if the student is delete")
	@Test
	public void test004(){
	
		steps.deleteStudent(studentId);
		steps.getStudentById(studentId).statusCode(404);
	}
	

}
 