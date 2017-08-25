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
import domain.Customer;

@Transactional
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@TransactionConfiguration(defaultRollback = true)
public class CustomerServiceTest extends AbstractTest {

	//The SUT

	@Autowired
	private CustomerService customerService;
	

	//Templates

	/*
	 * 4.1: An actor who is not authenticated must be able to register as a customer. 
	 */
	public void customerRegisterTemplate(final String username, final String password, String actorName, String surname, String city, String country, String email, String phone, String postalAddress, final Class<?> expected) {
		Class<?> caught = null;

		try {

			Customer res = customerService.create();

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

			customerService.save(res);

		} catch (final Throwable oops) {

			caught = oops.getClass();

		}

		this.checkExceptions(expected, caught);
	}

	//Drivers

	@Test
	public void customerRegisterDriver() {

		final Object testingData[][] = {
				
			//Test #01: All parameters correct. Expected true.
			{"customerTest", "customerTest", "actorName", "surname", "city", "country", "customer@mail.com", "+34 (29) 1259", "address", null},
			
			//Test #02: All fields empty. Expected false.
			{null, null, null, null, null, null, null, null, null, IllegalArgumentException.class},
			
			//Test #03: Phone number doesn't match pattern. Expected false.
			{"customerTest", "customerTest", "actorName", "surname", "city", "country", "customer@mail.com", "6824560", "address", IllegalArgumentException.class}

		};
		for (int i = 0; i < testingData.length; i++)
			this.customerRegisterTemplate((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (String) testingData[i][6],
				(String) testingData[i][7], (String) testingData[i][8], (Class<?>) testingData[i][9]);
	}
}
