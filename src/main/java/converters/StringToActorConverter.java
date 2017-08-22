
package converters;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import domain.Actor;
import services.AdministratorService;
import services.CustomerService;
import services.ManagerService;
import services.TrainerService;

@Component
@Transactional
public class StringToActorConverter implements Converter<String, Actor> {

	@Autowired
	private AdministratorService	administratorService;
	@Autowired
	private ManagerService			managerService;
	@Autowired
	private TrainerService			trainerService;
	@Autowired
	private CustomerService			customerService;


	@Override
	public Actor convert(String actor) {
		int id;

		try {
			id = Integer.valueOf(actor.trim());
		} catch (NumberFormatException e) {
			return null;
		}

		if (administratorService.exists(id)) {
			return administratorService.findOne(id);
		}

		if (managerService.exists(id)) {
			return managerService.findOne(id);
		}

		if (trainerService.exists(id)) {
			return trainerService.findOne(id);
		}

		return customerService.findOne(id);

	}

}
