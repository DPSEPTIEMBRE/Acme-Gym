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

import utilities.AbstractTest;
import domain.Annotation;
import domain.Customer;
import domain.Gym;

@Transactional
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@TransactionConfiguration(defaultRollback = true)
public class AnnotationServiceTest extends AbstractTest {

	//The SUT

	@Autowired
	private AnnotationService annotationService;
	
	@Autowired
	private GymService gymService;
	
	@Autowired
	private ActivityService activityService;
	

	//Templates

	/*
	 * 15.1: A non authenticated user must be able to display annotations of entities he or she can list.
	 */
	public void annotationDisplayTemplate(final Integer gymID, final Integer activityID, final Class<?> expected) {
		Class<?> caught = null;

		try {
			
			gymService.annotationsByGym(gymID);
			activityService.annotationsByActivity(activityID);

		} catch (final Throwable oops) {

			caught = oops.getClass();

		}

		this.checkExceptions(expected, caught);
	}
	
	/*
	 * 16.1: An authenticated actor must be able to write an annotation and associate it with an annotable entity.
	 */
	public void annotationWriteTemplate(final String username, final Integer id, String moment, String text, Integer rating, final Class<?> expected) {
		Class<?> caught = null;

		try {
			this.authenticate(username);
			
			Annotation res = annotationService.create();

			if(moment != null){
				Assert.isTrue(moment.matches("^([1-9]|([012][0-9])|(3[01]))-([0]{0,1}[1-9]|1[012])-\\d\\d\\d\\d [012]{0,1}[0-9]:[0-6][0-9]$"));
			}
			Assert.notNull(text);
			Assert.isTrue(rating >= 0 && rating <= 3);

			res.setMomentWritten(moment);
			res.setText(text);
			res.setRate(rating);

			annotationService.save(res);
			
			Gym g = gymService.findOne(id);
			List<Annotation> list = g.getAnnotations();
			list.add(res);
			
			this.unauthenticate();

		} catch (final Throwable oops) {

			caught = oops.getClass();

		}

		this.checkExceptions(expected, caught);
	}
	
	/*
	 * 17.1: An administrator must be able to delete annotations he or she thinks are inappropriate.
	 */
	public void annotationDeleteTemplate(final String username, final Integer annotationID, final Class<?> expected) {
		Class<?> caught = null;

		try {
			this.authenticate(username);
			
			Annotation a = annotationService.findOne(annotationID);
			annotationService.delete(a);
			
			this.unauthenticate();
			
		} catch (final Throwable oops) {

			caught = oops.getClass();

		}

		this.checkExceptions(expected, caught);
	}

	//Drivers

	@Test
	public void annotationDisplayDriver() {

		final Object testingData[][] = {
				
			//Test #01: Accessing as expected. Expected true.
			{42, 48, null},
			
			//Test #02: Attempt to access a nonexistent gym. Expected false.
			{142, 48, IllegalArgumentException.class},
			
			//Test #03: Attempt to access a nonexisten activity. Expected false.
			{42, 148, IllegalArgumentException.class}

		};
		for (int i = 0; i < testingData.length; i++)
			this.annotationDisplayTemplate((Integer) testingData[i][0], (Integer) testingData[i][1], (Class<?>) testingData[i][2]);
	}
	
	@Test
	public void annotationWriteDriver() {

		final Object testingData[][] = {
				
			//Test #01: Writing as authorized user. Expected true.
			{"trainer1", 42, "10-10-2017 20:00", "annotation", 2, null},
			
			//Test #02: Attempt to write as anonymous user. Expected false
			{null, 42, "10-10-2017 20:00", "annotation", 2, IllegalArgumentException.class},
			
			//Test #03: Rating doesn't conform to the established limits. Expected false.
			{"trainer1", 42, "10-10-2017 20:00", "annotation", 10, IllegalArgumentException.class}

		};
		for (int i = 0; i < testingData.length; i++)
			this.annotationWriteTemplate((String) testingData[i][0], (Integer) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3],
					(Integer) testingData[i][4], (Class<?>) testingData[i][5]);
	}
	
	@Test
	public void annotationDeleteDriver() {

		final Object testingData[][] = {
				
			//Test #01: Authorized deletion. Expected true.
			{"administrator", 50, null},
			
			//Test #02: Unauthorized deletion. Expected false.
			{"customer2", 50, IllegalArgumentException.class},
			
			//Test #03: Attempt to delete a nonexistent annotation. Expected false.
			{"administrator", 150, IllegalArgumentException.class}

		};
		for (int i = 0; i < testingData.length; i++)
			this.annotationDeleteTemplate((String) testingData[i][0], (Integer) testingData[i][1], (Class<?>) testingData[i][2]);
	}
}
