package services;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Activity;
import domain.Actor;
import domain.Administrator;
import domain.Annotation;
import domain.Customer;
import domain.Gym;
import domain.Manager;
import domain.Trainer;
import repositories.AnnotationRepository;
import security.Authority;
import security.LoginService;

@Service
@Transactional
public class AnnotationService {

	//Manager repositories

	@Autowired
	private AnnotationRepository annotationRepository;
	
	@Autowired
	private ActivityService activityService;
	
	@Autowired
	private LoginService loginService;
	
	@Autowired
	private GymService gymService;
	
	@Autowired
	private AdministratorService administratorService;
	
	@Autowired
	private TrainerService trainerService;
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private ManagerService managerService;

	//Constructor

	public AnnotationService() {
		super();
	}

	//CRUD Methods
	
	public Annotation create() {
		Annotation an= new Annotation();
		
		an.setActivity(activityService.create());
		an.setActorStores(null);
		an.setActorWrites(loginService.findActorByUserName(LoginService.getPrincipal().getId()));
		an.setGym(gymService.create());
		an.setMomentWritten(new String("00-00-0000 00:00"));
		an.setRate(0);
		an.setText("");
		
		return an;
	}
	
	public void delete(Annotation arg0) {
		Assert.notNull(arg0);
		if(arg0.getActivity()!=null) {
			Activity act= arg0.getActivity();
			List<Annotation> ann= act.getAnnotations();
			ann.remove(arg0);
			act.setAnnotations(ann);
			activityService.save(act);
		}else if(arg0.getActorStores()!=null) {
			Actor actor= arg0.getActorStores();
			List<Annotation> ann=actor.getAnnotationStore();
			ann.remove(arg0);
			actor.setAnnotationStore(ann);
			List<Authority> auths = (List<Authority>) actor.getUserAccount().getAuthorities();
			for(Authority auth: auths) {
				if(auth.getAuthority().equals(Authority.ADMINISTRATOR)) {
					administratorService.save((Administrator)actor);
				}else if(auth.getAuthority().equals(Authority.CUSTOMER)){
					customerService.save((Customer) actor);
				}else if(auth.getAuthority().equals(Authority.MANAGER)) {
					managerService.save((Manager) actor);
				}else {
					trainerService.save((Trainer) actor);
				}
			}
			
		}else if(arg0.getGym()!=null) {
			Gym gym= arg0.getGym();
			List<Annotation> ann = gym.getAnnotations();
			ann.remove(arg0);
			gym.setAnnotations(ann);
			gymService.save(gym);
		}
		
		Actor actor= arg0.getActorWrites();
		List<Annotation> ann=actor.getAnnotationStore();
		ann.remove(arg0);
		actor.setAnnotationStore(ann);
		List<Authority> auths = (List<Authority>) actor.getUserAccount().getAuthorities();
		for(Authority auth: auths) {
			if(auth.getAuthority().equals(Authority.ADMINISTRATOR)) {
				administratorService.save((Administrator)actor);
			}else if(auth.getAuthority().equals(Authority.CUSTOMER)){
				customerService.save((Customer) actor);
			}else if(auth.getAuthority().equals(Authority.MANAGER)) {
				managerService.save((Manager) actor);
			}else {
				trainerService.save((Trainer) actor);
			}
		}
		
		annotationRepository.delete(arg0);
	}

	public List<Annotation> findAll() {
		return annotationRepository.findAll();
	}

	public Annotation findOne(Integer arg0) {
		Assert.notNull(arg0);
		return annotationRepository.findOne(arg0);
	}

	public List<Annotation> save(List<Annotation> entities) {
		Assert.notNull(entities);
		Assert.noNullElements(entities.toArray());
		return annotationRepository.save(entities);
	}

	public boolean exists(Integer id) {
		return annotationRepository.exists(id);
	}

	public Annotation save(Annotation arg0) {
		Assert.notNull(arg0);
		
		Annotation annot= null;
		
		if(exists(arg0.getId())) {
			annot=annotationRepository.findOne(arg0.getId());
			annot.setActivity(arg0.getActivity());
			annot.setActorStores(arg0.getActorStores());
			annot.setActorWrites(arg0.getActorWrites());
			annot.setGym(arg0.getGym());
			annot.setMomentWritten(arg0.getMomentWritten());
			annot.setRate(arg0.getRate());
			annot.setText(arg0.getText());
			annot=annotationRepository.save(annot);
		}else {
			annot=annotationRepository.save(arg0);
		}
		
		return annot;
	}
	
	

}
