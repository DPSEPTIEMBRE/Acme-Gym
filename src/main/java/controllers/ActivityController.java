package controllers;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import domain.Activity;
import domain.Gym;
import domain.Trainer;
import services.ActivityService;
import services.GymService;
import services.TrainerService;

@Controller
@RequestMapping("/activity")
public class ActivityController extends AbstractController{

	private static Integer actual=null;

	//Services

	@Autowired
	private ActivityService activityService;

	@Autowired
	private GymService gymService;

	@Autowired
	private TrainerService trainerService;


	// Constructors -----------------------------------------------------------

	public ActivityController() {
		super();
	}

	//Actions
	
		
	@RequestMapping("/avgStar")
	public ModelAndView avgStar(@RequestParam Activity q) {
		ModelAndView result;

		result = new ModelAndView("activity/avgStar");
		
		result.addObject("avgStar", activityService.avgStar(q));

		return result;
	}

	
	@RequestMapping("/list")
	public ModelAndView list(@RequestParam Integer a) {
		ModelAndView result;

		result = new ModelAndView("activity/list");
		
		result.addObject("a", a);
		result.addObject("activities", activityService.findAll());

		return result;
	}

	@RequestMapping("/listByGym")
	public ModelAndView listByGym(@RequestParam Integer q) {
		ModelAndView result;

		List<Activity> activities= new ArrayList<Activity>();
		activities.addAll(gymService.activitiesByGym(q));


		result = new ModelAndView("activity/list");
		result.addObject("activities", activities);
		result.addObject("a", 1);

		return result;
	}

	@RequestMapping("/listByGym2")
	public ModelAndView listByGym2(@RequestParam Integer q) {
		ModelAndView result;

		List<Activity> activities= new ArrayList<Activity>();
		activities.addAll(gymService.activitiesByGym(q));


		result = new ModelAndView("activity/list");
		result.addObject("activities", activities);
		result.addObject("a", 1);
		return result;
	}

	@RequestMapping("/listByGym3")
	public ModelAndView listByGym3(@RequestParam Integer q) {
		ModelAndView result;

		List<Activity> activities= new ArrayList<Activity>();
		activities.addAll(gymService.activitiesByGym(q));


		result = new ModelAndView("activity/list");
		result.addObject("activities", activities);
		result.addObject("a", 2);
		return result;
	}

	@RequestMapping("/addTrainer")
	public ModelAndView addTrainer(@RequestParam Integer q) {
		ModelAndView result;

		Gym g = activityService.findOne(q).getGym();
		List<Trainer> trainers = g.getTrainers();
		trainers.removeAll(activityService.findOne(q).getTrainers());


		result = new ModelAndView("trainer/list");
		result.addObject("trainers", trainers);
		result.addObject("a", 2);
		
		actual = q;
		return result;
	}


	@RequestMapping("/addToActivity")
	public ModelAndView addToActivity(@RequestParam Integer q) {
		ModelAndView result;

		Trainer trainer= trainerService.findOne(q);
		Activity activity= activityService.findOne(actual);
		try{
			List<Trainer> list= activity.getTrainers();
			list.add(trainer);
			activity.setTrainers(list);
			activityService.save(activity);
			result = new ModelAndView("redirect:/welcome/index.do");
		}catch (Throwable e) {
			Gym g = activity.getGym();
			List<Trainer> trainers = g.getTrainers();
			trainers.removeAll(activity.getTrainers());
			result = new ModelAndView("trainer/list");
			result.addObject("trainers", trainers);
			result.addObject("a", 2);
		}
		actual=null;
		return result;

	}


	@RequestMapping("/cancel")
	public ModelAndView cancel(@RequestParam Integer q) {
		ModelAndView result;

		Activity a= activityService.findOne(q);

		try {
			activityService.delete(a);
			result = new ModelAndView("redirect:/welcome/index.do");
		}catch (Throwable e) {
			result = new ModelAndView("redirect:/welcome/index.do");
		}

		return result;
	}


	@RequestMapping(value = "/save", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid Activity activity,BindingResult binding) {
		ModelAndView result;

		if(binding.hasErrors()) {
			result= new ModelAndView("activity/create");
			result.addObject("activity", activity);
			result.addObject("message", "activity.commit.error");
		}else {
			try {
				activityService.save(activity);
				result=new ModelAndView("redirect:/welcome/index.do");
			}catch (Throwable e) {
				result= new ModelAndView("activity/create");
				result.addObject("activity", activity);
				result.addObject("message", "activity.commit.error");
			}
		}

		return result;
	}

}
