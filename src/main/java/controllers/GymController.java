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

import domain.Actor;
import domain.Customer;
import domain.Gym;
import domain.Manager;
import security.Authority;
import security.LoginService;
import services.ActivityService;
import services.GymService;

@Controller
@RequestMapping("/gym")
public class GymController extends AbstractController{

	//Services

	@Autowired
	private GymService gymService;

	@Autowired
	private ActivityService activityService;

	@Autowired
	private LoginService loginService;

	// Constructors -----------------------------------------------------------

	public GymController() {
		super();
	}

	//Actions

	@RequestMapping("/list")
	public ModelAndView list(@RequestParam Integer a,@RequestParam(required=false) Integer userAccountID) {
		ModelAndView result;

		List<Gym> gyms= gymService.findAll();
		if(userAccountID!=null && a!=3) {
			Actor actor=loginService.findActorByUserName(userAccountID);
			for(Authority auth : actor.getUserAccount().getAuthorities()) {
				if(auth.getAuthority().equals(Authority.CUSTOMER)) {
					Customer customer = (Customer) loginService.findActorByUserName(userAccountID);
					gyms.removeAll(customer.getGyms());
				} else if(auth.getAuthority().equals(Authority.MANAGER)) {
					Manager manager = (Manager) loginService.findActorByUserName(userAccountID);
					gyms.removeAll(manager.getGyms());
				}
			}

		}



		result = new ModelAndView("gym/list");
		result.addObject("gyms", gyms);
		result.addObject("a", a);

		return result;
	}

	@RequestMapping("/listByActivity")
	public ModelAndView listByActivity(@RequestParam Integer q) {
		ModelAndView result;

		List<Gym> gyms= new ArrayList<Gym>();
		Gym g= activityService.gymByActivity(q);
		if(g!=null) {
			gyms.add(activityService.gymByActivity(q));
		}

		result = new ModelAndView("gym/list");
		result.addObject("gyms", gyms);
		result.addObject("a", 3);

		return result;
	}

	@RequestMapping(value= "/save",method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid Gym gym, BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			result = new ModelAndView("gym/create");
			result.addObject("gym", gym);
			result.addObject("url","gym/save.do");
			result.addObject("message", "gym.commit.error");
		} else {
			try {
				gymService.save(gym);
				result = new ModelAndView("redirect:/welcome/index.do");
			} catch (Throwable oops) {
				result = new ModelAndView("gym/create");
				result.addObject("gym", gym);
				result.addObject("url","gym/save.do");
				result.addObject("message", "gym.commit.error"); } }


		return result;
	}


	@RequestMapping(value= "/save-edit",method = RequestMethod.POST, params = "save")
	public ModelAndView saveEdit(@Valid Gym gym, BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			result = new ModelAndView("gym/edit");
			result.addObject("gym", gym);
			result.addObject("url","gym/save.do");
			result.addObject("message", null);
		} else {
			try {
				gymService.save(gym);
				result = new ModelAndView("redirect:/welcome/index.do");
			} catch (Throwable oops) {
				result = new ModelAndView("gym/edit");
				result.addObject("gym", gym);
				result.addObject("url","gym/save.do");
				result.addObject("message", "gym.commit.error"); } }


		return result;
	}

	@RequestMapping("/edit")
	public ModelAndView editSave(@RequestParam Integer q) {
		ModelAndView result;

		Gym gym= gymService.findOne(q);

		result = new ModelAndView("gym/edit");
		result.addObject("gym", gym);
		result.addObject("message", null);  
		result.addObject("url", "gym/save-edit.do");


		return result;
	}

	@RequestMapping("/delete")
	public ModelAndView delete(@RequestParam Integer q) {
		ModelAndView result;

		result = new ModelAndView("gym/delete");
		result.addObject("gym", q);
		result.addObject("message", null);  
		result.addObject("url", "gym/delete-delete.do");


		return result;
	}

	@RequestMapping("/delete-delete")
	public ModelAndView deleteDelete(@RequestParam Integer q) {
		ModelAndView result;

		Gym gym= gymService.findOne(q);

		try {
			gymService.delete(gym);
			result = new ModelAndView("redirect:/welcome/index.do");
		}catch(Throwable oops){
			result = new ModelAndView("gym/edit");
			result.addObject("gym", gym);
			result.addObject("url","gym/save.do");
			result.addObject("message", "gym.commit.error"); }



		return result;
	}


}
