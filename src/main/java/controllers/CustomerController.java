package controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import domain.Activity;
import domain.Customer;
import domain.Gym;
import security.LoginService;
import services.ActivityService;
import services.CustomerService;
import services.GymService;

@Controller
@RequestMapping("/customer")
public class CustomerController extends AbstractController{

	//Services

	@Autowired
	private CustomerService customerService;

	@Autowired
	private LoginService loginService;
	
	@Autowired
	private GymService gymService;
	
	@Autowired
	private ActivityService activityService;

	// Constructors -----------------------------------------------------------

	public CustomerController() {
		super();
	}

	//Actions

	@RequestMapping("/gym/list")
	public ModelAndView listGyms(@RequestParam Integer q,@RequestParam Integer a) {
		ModelAndView result;

		Customer customer= (Customer)loginService.findActorByUserName(q);

		result = new ModelAndView("gym/list");
		result.addObject("gyms", customer.getGyms());
		result.addObject("a", a);

		return result;
	}
	
	@RequestMapping("/gym/join")
	public ModelAndView joinGym(@RequestParam Integer q) {
		ModelAndView result;

		Gym gym= gymService.findOne(q);
		Customer actual=(Customer)loginService.findActorByUserName(LoginService.getPrincipal().getId());
		
		List<Customer> customers= gym.getCustomers();
		List<Gym> gyms= actual.getGyms();
		customers.add(actual);
		gym.setCustomers(customers);
		gyms.add(gym);
		actual.setGyms(gyms);
		
		try {
			gymService.save(gym);
			customerService.save(actual);
			result = new ModelAndView("redirect:/welcome/index.do");
		}catch (Throwable e) {
			result = new ModelAndView("gym/list");
			result.addObject("gyms", actual.getGyms());
			result.addObject("a", 1);
		}

		return result;
	}
	
	@RequestMapping("/gym/leave")
	public ModelAndView leaveGym(@RequestParam Integer q) {
		ModelAndView result;

		Gym gym= gymService.findOne(q);
		Customer actual=(Customer)loginService.findActorByUserName(LoginService.getPrincipal().getId());
		
		List<Customer> customers= gym.getCustomers();
		List<Gym> gyms= actual.getGyms();
		customers.remove(actual);
		gym.setCustomers(customers);
		gyms.remove(gym);
		actual.setGyms(gyms);
		
		try {
			gymService.save(gym);
			customerService.save(actual);
			result = new ModelAndView("redirect:/welcome/index.do");
		}catch (Throwable e) {
			result = new ModelAndView("gym/list");
			result.addObject("gyms", actual.getGyms());
			result.addObject("a", 1);
		}

		return result;
	}
	
	
	@RequestMapping("/activity/list")
	public ModelAndView listActivities(@RequestParam Integer q,@RequestParam Integer a) {
		ModelAndView result;

		Customer customer= (Customer)loginService.findActorByUserName(q);
		List<Activity> activitiesLeave = new ArrayList<Activity>();
		if(a==3) {
			for(Gym gym: customer.getGyms()) {
				for(Activity act: gym.getActivities()) {
					if(!act.getCustomers().contains(customer)) {
						activitiesLeave.add(act);
					}
				}
			}
		}else {
			for(Gym gym: customer.getGyms()) {
				for(Activity act: gym.getActivities()) {
					if(act.getCustomers().contains(customer)) {
						activitiesLeave.add(act);
					}
				}
			}
		}
		
		
		
		result = new ModelAndView("activity/list");
		result.addObject("activities", activitiesLeave);
		result.addObject("a", a);

		return result;
	}
	
	
	@RequestMapping("/activity/join")
	public ModelAndView joinActivity(@RequestParam Integer q) {
		ModelAndView result;

		Activity act= activityService.findOne(q);
		Customer actual=(Customer)loginService.findActorByUserName(LoginService.getPrincipal().getId());
		List<Customer> customers=act.getCustomers();
		customers.add(actual);
		act.setCustomers(customers);

		try {
			activityService.save(act);
			
			result = new ModelAndView("redirect:/welcome/index.do");
		}catch (Throwable e) {
			result = new ModelAndView("redirect:/welcome/index.do");
		}

		return result;
	}
	
	@RequestMapping("/activity/leave")
	public ModelAndView leaveActivity(@RequestParam Integer q) {
		ModelAndView result;

		Activity act= activityService.findOne(q);
		Customer actual=(Customer)loginService.findActorByUserName(LoginService.getPrincipal().getId());
		List<Customer> customers=act.getCustomers();
		customers.remove(actual);
		act.setCustomers(customers);

		try {
			activityService.save(act);
			
			result = new ModelAndView("redirect:/welcome/index.do");
		}catch (Throwable e) {
			result = new ModelAndView("redirect:/welcome/index.do");
		}

		return result;
	}
	
	
}
