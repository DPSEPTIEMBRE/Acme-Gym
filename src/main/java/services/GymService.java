package services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Activity;
import domain.Annotation;
import domain.Gym;
import repositories.GymRepository;

@Service
@Transactional
public class GymService {
	
	//Manager repositories

	@Autowired
	private GymRepository gymRepository;

	//Constructor

	public GymService() {
		super();
	}
	
	//CRUD Methods

	public void delete(Gym arg0) {
		Assert.notNull(arg0);
		gymRepository.delete(arg0);
	}

	public List<Gym> findAll() {
		return gymRepository.findAll();
	}

	public Gym findOne(Integer arg0) {
		Assert.notNull(arg0);
		return gymRepository.findOne(arg0);
	}

	public List<Gym> save(List<Gym> entities) {
		Assert.notNull(entities);
		Assert.noNullElements(entities.toArray());
		return gymRepository.save(entities);
	}

	public Gym save(Gym arg0) {
		Assert.notNull(arg0);
		return gymRepository.save(arg0);
	}
	
	//Others Methods
	
	public List<Activity> activitiesByGym(int gym_id) {
		Assert.notNull(gym_id);
		return gymRepository.activitiesByGym(gym_id);
	}

	public List<Annotation> annotationsByGym(int activity_id) {
		Assert.notNull(activity_id);
		return gymRepository.annotationsByGym(activity_id);
	}

	
	public List<Gym> gymWithMoreActivities() {
		return gymRepository.gymWithMoreActivities();
	}

	public Object[] minMaxAvgDesviationCustomersForGym() {
		return gymRepository.minMaxAvgDesviationCustomersForGym();
	}


	

}
