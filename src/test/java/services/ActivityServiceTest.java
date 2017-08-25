package services;

import java.util.ArrayList;
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
import domain.Activity;
import domain.Customer;
import domain.Gym;

@Transactional
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@TransactionConfiguration(defaultRollback = true)
public class ActivityServiceTest extends AbstractTest {

	//The SUT

	@Autowired
	private GymService gymService;

	@Autowired
	private ActivityService	activityService;
	
	@Autowired
	private LoginService loginService;


	//Templates

	/*
	 * 4.3: Browse the catalogue of activities and navigate to the gyms that offer them or the trainers who deliver them.
	 */
	public void browseActivitiesTemplate(final String username, final Integer activityID, final Class<?> expected) {
		Class<?> caught = null;

		try {
			this.authenticate(username);

			activityService.findAll();
			activityService.gymByActivity(activityID);
			activityService.trainersByActivity(activityID);

			this.unauthenticate();

		} catch (final Throwable oops) {

			caught = oops.getClass();

		}

		this.checkExceptions(expected, caught);
	}
	
	/*
	 * 4.4: Search for activities that contain a single key word in their title or descriptions and are organised on a given day at a given time.
	 */
	public void searchActivitiesTemplate(final String username, final Integer activityID, final Class<?> expected) {
		Class<?> caught = null;

		try {
			this.authenticate(username);

			Activity a = activityService.findOne(activityID);
			Assert.notNull(a);

			this.unauthenticate();

		} catch (final Throwable oops) {

			caught = oops.getClass();

		}

		this.checkExceptions(expected, caught);
	}

	/*
	 * 6.2: An actor authenticated as manager must be able to manage the activities on his or her gyms, which includes registering,
	 * 		listing and cancelling them.
	 */
	public void manageActivityTemplate(final String username, final Integer managerID, final Integer gymID, String title, List<String> pictures, String description,
			Integer dayWeek, String startTime, String endTime, Integer numSeats, final Class<?> expected) {
		Class<?> caught = null;

		try {
			this.authenticate(username);
			
			//Registering
			Assert.isTrue(username == "manager1" || username == "manager2");
			Gym gym = gymService.findOne(gymID);

			Activity res;
			res = activityService.create();

			Assert.notNull(title);
			Assert.notNull(description);
			Assert.notNull(dayWeek);
			Assert.notNull(startTime);
			Assert.notNull(endTime);
			Assert.notNull(numSeats);
			Assert.isTrue(numSeats > 0);

			res.setTitle(title);
			res.setDescription(description);
			res.setDayWeek(dayWeek);
			res.setStartTime(startTime);
			res.setEndTime(endTime);
			res.setNumSeats(numSeats);
			res.setGym(gym);
			res.setPictures(pictures);
			res.setIsCancelled(false);
			
			activityService.save(res);
			
			//Listing
			gymService.activitiesByGym(gymID);
			
			//Cancelling
			res.setIsCancelled(true);
			
			activityService.save(res);
			
			this.unauthenticate();

		} catch (final Throwable oops) {

			caught = oops.getClass();

		}

		this.checkExceptions(expected, caught);
	}
	
	/*
	 * 7.2: A customer must be able to join or leave an activity in one of the gym's he's joined, as long as there are available seats.
	 */
	public void joinLeaveActivityTemplate(final String username, final Integer customerID, final Integer activityID, final Class<?> expected) {
		Class<?> caught = null;

		try {
			this.authenticate(username);
			
			//Joining
			Assert.isTrue(username == "customer" || username == "customer2");
			Customer c = (Customer) loginService.findActorByUserName(customerID);
			Activity a = activityService.findOne(activityID);
			List<Customer> list = a.getCustomers();
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
	public void browseActivitiesDriver() {

		final Object testingData[][] = {
				
			//Test #01: Access by authenticated user. Expected true.
			{"manager1", 48, null},
			
			//Test #02: Access by anonymous user to non existing activity. Expected false.
			{null, 78, NullPointerException.class},

			//Test #03: Access by authorized user to non existing activity. Expected false.
			{"administrator", 78, NullPointerException.class}

		};
		for (int i = 0; i < testingData.length; i++)
			this.browseActivitiesTemplate((String) testingData[i][0], (Integer) testingData[i][1], (Class<?>) testingData[i][2]);
	}
	
	@Test
	public void searchActivitiesDriver() {

		final Object testingData[][] = {
				
			//Test #01: Search by authenticated user. Expected true.
			{"trainer2", 49, null},
			
			//Test #02: Access by anonymous user to non existing activity. Expected false.
			{null, 184, IllegalArgumentException.class},

			//Test #03: Access by authorized user to non existing activity. Expected false.
			{"administrator", 84, IllegalArgumentException.class}

		};
		for (int i = 0; i < testingData.length; i++)
			this.searchActivitiesTemplate((String) testingData[i][0], (Integer) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	@Test
	public void manageActivityDriver() {
		
		List<String> pictures = new ArrayList<String>();
		pictures.add("https://i.ytimg.com/vi/iWm0beJM6Bk/maxresdefault.jpg");
		
		final Object testingData[][] = {
	
			//Test #01: Management by authorized manager. Expected true.
			{"manager1", 40, 42, "activity", pictures, "description", 3, "10:00", "20:00", 10, null},
			
			//Test #02: Search by anonymous user. Expected false.
			{null, 40, 42, "activity", pictures, "description", 1, "00:00", "10:00", 10, IllegalArgumentException.class},
			
			//Test #03: Negative number of seats introduced. Expected false.
			{"manager1", 40, 42, "activity", pictures, "description", 1, "00:00", "10:00", -30, IllegalArgumentException.class}
				
		};
		for (int i = 0; i < testingData.length; i++)
			this.manageActivityTemplate((String) testingData[i][0], (Integer) testingData[i][1], (Integer) testingData[i][2], (String) testingData[i][3],
					(List<String>) testingData[i][4], (String) testingData[i][5], (Integer) testingData[i][6], (String) testingData[i][7],
					(String) testingData[i][8], (Integer) testingData[i][9], (Class<?>) testingData[i][10]);
	}

	@Test
	public void joinLeaveActivityDriver() {

		final Object testingData[][] = {
				
			//Test #01: Access by customer. Expected true.
			{"customer", 44, 48, null},
			
			//Test #02: Access by anonymous user. Expected false.
			{null, 44, 48, IllegalArgumentException.class},
			
			//Test #03: Access by authorized user to non existing activity. Expected false.
			{"customer2", 45, 108, NullPointerException.class}

		};
		for (int i = 0; i < testingData.length; i++)
			this.joinLeaveActivityTemplate((String) testingData[i][0], (Integer) testingData[i][1], (Integer) testingData[i][2], (Class<?>) testingData[i][3]);
	}
	
}
