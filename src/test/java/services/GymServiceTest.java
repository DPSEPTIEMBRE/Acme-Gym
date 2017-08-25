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
	public void manageGymTemplate(final String username, final Integer id, String logo, String name, String address,
			Double fee, String logo2, String name2, String address2,
			Double fee2, final Class<?> expected) {
		Class<?> caught = null;

		try {
			this.authenticate(username);
			
			//Listing
			Manager manager = (Manager) loginService.findActorByUserName(id);
			managerService.gymsByManager(id);
			
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
	
	//Drivers

	@Test
	public void browseGymsDriver() {

		final Object testingData[][] = {
				
			//Test #01: Access by anonymous user. Expected true.
			{null, 42, 48,  null},
			
			//Test #02: Access by anonymous user to non existing gym. Expected false.
			{null, 108, 48, IllegalArgumentException.class},
			
			//Test #03: Access by authorized user to non existing activity. Expected false.
			{"customer1", 42, 104, IllegalArgumentException.class}

		};
		for (int i = 0; i < testingData.length; i++)
			this.browseGymsTemplate((String) testingData[i][0], (Integer) testingData[i][1], (Integer) testingData[i][2], (Class<?>) testingData[i][3]);
	}

	@Test
	public void manageGymDriver() {

		final Object testingData[][] = {
				
			//Test #01: Access by manager, all parameters correct. Expected true.
			{"manager1", 40, "https://i.ytimg.com/vi/iWm0beJM6Bk/maxresdefault.jpg", "gym", "gym street", 22.00,
				"https://i.ytimg.com/vi/iWm0beJM6Bk/maxresdefault.jpg", "gym2", "gym2 street", 1000.00, null},
				
			//Test #02: Access by anonymous user. Expected false.
			{null, 40, "https://i.ytimg.com/vi/iWm0beJM6Bk/maxresdefault.jpg", "gym", "gym street", 22.00,
				"https://i.ytimg.com/vi/iWm0beJM6Bk/maxresdefault.jpg", "gym2", "gym2 street", 1000.00, IllegalArgumentException.class},
				
			//Test #03: Access by unauthorized user. Expected false.
			{"trainer1", 40, "https://i.ytimg.com/vi/iWm0beJM6Bk/maxresdefault.jpg", "gym", "gym street", 22.00,
				"https://i.ytimg.com/vi/iWm0beJM6Bk/maxresdefault.jpg", "gym2", "gym2 street", 1000.00, IllegalArgumentException.class},
				
			//Test #04: Empty fields in creating. Expected false.
			{"manager1", 40, null, null, null, null,
				"https://i.ytimg.com/vi/iWm0beJM6Bk/maxresdefault.jpg", "gym2", "gym2 street", 1000.00, IllegalArgumentException.class},
					
			//Test #05: Empty fields in editing. Expected false.
			{"manager1", 40, "https://i.ytimg.com/vi/iWm0beJM6Bk/maxresdefault.jpg", "gym", "gym street", 22.00,
				null, null, null, null, IllegalArgumentException.class},
						
			//Test #06: Introduced negative value on fee in creation. Expected false.
			{"manager1", 40, "https://i.ytimg.com/vi/iWm0beJM6Bk/maxresdefault.jpg", "gym", "gym street", -22.00,
				"https://i.ytimg.com/vi/iWm0beJM6Bk/maxresdefault.jpg", "gym2", "gym2 street", 1000.00, IllegalArgumentException.class},
				
			//Test #07: Introduced negative value on fee in editing. Expected false.
			{"manager1", 40, "https://i.ytimg.com/vi/iWm0beJM6Bk/maxresdefault.jpg", "gym", "gym street", 22.00,
				"https://i.ytimg.com/vi/iWm0beJM6Bk/maxresdefault.jpg", "gym2", "gym2 street", -22.00, IllegalArgumentException.class},
							
			//Test #08: Introduced invalid logo URL in creation. Expected false.
			{"manager1", 40, "logo", "gym", "gym street", 22.00,
					"https://i.ytimg.com/vi/iWm0beJM6Bk/maxresdefault.jpg", "gym2", "gym2 street", 1000.00, IllegalArgumentException.class},
				
			//Test #09 Introduced invalid logo URL in editing. Expected false.
			{"manager1", 40, "https://i.ytimg.com/vi/iWm0beJM6Bk/maxresdefault.jpg", "gym", "gym street", 22.00,
				"logo", "gym2", "gym2 street", 1000.00, IllegalArgumentException.class},
								
			//Test #10: Introduced HTML code in name field on creation. Expected false.
			{"manager1", 40, "https://i.ytimg.com/vi/iWm0beJM6Bk/maxresdefault.jpg", "<!DOCTYPE html><html><body><h1>This is heading 1</h1></body></html>", "gym street", 22.00,
				"https://i.ytimg.com/vi/iWm0beJM6Bk/maxresdefault.jpg", "gym2", "gym2 street", 1000.00, IllegalArgumentException.class},
									
			//Test #11: Introduced HTML code in address field on edition. Expected false.
			{"manager1", 40, "https://i.ytimg.com/vi/iWm0beJM6Bk/maxresdefault.jpg", "gym", "gym street", 22.00,
				"https://i.ytimg.com/vi/iWm0beJM6Bk/maxresdefault.jpg", "gym2", "<!DOCTYPE html><html><body><h1>This is heading 1</h1></body></html>", 1000.00, IllegalArgumentException.class}

		};
		for (int i = 0; i < testingData.length; i++)
			this.manageGymTemplate((String) testingData[i][0], (Integer) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3],
					(String) testingData[i][4], (Double) testingData[i][5], (String) testingData[i][6], (String) testingData[i][7],
					(String) testingData[i][8], (Double) testingData[i][9], (Class<?>) testingData[i][10]);
	}

}
