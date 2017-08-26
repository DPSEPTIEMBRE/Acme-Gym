package services;

import java.util.List;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.util.Assert;

import security.LoginService;
import utilities.AbstractTest;
import domain.Activity;
import domain.Gym;
import domain.Trainer;

@Transactional
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@TransactionConfiguration(defaultRollback = true)
public class TrainerServiceTest extends AbstractTest {

	//The SUT

	@Autowired
	private TrainerService	trainerService;
	
	@Autowired
	private LoginService loginService;
	
	@Autowired
	private GymService gymService;
	
	@Autowired
	private ActivityService activityService;


	//Templates

	/*
	 * 6.3: A manager must be able to list the trainers that are registered in the system 
	 * 		and search for them using a single key word that must be contained in their names or surnames.
	 * 
	 */
	public void trainerListSearchTemplate(final String username, final Integer trainerID, final Class<?> expected){
		Class<?> caught = null;
		
		try{
			this.authenticate(username);
			
			Assert.isTrue(username == "manager1" || username == "manager2");
			trainerService.findAll();
			trainerService.findOne(trainerID);
			
			this.unauthenticate();

		} catch (final Throwable oops) {

			caught = oops.getClass();

		}

		this.checkExceptions(expected, caught);
	}
	
	/*
	 * 6.4: Register a new trainer to the system as manager.
	 * 
	 */
	public void trainerRegisterTemplate(final String username, final String newUsername, final String password, String actorName, String surname, String phone, String email, String postalAddress, String city, String country, final Class<?> expected) {
		Class<?> caught = null;

		try {
			this.authenticate(username);
			
			Assert.isTrue(username == "manager1" || username == "manager2");
			Trainer res = trainerService.create();

			Assert.notNull(newUsername);
			Assert.notNull(password);
			if(phone != null){
				Assert.isTrue(phone.matches("(\\+\\d{2} \\(\\d{1,3}\\) \\d{4,})|(\\+\\d{2} \\d{4,})"));
			}
			Assert.notNull(email);
			Assert.notNull(actorName);
			Assert.notNull(surname);

			res.getUserAccount().setUsername(newUsername);
			res.getUserAccount().setPassword(password);
			res.setActorName(actorName);
			res.setSurname(surname);
			res.setCity(city);
			res.setCountry(country);
			res.setEmail(email);
			res.setPhone(phone);
			res.setPostalAddress(postalAddress);

			trainerService.save(res);
			
			this.unauthenticate();

		} catch (final Throwable oops) {

			caught = oops.getClass();

		}

		this.checkExceptions(expected, caught);
	}
	
	/*
	 * 6.5: Associate a trainer with one of the manager's gyms.
	 * 
	 */
	public void trainerAssociationToGymTemplate(final String username, final Integer gymID, final Class<?> expected) {
		Class<?> caught = null;

		try {
			this.authenticate(username);
			
			Assert.isTrue(username == "manager1" || username == "manager2");
			Assert.notNull(gymID);
			Trainer t = trainerService.create();
			Gym g = gymService.findOne(gymID);
			List<Trainer> list = g.getTrainers();
			list.add(t);
			
			this.unauthenticate();

		} catch (final Throwable oops) {

			caught = oops.getClass();

		}

		this.checkExceptions(expected, caught);
	}
	
	/*
	 * 6.6: Associate a trainer with an activity in one of the manager's gyms.
	 * 
	 */
	public void trainerAssociationToActivityTemplate(final String username, final Integer activityID, final Class<?> expected) {
		Class<?> caught = null;

		try {
			this.authenticate(username);
			
			Assert.isTrue(username == "manager1" || username == "manager2");
			Assert.notNull(activityID);
			Trainer t = trainerService.create();
			Activity a = activityService.findOne(activityID);
			List<Trainer> list = a.getTrainers();
			list.add(t);
			
			this.unauthenticate();

		} catch (final Throwable oops) {

			caught = oops.getClass();

		}

		this.checkExceptions(expected, caught);
	}

	//Drivers

	@Test
	public void trainerListSearchDriver(){
	
		final Object testingData[][] = {
				
				//Test #01: Accessing as manager. Expected true.
				{"manager1", 46, null},
				
				//Test #02: Accessing as anonymous user. Expected false.
				{null, 46, IllegalArgumentException.class},
				
				//Test #03: Accessing a non existing trainer. Expected false.
				{"manager1", null, IllegalArgumentException.class},

		};
		for (int i = 0; i < testingData.length; i++)
			this.trainerListSearchTemplate((String) testingData[i][0], (Integer) testingData[i][1], (Class<?>) testingData[i][2]);
	}
	
	@Test
	public void trainerRegisterDriver() {

		final Object testingData[][] = {
				
			//Test #01: All parameters correct. Expected true.
			{"manager1", "trainerTest", "trainerTest", "actorName", "surname", "+34 (29) 1259", "trainer@mail.com", "address", "city", "country", null},
			
			//Test #02: All fields empty. Expected false.
			{null, null, null, null, null, null, null, null, null, null, IllegalArgumentException.class},
			
			//Test #03: Phone number doesn't match pattern. Expected false.
			{"manager1", "trainerTest", "trainerTest", "actorName", "surname", "6824560", "trainer@mail.com", "address", "city", "country", IllegalArgumentException.class}

		};
		for (int i = 0; i < testingData.length; i++)
			this.trainerRegisterTemplate((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (String) testingData[i][6],
				(String) testingData[i][7], (String) testingData[i][8], (String) testingData[i][9], (Class<?>) testingData[i][10]);
	}
	
	@Test
	public void trainerAssociationToGymDriver() {

		final Object testingData[][] = {
				
			//Test #01: All parameters correct. Expected true.
			{"manager1", 42, null},
			
			//Test #02: Access attempt by unauthorized user. Expected false.
			{"trainer1", 42, IllegalArgumentException.class},
			
			//Test #03: Attempt to associate to a nonexistent gym. Expected false.
			{"manager1", null, IllegalArgumentException.class},
			
		};
		for (int i = 0; i < testingData.length; i++)
			this.trainerAssociationToGymTemplate((String) testingData[i][0], (Integer) testingData[i][1], (Class<?>) testingData[i][2]);
	}
	
	@Test
	public void trainerAssociationToActivityDriver() {

		final Object testingData[][] = {
				
			//Test #01: All parameters correct. Expected true.
			{"manager1", 48, null},
			
			//Test #02: Access attempt by anonymous user. Expected false.
			{null, 48, IllegalArgumentException.class},
			
			//Test #03: Attempt to associate to an activity that doesn't exist. Expected false.
			{"manager1", null, IllegalArgumentException.class},
			
		};
		for (int i = 0; i < testingData.length; i++)
			this.trainerAssociationToActivityTemplate((String) testingData[i][0], (Integer) testingData[i][1], (Class<?>) testingData[i][2]);
	}

}
