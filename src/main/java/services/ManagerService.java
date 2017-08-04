package services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Gym;
import domain.Manager;
import repositories.ManagerRepository;

@Service
@Transactional
public class ManagerService {
	
	
	//Manager repositories

	@Autowired
	private ManagerRepository managerRepository;

	//Constructor

	public ManagerService() {
		super();
	}

	//CRUD Methods
	
	public void delete(Manager arg0) {
		Assert.notNull(arg0);
		managerRepository.delete(arg0);
	}

	public List<Manager> findAll() {
		return managerRepository.findAll();
	}

	public Manager findOne(Integer arg0) {
		Assert.notNull(arg0);
		return managerRepository.findOne(arg0);
	}
	
	public List<Manager> save(List<Manager> entities) {
		Assert.notNull(entities);
		Assert.noNullElements(entities.toArray());
		return managerRepository.save(entities);
	}

	public Manager save(Manager arg0) {
		Assert.notNull(arg0);
		return managerRepository.save(arg0);
	}
	
	public boolean exists(Integer arg0) {
		return managerRepository.exists(arg0);
	}
	

	//Others Methods
	
	public Object[] avgDesviationNotesWrittersByManagers() {
		return managerRepository.avgDesviationNotesWrittersByManagers();
	}

	public Object[] avgDesviationNotesStoreByManagers() {
		return managerRepository.avgDesviationNotesStoreByManagers();
	}

	public Object[] avgDesviationStarsByManagers() {
		return managerRepository.avgDesviationStarsByManagers();
	}

	public Double avgStarsCountryByManagers() {
		return managerRepository.avgStarsCountryByManagers();
	}

	public Double avgStarsCityByManagers() {
		return managerRepository.avgStarsCityByManagers();
	}

	public List<Gym> gymsByManager(int manager_id) {
		Assert.notNull(manager_id);
		return managerRepository.gymsByManager(manager_id);
	}

	public Object[] minMaxAvgDesviationGymsByManagers() {
		return managerRepository.minMaxAvgDesviationGymsByManagers();
	}

	
}
