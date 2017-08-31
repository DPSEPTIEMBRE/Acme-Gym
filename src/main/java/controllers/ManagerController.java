package controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import domain.Activity;
import domain.Gym;
import domain.Manager;
import domain.Trainer;
import security.LoginService;
import services.ActivityService;
import services.GymService;
import services.ManagerService;
import services.TrainerService;

@Controller
@RequestMapping("/manageractor")
public class ManagerController extends AbstractController{

	private static Integer actual=null;

	//Services

	@Autowired
	private ManagerService managerService;
	
	@Autowired
	private GymService gymService;

	@Autowired
	private TrainerService trainerService;
	
	@Autowired
	private LoginService loginService;

	@Autowired
	private ActivityService activityService;
	
	// Constructors -----------------------------------------------------------

	public ManagerController() {
		super();
	}

	//Actions

	@RequestMapping("/avgStar")
	public ModelAndView avgStar(@RequestParam Manager q) {
		ModelAndView result;

		result = new ModelAndView("manager/avgStar");
		
		result.addObject("avgStar", managerService.avgStar(q));

		return result;
	}
	
	@RequestMapping("/gym/list")
	public ModelAndView listGyms(@RequestParam Integer q,@RequestParam Integer a) {
		ModelAndView result;

		Manager manager= (Manager)loginService.findActorByUserName(q);

		result = new ModelAndView("gym/list");
		result.addObject("gyms", managerService.gymsByManager(manager.getId()));
		result.addObject("a", a);

		return result;
	}
	
	@RequestMapping("/gym/create")
	public ModelAndView create() {
		ModelAndView result;

		Gym gym= gymService.create();
		
		result = new ModelAndView("gym/create");
		result.addObject("gym", gym);
		result.addObject("url","gym/save.do");
		result.addObject("message", null);

		return result;
	}
	
	@RequestMapping("/trainer/addByGym")
	public ModelAndView addTrainerByGym(@RequestParam Integer q) {
		ModelAndView result;

		List<Trainer> trainers= trainerService.findAll();
		Gym gym= gymService.findOne(q);
		
		for(Gym g: gymService.findAll()) {
			trainers.removeAll(g.getTrainers());
		}
		
		result = new ModelAndView("trainer/list");
		result.addObject("trainers", trainers);
		result.addObject("a", 1);
		
		actual=gym.getId();

		return result;
	}
	
	@RequestMapping("/trainer/addToGym")
	public ModelAndView addToGym(@RequestParam Integer q) {
		ModelAndView result;
		Trainer trainer= trainerService.findOne(q);
		Gym actualGym= gymService.findOne(actual);
		try {
			List<Trainer> trainers= actualGym.getTrainers();
			trainers.add(trainer);
			actualGym.setTrainers(trainers);
			gymService.save(actualGym);
			result = new ModelAndView("redirect:/welcome/index.do");
		}catch (Throwable e) {
			result= new ModelAndView("trainer/list");
			List<Trainer> trainers2= trainerService.findAll();
			for(Gym g: gymService.findAll()) {
				trainers2.removeAll(g.getTrainers());
			}
			result.addObject("trainers", trainers2);
			result.addObject("a", 1);
		}
		
		actual=null;

		return result;
	}
	
	@RequestMapping("/trainer/list")
	public ModelAndView listTrainers() {
		ModelAndView result;

		List<Trainer> trainers= trainerService.findAll();

		result = new ModelAndView("trainer/list");
		result.addObject("trainers", trainers);

		return result;
	}
	
	@RequestMapping("/trainer/create")
	public ModelAndView createTrainer() {
		ModelAndView result;

		Trainer trainer= trainerService.create();

		result = new ModelAndView("trainer/create");
		result.addObject("trainer", trainer);

		return result;
	}

	@RequestMapping("/activity/create")
	public ModelAndView createActivity(@RequestParam Integer q) {
		ModelAndView result;

		Activity activity= activityService.create();
		Gym gym= gymService.findOne(q);
		activity.setGym(gym);

		result = new ModelAndView("activity/create");
		result.addObject("activity", activity);
		result.addObject("url", "activity/save.do");

		return result;
	}

	
}
