package services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Activity;
import domain.Annotation;
import domain.Customer;
import domain.Gym;
import domain.Trainer;
import repositories.ActivityRepository;

@Service
@Transactional
public class ActivityService {

	//Manager repositories
	
	@Autowired
	private ActivityRepository activityRepository;
	
	@Autowired
	private GymService gymService;
	
	//Constructor
	
	public ActivityService() {
		super();
	}

	//CRUD Methods
	
	public Activity create() {
		Activity act= new Activity();
		
		act.setAnnotations(new ArrayList<Annotation>());
		act.setCustomers(new ArrayList<Customer>());
		act.setDayWeek(new Integer(1));
		act.setDescription(new String());
		act.setEndTime(new String("00:00"));
		act.setGym(gymService.create());
		act.setNumSeats(new Integer(1));
		act.setPictures(new ArrayList<String>());
		act.setStartTime(new String("00:00"));
		act.setTitle(new String());
		act.setTrainers(new ArrayList<Trainer>());
		act.setIsCancelled(false);
		
		return act;
	}
	
	public void delete(Activity arg0) {
		Assert.notNull(arg0);
		arg0.setIsCancelled(true);
		activityRepository.save(arg0);
	}

	public List<Activity> findAll() {
		
		List<Activity> activities=activityRepository.findAll();
		List<Activity> res= new ArrayList<Activity>();
		
		for(Activity a: activities) {
			if(!a.getIsCancelled()) {
				res.add(a);
				}
		}
		
		return res;
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

	public boolean exists(Integer id) {
		return activityRepository.exists(id);
	}

	public Activity save(Activity arg0) {
		Assert.notNull(arg0);
		
		Activity act=null;
		
		if(exists(arg0.getId())) {
			act=findOne(arg0.getId());
			act.setAnnotations(arg0.getAnnotations());
			act.setCustomers(arg0.getCustomers());
			act.setDayWeek(arg0.getDayWeek());
			act.setDescription(arg0.getDescription());
			act.setEndTime(arg0.getEndTime());
			act.setGym(arg0.getGym());
			act.setNumSeats(arg0.getNumSeats());
			act.setPictures(arg0.getPictures());
			act.setStartTime(arg0.getStartTime());
			act.setTitle(arg0.getTitle());
			act.setTrainers(arg0.getTrainers());
			act.setIsCancelled(arg0.getIsCancelled());
			act= activityRepository.save(act);

		}else {
			act=activityRepository.save(arg0);
			Gym gym= act.getGym();
			List<Activity> activities = gym.getActivities();
			activities.add(act);
			gym.setActivities(activities);
			gymService.save(gym);
		}
		
		return act;
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
	
	public Gym gymByActivity(int activity_id) {
		Assert.notNull(activity_id);
		
		Gym g= activityRepository.gymByActivity(activity_id);
		
		if(g.getIsDelete()) {
			g=null;
		}
		
		return g;
	}

	public Double avgStarsByActivity(int activity_id) {
		Assert.notNull(activity_id);
		return activityRepository.avgStarsByActivity(activity_id);
	}

	public Double avgStar(Activity a) {
		Double res = 0.0;
		Integer up=0;
		Integer total=a.getAnnotations().size();
		
		for(Annotation an : a.getAnnotations()) {
			up= up + an.getRate();
		}
		
		res=new Double(up/total);
		
		return res;
	}
	
	
}
