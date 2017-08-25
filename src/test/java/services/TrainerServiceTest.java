package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.util.Assert;

import utilities.AbstractTest;
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


	//Templates

	/*
	 * 6.3: List the trainers that are registered in the system and search for them using a single key word that must be contained in their names or surnames
	 * 
	 */
	public void trainerListSearchTemplate(final String username, final Integer trainerID, final Class<?> expected){
		Class<?> caught = null;
		
		try{
			authenticate(username);
			
			trainerService.findAll();
			trainerService.findOne(trainerID);
			
			unauthenticate();

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
			authenticate(username);
			
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
			
			unauthenticate();

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
				{"manager1", 90, NullPointerException.class},

		};
		for (int i = 0; i < testingData.length; i++)
			this.trainerListSearchTemplate((String) testingData[i][0], (Integer) testingData[i][1], (Class<?>) testingData[i][2]);
	}
	
	@Test
	public void trainerRegisterDriver() {

		final Object testingData[][] = {
				
			//Test #01: All parameters correct. Expected true.
			{"manager1", "trainerTest", "trainerTest", "actorName", "surname", "city", "country", "trainer@mail.com", "+34 (29) 1259", "address", null},
			
			//Test #02: All fields empty. Expected false.
			{null, null, null, null, null, null, null, null, null, IllegalArgumentException.class},
			
			//Test #03: Phone number doesn't match pattern. Expected false.
			{"manager1", "trainerTest", "trainerTest", "actorName", "surname", "city", "country", "trainer@mail.com", "6824560", "address", IllegalArgumentException.class}

		};
		for (int i = 0; i < testingData.length; i++)
			this.trainerRegisterTemplate((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (String) testingData[i][6],
				(String) testingData[i][7], (String) testingData[i][8], (String) testingData[i][9], (Class<?>) testingData[i][10]);
	}

}
