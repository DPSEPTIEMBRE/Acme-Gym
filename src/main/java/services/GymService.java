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
import domain.Manager;
import domain.Trainer;
import repositories.GymRepository;
import security.LoginService;

@Service
@Transactional
public class GymService {

	//Manager repositories

	@Autowired
	private GymRepository gymRepository;
	
	@Autowired
	private LoginService loginService;
	
	@Autowired
	private ManagerService managerService;
	
	@Autowired
	private ActivityService activityService;

	//Constructor

	public GymService() {
		super();
	}

	//CRUD Methods

	public Gym create() {
		Gym gym= new Gym();
		
		gym.setActivities(new ArrayList<Activity>());
		gym.setAddress(new String());
		gym.setAnnotations(new ArrayList<Annotation>());
		gym.setCustomers(new ArrayList<Customer>());
		gym.setFee(new Double(0.0));
		gym.setLogo(new String());
		gym.setName(new String());
		gym.setTrainers(new ArrayList<Trainer>());
		gym.setIsDelete(false);
		
		return gym;
	}

	public void delete(Gym arg0) {
		Assert.notNull(arg0);
		arg0.setIsDelete(true);
		
		gymRepository.save(arg0);
		
		for(Activity a: arg0.getActivities()) {
			a.setIsCancelled(true);
			activityService.save(a);
		}
	}

	public List<Gym> findAll() {
		
		List<Gym> gyms=gymRepository.findAll();
		List<Gym> res= new ArrayList<Gym>();
		
		for(Gym g: gyms) {
			if(!g.getIsDelete()) {
				res.add(g);
			}else {
				continue;
			}
		}
		
		return res;
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

	public boolean exists(Integer id) {
		return gymRepository.exists(id);
	}

	public Gym save(Gym arg0) {
		Assert.notNull(arg0);
		Gym gym = null;
		if(exists(arg0.getId())) {
			gym= findOne(arg0.getId());
			gym.setActivities(arg0.getActivities());
			gym.setAddress(arg0.getAddress());
			gym.setAnnotations(arg0.getAnnotations());
			gym.setCustomers(arg0.getCustomers());
			gym.setFee(arg0.getFee());
			gym.setLogo(arg0.getLogo());
			gym.setName(arg0.getName());
			gym.setTrainers(arg0.getTrainers());
			gym.setIsDelete(arg0.getIsDelete());
			System.out.println("VA BIEN");
			gym = gymRepository.save(gym);
			System.out.println("TODO OK");
			
		}else{
			gym= gymRepository.save(arg0);
			Manager man= (Manager) loginService.findActorByUserName(LoginService.getPrincipal().getId());
			List<Gym> gyms= man.getGyms();
			gyms.add(gym);
			man.setGyms(gyms);
			managerService.save(man);	
			
		}
		return gym;
	}

	//Others Methods

	public List<Activity> activitiesByGym(int gym_id) {
		Assert.notNull(gym_id);
		
		List<Activity> activities=gymRepository.activitiesByGym(gym_id);
		List<Activity> res= new ArrayList<Activity>();
		
		for(Activity a: activities) {
			if(!a.getIsCancelled()) {
				res.add(a);
			}
		}
		
		return res;
	}

	public List<Annotation> annotationsByGym(int gym_id) {
		Assert.notNull(gym_id);
		return gymRepository.annotationsByGym(gym_id);
	}


	public List<Gym> gymWithMoreActivities() {
		return gymRepository.gymWithMoreActivities();
	}

	public Object[] minMaxAvgDesviationCustomersForGym() {
		return gymRepository.minMaxAvgDesviationCustomersForGym();
	}

	public Object[] avgDesviationNotesByGym() {
		return gymRepository.avgDesviationNotesByGym();
	}

	public List<Annotation> annotationsByActivity(int activity_id) {
		return activityService.annotationsByActivity(activity_id);
	}

	public Object[] avgDesviationStarsByGym() {
		Object[] res = gymRepository.avgDesviationStarsByGym();
		 if(res==null) {
			 Object[] aux = {0.0,0.0};
			 res=aux;
		 }
		return res;
	}

	public Double avgStarsByActivity(int activity_id) {
		Assert.notNull(activity_id);
		return activityService.avgStarsByActivity(activity_id);
	}




}
