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
import domain.Manager;

@Transactional
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@TransactionConfiguration(defaultRollback = true)
public class ManagerServiceTest extends AbstractTest {

	//The SUT

	@Autowired
	private ManagerService managerService;


	//Templates

	/*
	 * 4.1: An actor who is not authenticated must be able to register as a manager. 
	 */
	public void managerRegisterTemplate(final String username, final String password, String actorName, String surname, String city, String country, String email, String phone, String postalAddress, final Class<?> expected) {
		Class<?> caught = null;

		try {

			Manager res = managerService.create();

			Assert.notNull(username);
			Assert.notNull(password);
			if(phone != null){
				Assert.isTrue(phone.matches("(\\+\\d{2} \\(\\d{1,3}\\) \\d{4,})|(\\+\\d{2} \\d{4,})"));
			}
			Assert.notNull(email);
			Assert.notNull(actorName);
			Assert.notNull(surname);

			res.getUserAccount().setUsername(username);
			res.getUserAccount().setPassword(password);
			res.setActorName(actorName);
			res.setSurname(surname);
			res.setCity(city);
			res.setCountry(country);
			res.setEmail(email);
			res.setPhone(phone);
			res.setPostalAddress(postalAddress);

			managerService.save(res);

		} catch (final Throwable oops) {

			caught = oops.getClass();

		}

		this.checkExceptions(expected, caught);
	}
	
	/*
	 * 5.2: An actor who is authenticated must be able to edit personal data. 
	 */
	public void managerEditTemplate(final String username, final Integer id, String actorName, String surname, String city, String country, String email, String phone, String postalAddress, final Class<?> expected) {
		Class<?> caught = null;

		try {
			this.authenticate(username);
			
			Assert.isTrue(username != null);
			Manager m = managerService.findOne(id);
			
			if(phone != null){
				Assert.isTrue(phone.matches("(\\+\\d{2} \\(\\d{1,3}\\) \\d{4,})|(\\+\\d{2} \\d{4,})"));
			}
			Assert.notNull(email);
			Assert.notNull(actorName);
			Assert.notNull(surname);

			m.setActorName(actorName);
			m.setSurname(surname);
			m.setCity(city);
			m.setCountry(country);
			m.setEmail(email);
			m.setPhone(phone);
			m.setPostalAddress(postalAddress);
			
			managerService.save(m);
			
			this.unauthenticate();

		} catch (final Throwable oops) {

			caught = oops.getClass();

		}

		this.checkExceptions(expected, caught);
	}

	/*
	 * 8.1: An administrator must be able to ban or unban a manager.
	 */
	public void managerBanTemplate(final String username, final Integer managerID, final Class<?> expected) {
		Class<?> caught = null;

		try {
			this.authenticate(username);
			
			Assert.isTrue(username == "admin");
			Manager m = managerService.create();
			Assert.notNull(managerID);
			m.getUserAccount().setActivate(false);
			
			this.unauthenticate();

		} catch (final Throwable oops) {

			caught = oops.getClass();

		}

		this.checkExceptions(expected, caught);
	}
	
	//Drivers

	@Test
	public void managerRegisterDriver() {

		final Object testingData[][] = {
				
			//Test #01: All parameters correct. Expected true.
			{"managerTest", "managerTest", "actorName", "surname", "city", "country", "manager@mail.com", "+34 (29) 1259", "address", null},
			
			//Test #02: All fields empty. Expected false.
			{null, null, null, null, null, null, null, null, null, IllegalArgumentException.class},
			
			//Test #03: Phone number doesn't match pattern. Expected false.
			{"managerTest", "managerTest", "actorName", "surname", "city", "country", "manager@mail.com", "6824560", "address", IllegalArgumentException.class}

		};
		for (int i = 0; i < testingData.length; i++)
			this.managerRegisterTemplate((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (String) testingData[i][6],
				(String) testingData[i][7], (String) testingData[i][8], (Class<?>) testingData[i][9]);
	}
	
	@Test
	public void managerEditDriver() {

		final Object testingData[][] = {
				
			//Test #01: All parameters correct. Expected true.
			{"manager1", 40, "actorName1", "surname1", "city1", "country1", "manager1@mail.com", "+35 (29) 1259", "address1", null},
			
			//Test #02: All fields empty. Expected false.
			{"manager1", 40, null, null, null, null, null, null, null, IllegalArgumentException.class},
			
			//Test #03: Phone number doesn't match pattern. Expected false.
			{"manager1", 40, "actorName", "surname", "city", "country", "manager@mail.com", "6824560", "address", IllegalArgumentException.class}

		};
		for (int i = 0; i < testingData.length; i++)
			this.managerEditTemplate((String) testingData[i][0], (Integer) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4],
					(String) testingData[i][5], (String) testingData[i][6], (String) testingData[i][7], (String) testingData[i][8], (Class<?>) testingData[i][9]);
	}
	
	@Test
	public void managerBanDriver() {

		final Object testingData[][] = {
				
			//Test #01: Access with authorized account. Expected true.
			{"admin", 40, null},
			
			//Test #02: Access with unauthorized account. Expected false.
			{"customer2", 40, IllegalArgumentException.class},
			
			//Test #03: Attempt to ban an unexistent manager. Expected false.
			{"admin", null, IllegalArgumentException.class}

		};
		for (int i = 0; i < testingData.length; i++)
			this.managerBanTemplate((String) testingData[i][0], (Integer) testingData[i][1], (Class<?>) testingData[i][2]);
	}
	
}
