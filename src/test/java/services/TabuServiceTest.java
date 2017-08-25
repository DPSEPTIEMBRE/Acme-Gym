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
import domain.TabuWord;

@Transactional
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@TransactionConfiguration(defaultRollback = true)
public class TabuServiceTest extends AbstractTest {

	//The SUT

	@Autowired
	private TabuWordService tabuService;


	//Templates

	/*
	 * 20: An administrator must be able to list and create taboo words.
	 */
	public void tabuListCreateTemplate(final String username, String name, final Class<?> expected) {
		Class<?> caught = null;

		try {
			this.authenticate(username);
			
			//List
			tabuService.findAll();
			
			//Create
			TabuWord res = tabuService.create();
			Assert.notNull(name);
			res.setName(name);
			tabuService.save(res);
			
			this.unauthenticate();

		} catch (final Throwable oops) {

			caught = oops.getClass();

		}

		this.checkExceptions(expected, caught);
	}

	//Drivers

	@Test
	public void tabuListCreateDriver() {

		final Object testingData[][] = {
				
			//Test #01: Listing and creating as administrator. Expected true.
			{"administrator", "cialis", null},
			
			//Test #02: Attempting access as anonymous user. Expected false.
			{null, "cialis", IllegalArgumentException.class},
			
			//Test #03: Creating a null word. Expected false.
			{"administrator", null, IllegalArgumentException.class}

		};
		for (int i = 0; i < testingData.length; i++)
			this.tabuListCreateTemplate((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
	}
}
