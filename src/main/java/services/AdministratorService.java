package services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Administrator;
import repositories.AdministratorRepository;

@Service
@Transactional
public class AdministratorService {

	//Manager repositories

	@Autowired
	private AdministratorRepository administratorRepository;

	//Constructor

	public AdministratorService() {
		super();
	}

	//CRUD Methods
	
	public void delete(Administrator arg0) {
		Assert.notNull(arg0);
		administratorRepository.delete(arg0);
	}

	public List<Administrator> findAll() {
		return administratorRepository.findAll();
	}

	public Administrator findOne(Integer arg0) {
		Assert.notNull(arg0);
		return administratorRepository.findOne(arg0);
	}

	public List<Administrator> save(List<Administrator> entities) {
		Assert.notNull(entities);
		Assert.noNullElements(entities.toArray());
		return administratorRepository.save(entities);
	}

	public Administrator save(Administrator arg0) {
		Assert.notNull(arg0);
		return administratorRepository.save(arg0);
	}
	
	//Others Methods
	
	public Object[] avgDesviationNotesWrittersByAdministrators() {
		return administratorRepository.avgDesviationNotesWrittersByAdministrators();
	}

	public Object[] avgDesviationNotesStoreByAdministrators() {
		return administratorRepository.avgDesviationNotesStoreByAdministrators();
	}

	public Object[] avgDesviationStarsByAdministrators() {
		return administratorRepository.avgDesviationStarsByAdministrators();
	}

	public Double avgStarsCountryByAdministrators() {
		return administratorRepository.avgStarsCountryByAdministrators();
	}

	public Double avgStarsCityByAdministrators() {
		return administratorRepository.avgStarsCityByAdministrators();
	}

	

}
