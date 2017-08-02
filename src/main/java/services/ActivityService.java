package services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Activity;
import domain.Annotation;
import domain.Gym;
import domain.Trainer;
import repositories.ActivityRepository;

@Service
@Transactional
public class ActivityService {

	//Manager repositories
	
	@Autowired
	private ActivityRepository activityRepository;
	
	//Constructor
	
	public ActivityService() {
		super();
	}

	//CRUD Methods

	public void delete(Activity arg0) {
		Assert.notNull(arg0);
		activityRepository.delete(arg0);
	}

	public List<Activity> findAll() {
		return activityRepository.findAll();
	}

	public Activity findOne(Integer arg0) {
		Assert.notNull(arg0);
		return activityRepository.findOne(arg0);
	}

	public List<Activity> save(List<Activity> entities) {
		Assert.notNull(entities);
		Assert.noNullElements(entities.toArray());
		return activityRepository.save(entities);
	}

	public Activity save(Activity arg0) {
		Assert.notNull(arg0);
		return activityRepository.save(arg0);
	}

	//Others Methods
	
	public List<Trainer> trainersByActivity(int activity_id) {
		Assert.notNull(activity_id);
		return activityRepository.trainersByActivity(activity_id);
	}
	
	public List<Annotation> annotationsByActivity(int activity_id) {
		Assert.notNull(activity_id);
		return activityRepository.annotationsByActivity(activity_id);
	}
	
	public List<Gym> gymByActivity(int activity_id) {
		Assert.notNull(activity_id);
		return activityRepository.gymByActivity(activity_id);
	}
	
	
}
