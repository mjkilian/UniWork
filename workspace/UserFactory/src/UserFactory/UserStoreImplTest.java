package UserFactory;


import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class UserStoreImplTest {
	static UserStoreImpl userstore; 
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		userstore = new UserStoreImpl("users.ser","students.ser","employers.ser","visitors.ser");
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}


	
	@Before
	public void setUp() throws Exception {
	}

	@Test 
	public void addGetUser(){
		User u1 = new User("Kilian", "Michael", "1003819", "password");
		userstore.addUser("Kilian", "Michael", "1003819", "password");
		User u2 = userstore.getUser("1003819", "password");
		assertTrue(u1.equals(u2));
	}
	
	@Test 
	public void addGetStudent(){
		Student s1 = new Student("Kilian", "Michael", "1003819k@student.gla.ac.uk", "password","1003819","SE");
		userstore.addStudent("Kilian", "Michael", "1003819k@student.gla.ac.uk", "password","1003819","SE");
		Student s2 = userstore.getStudent("1003819k@student.gla.ac.uk", "password");
		assertTrue(s1.equals(s2));
	}
	
	@Test 
	public void addGetEmployer(){
		Employer s1 = new Employer("BT", "bt","password");
		userstore.addEmployer("BT","bt","password");
		Employer s2 = userstore.getEmployer("bt", "password");
		assertTrue(s1.equals(s2));
	}
	
	@Test 
	public void addGetVisitor(){
		Visitor u1 = new Visitor("Kilian", "Michael", "1003819", "password");
		userstore.addVisitor("Kilian", "Michael", "1003819", "password");
		Visitor u2 = userstore.getVisitor("1003819", "password");
		assertTrue(u1.equals(u2));
	}
	@After
	public void tearDown() throws Exception {
		userstore = null;
	}


}