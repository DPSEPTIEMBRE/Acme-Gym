package controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import domain.Gym;
import services.GymService;

@Controller
@RequestMapping("/gym")
public class GymController {

	//Services
	
	@Autowired
	private GymService gymService;
	
	// Constructors -----------------------------------------------------------

	public GymController() {
		super();
	}
	
	//Actions
	
	@RequestMapping("/list")
	public ModelAndView list() {
		ModelAndView result;

		List<Gym> gyms= gymService.findAll();
		
		result = new ModelAndView("gym/list");
		result.addObject("gyms", gyms);

		return result;
	}
}
