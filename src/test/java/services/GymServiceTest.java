package services;

import java.util.List;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.util.Assert;

import security.LoginService;
import utilities.AbstractTest;
import domain.Customer;
import domain.Gym;
import domain.Manager;

@Transactional
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@TransactionConfiguration(defaultRollback = true)
public class GymServiceTest extends AbstractTest {

	//The SUT

	@Autowired
	private GymService gymService;
	
	@Autowired
	private ActivityService activityService;

	@Autowired
	private ManagerService managerService;
	
	@Autowired
	private LoginService loginService;


	//Templates

	/*
	 * 4.2: Browse the catalogue of gyms and navigate to the activities that they organise and the trainers who offer them.
	 */
	public void browseGymsTemplate(final String username, final Integer gymID, final Integer activityID, final Class<?> expected) {
		Class<?> caught = null;

		try {
			this.authenticate(username);

			gymService.findAll();
			gymService.activitiesByGym(gymID);
			activityService.trainersByActivity(activityID);

			this.unauthenticate();

		} catch (final Throwable oops) {

			caught = oops.getClass();

		}

		this.checkExceptions(expected, caught);
	}

	/*
	 * 6.1: An actor authenticated as manager must be able to manage his or her gyms, which includes listing,
	 * 		creating, editing and deleting them.
	 */
	public void manageGymTemplate(final String username, String logo, String name, String address,
			Double fee, String logo2, String name2, String address2, Double fee2, final Class<?> expected) {
		Class<?> caught = null;

		try {
			this.authenticate(username);
			
			//Listing
			Assert.isTrue(username == "manager1" || username == "manager2");
			Manager manager = managerService.create();
			managerService.gymsByManager(manager.getId());
			
			//Creating
			Gym res;
			res = gymService.create();

			Assert.notNull(logo);
			Assert.notNull(name);
			Assert.notNull(address);
			Assert.notNull(fee);
			Assert.isTrue(fee >= 0);

			res.setLogo(logo);
			res.setName(name);
			res.setAddress(address);
			res.setFee(fee);
			Assert.isTrue(fee >= 0);
			res.setIsDelete(false);
			List<Gym> list = manager.getGyms();
			list.add(res);
			gymService.save(res);
			
			//Editing
			res.setLogo(logo2);
			res.setName(name2);
			res.setAddress(address2);
			res.setFee(fee2);
			gymService.save(res);
			
			//Deleting
			gymService.delete(res);
			
			this.unauthenticate();

		} catch (final Throwable oops) {

			caught = oops.getClass();

		}

		this.checkExceptions(expected, caught);
	}
	
	/*
	 * 7.1: An actor authenticated as customer must be able to join or leave a gym.
	 */
	public void joinLeaveGymTemplate(final String username, final Integer customerID, final Integer gymID, final Class<?> expected) {
		Class<?> caught = null;

		try {
			this.authenticate(username);

			//Joining
			Assert.isTrue(username == "customer1" || username == "customer2");
			Customer c = (Customer) loginService.findActorByUserName(customerID);
			Gym g = gymService.findOne(gymID);
			List<Customer> list = g.getCustomers();
			list.add(c);	
			
			//Leaving
			list.remove(list.size()-1);

			this.unauthenticate();

		} catch (final Throwable oops) {

			caught = oops.getClass();

		}

		this.checkExceptions(expected, caught);
	}
	
	//Drivers

	@Test
	public void browseGymsDriver() {

		final Object testingData[][] = {
				
			//Test #01: Access by anonymous user. Expected true.
			{null, 42, 48, null},
			
			//Test #02: Access by anonymous user to non existing gym. Expected false.
			{null, null, 48, NullPointerException.class},
			
			//Test #03: Access by authorized user to non existing activity. Expected false.
			{"customer1", 42, null, NullPointerException.class}

		};
		for (int i = 0; i < testingData.length; i++)
			this.browseGymsTemplate((String) testingData[i][0], (Integer) testingData[i][1], (Integer) testingData[i][2], (Class<?>) testingData[i][3]);
	}

	@Test
	public void manageGymDriver() {

		final Object testingData[][] = {
				
			//Test #01: Access by manager, all parameters correct. Expected true.
			{"manager2", "https://i.ytimg.com/vi/iWm0beJM6Bk/maxresdefault.jpg", "gym", "gym street", 22.00,
				"https://i.ytimg.com/vi/iWm0beJM6Bk/maxresdefault.jpg", "gym2", "gym2 street", 1000.00, null},
				
			//Test #02: Access by anonymous user. Expected false.
			{null, "https://i.ytimg.com/vi/iWm0beJM6Bk/maxresdefault.jpg", "gym", "gym street", 22.00,
				"https://i.ytimg.com/vi/iWm0beJM6Bk/maxresdefault.jpg", "gym2", "gym2 street", 1000.00, IllegalArgumentException.class},
				
			//Test #03: Access by unauthorized user. Expected false.
			{"trainer1", "https://i.ytimg.com/vi/iWm0beJM6Bk/maxresdefault.jpg", "gym", "gym street", 22.00,
				"https://i.ytimg.com/vi/iWm0beJM6Bk/maxresdefault.jpg", "gym2", "gym2 street", 1000.00, IllegalArgumentException.class},
				
			//Test #04: Empty fields in creating. Expected false.
			{"manager1", null, null, null, null,
					"https://i.ytimg.com/vi/iWm0beJM6Bk/maxresdefault.jpg", "gym2", "gym2 street", 1000.00, IllegalArgumentException.class},
					
			//Test #05: Empty fields in editing. Expected false.
			{"manager1", "https://i.ytimg.com/vi/iWm0beJM6Bk/maxresdefault.jpg", "gym", "gym street", 22.00,
				null, null, null, null, ConstraintViolationException.class},
						
			//Test #06: Introduced negative value on fee in creation. Expected false.
			{"manager1", "https://i.ytimg.com/vi/iWm0beJM6Bk/maxresdefault.jpg", "gym", "gym street", -22.00,
				"https://i.ytimg.com/vi/iWm0beJM6Bk/maxresdefault.jpg", "gym2", "gym2 street", 1000.00, ConstraintViolationException.class},
				
			//Test #07: Introduced negative value on fee in editing. Expected false.
			{"manager1", "https://i.ytimg.com/vi/iWm0beJM6Bk/maxresdefault.jpg", "gym", "gym street", 22.00,
				"https://i.ytimg.com/vi/iWm0beJM6Bk/maxresdefault.jpg", "gym2", "gym2 street", -22.00, ConstraintViolationException.class},
							
			//Test #08: Introduced invalid logo URL in creation. Expected false.
			{"manager1", "logo", "gym", "gym street", 22.00,
					"https://i.ytimg.com/vi/iWm0beJM6Bk/maxresdefault.jpg", "gym2", "gym2 street", 1000.00, ConstraintViolationException.class},
				
			//Test #09 Introduced invalid logo URL in editing. Expected false.
			{"manager1", "https://i.ytimg.com/vi/iWm0beJM6Bk/maxresdefault.jpg", "gym", "gym street", 22.00,
				"logo", "gym2", "gym2 street", 1000.00, ConstraintViolationException.class},
								
			//Test #10: Introduced HTML code in name field on creation. Expected false.
			{"manager1", "https://i.ytimg.com/vi/iWm0beJM6Bk/maxresdefault.jpg", "<!DOCTYPE html><html><body><h1>This is heading 1</h1></body></html>", "gym street", 22.00,
				"https://i.ytimg.com/vi/iWm0beJM6Bk/maxresdefault.jpg", "gym2", "gym2 street", 1000.00, ConstraintViolationException.class},
									
			//Test #11: Introduced HTML code in address field on edition. Expected false.
			{"manager1", "https://i.ytimg.com/vi/iWm0beJM6Bk/maxresdefault.jpg", "gym", "gym street", 22.00,
				"https://i.ytimg.com/vi/iWm0beJM6Bk/maxresdefault.jpg", "gym2", "<!DOCTYPE html><html><body><h1>This is heading 1</h1></body></html>", 1000.00, ConstraintViolationException.class}

		};
		for (int i = 0; i < testingData.length; i++)
			this.manageGymTemplate((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2],
					(String) testingData[i][3], (Double) testingData[i][4], (String) testingData[i][5], (String) testingData[i][6],
					(String) testingData[i][7], (Double) testingData[i][8], (Class<?>) testingData[i][9]);
	}
	
	@Test
	public void joinLeaveGymDriver() {

		final Object testingData[][] = {
				
			//Test #01: Access by customer. Expected true.
			{"customer1", 44, 43,  null},
			
			//Test #02: Access by unauthorized user. Expected false.
			{"manager1", 44, 43, IllegalArgumentException.class},
			
			//Test #03: Access by authorized user to non existing gym. Expected false.
			{"customer1", 44, 103, NullPointerException.class}

		};
		for (int i = 0; i < testingData.length; i++)
			this.joinLeaveGymTemplate((String) testingData[i][0], (Integer) testingData[i][1], (Integer) testingData[i][2], (Class<?>) testingData[i][3]);
	}

}
