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

@Transactional
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@TransactionConfiguration(defaultRollback = true)
public class AdministratorServiceTest extends AbstractTest {

	//The SUT

	@Autowired
	private AdministratorService administratorService;
	
	@Autowired
	private ActivityService activityService;
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private GymService gymService;
	
	@Autowired
	private ManagerService managerService;
	
	@Autowired
	private TrainerService trainerService;
	

	//Templates

	/*
	 * 8.2: An administrator must be able to display a dashboard with site information.
	 */
	public void adminDashboardTemplate(final String username, final Integer activityID, final Class<?> expected) {
		Class<?> caught = null;

		try {
			this.authenticate(username);
			
			Assert.isTrue(username == "admin");
			activityService.avgStarsByActivity(activityID);
			administratorService.avgStarsCityByAdministrators();
			customerService.avgStarsCityByCustomers();
			gymService.avgDesviationStarsByGym();
			managerService.minMaxAvgDesviationGymsByManagers();
			trainerService.avgStarsCountryByTrainers();
			
			this.unauthenticate();

		} catch (final Throwable oops) {

			caught = oops.getClass();

		}

		this.checkExceptions(expected, caught);
	}

	//Drivers

	@Test
	public void adminDashboardDriver() {

		final Object testingData[][] = {
				
			//Test #01: Access as administrator. Expected true.
			{"admin", 48, null},
			
			//Test #02: Access as unauthorized user. Expected false.
			{"manager1", 48, IllegalArgumentException.class},
			
			//Test #03: Attempt to access a null activity. Expected false.
			{"admin", null, NullPointerException.class},

		};
		for (int i = 0; i < testingData.length; i++)
			this.adminDashboardTemplate((String) testingData[i][0], (Integer) testingData[i][1], (Class<?>) testingData[i][2]);
	}
}
