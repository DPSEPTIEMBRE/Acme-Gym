package controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import domain.Trainer;
import services.ActivityService;
import services.TrainerService;

@Controller
@RequestMapping("/trainer")
public class TrainerController {

	//Services

	@Autowired
	private TrainerService trainerService;
	
	@Autowired
	private ActivityService activityService;

	// Constructors -----------------------------------------------------------

	public TrainerController() {
		super();
	}

	//Actions

	@RequestMapping("/listByActivity.do")
	public ModelAndView list(@RequestParam Integer q) {
		ModelAndView result;

		List<Trainer> trainers= new ArrayList<Trainer>();
		
		trainers.addAll(activityService.trainersByActivity(q));
		
		result = new ModelAndView("trainer/list");
		result.addObject("trainers", trainers);

		return result;
	}
	

}
