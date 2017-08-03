package controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import domain.Activity;
import services.ActivityService;

@Controller
@RequestMapping("/activity")
public class ActivityController extends AbstractController{

	//Services

	@Autowired
	private ActivityService activityService;

	// Constructors -----------------------------------------------------------

	public ActivityController() {
		super();
	}

	//Actions

	@RequestMapping("/list")
	public ModelAndView list() {
		ModelAndView result;

		List<Activity> activities= activityService.findAll();

		result = new ModelAndView("activity/list");
		result.addObject("activities", activities);

		return result;
	}
}
