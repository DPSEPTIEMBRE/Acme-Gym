package controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import domain.Actor;
import domain.Administrator;
import domain.Customer;
import domain.Manager;
import domain.Trainer;
import security.LoginService;
import services.AdministratorService;
import services.CustomerService;
import services.ManagerService;
import services.TrainerService;


@Controller
@RequestMapping("/actor")
public class ActorController extends AbstractController{
	
	@Autowired
	private LoginService loginService;
	@Autowired
	private AdministratorService administratorService;
	@Autowired
	private CustomerService customerService;
	@Autowired
	private ManagerService managerService;
	@Autowired
	private TrainerService trainerService;
	
	public ActorController(){
		super();
	}
	
	@RequestMapping(value="/edit", method= RequestMethod.GET)
	public ModelAndView edit(int userAccountID){
		ModelAndView result;
		Actor actor;
		
		actor = loginService.findActorByUserName(userAccountID);
		
		result= createEditModelAndView(actor);
		
		return result;
	}
	
	@RequestMapping(value="/signup", method= RequestMethod.GET)
	public ModelAndView signup(@RequestParam int q){
		ModelAndView result=new ModelAndView("actor/signup");
		Actor actor = null;
		String url=null;

		
		if(q==1) {
			actor= customerService.create();
			url="actor/save-customer-create.do";
		}else if(q==2) {
			actor= managerService.create();
			url="actor/save-manager-create.do";
		}else if(q==3){
			actor = trainerService.create();
			url="actor/save-trainer-create.do";
		}

		
		result.addObject("actor", actor);
		result.addObject("customer", actor);
		result.addObject("trainer", actor);
		result.addObject("manager", actor);
		result.addObject("message", null);
		result.addObject("url", url);
		result.addObject("type", q);
		
		return result;
	}
	
	protected ModelAndView createEditModelAndView(Actor actor){
		ModelAndView result;
		
		result=createEditModelAndView(actor,null);
		
		return result;
	}
	
	protected ModelAndView createEditModelAndView(Actor actor,String message){
		ModelAndView result;
		
		result = new ModelAndView("actor/edit");
		result.addObject("actor", actor);
		result.addObject("administrator", actor);
		result.addObject("customer", actor);
		result.addObject("trainer", actor);
		result.addObject("manager", actor);
		result.addObject("message", message);
		
		return result;
	}
	
	@RequestMapping(value = "/save-administrator", method = RequestMethod.POST, params = "save")
	public ModelAndView saveAdministrator(@Valid Administrator actor, BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			result = createEditModelAndView(actor);
		} else {
			try {
				administratorService.save(actor);
				result = new ModelAndView("redirect:/welcome/index.do");
			} catch (Throwable oops) {
				result = createEditModelAndView(actor, "actor.commit.error"); } }

		return result; }
	
	@RequestMapping(value="/save-customer", method = RequestMethod.POST, params = "save")
	public ModelAndView saveCustomer(@Valid Customer actor, BindingResult binding){
		ModelAndView result;
		if(binding.hasErrors()){
			result = createEditModelAndView(actor);
		}else{
			try{
				customerService.save(actor);
				result = new ModelAndView("redirect:/welcome/index.do");
			}catch(Throwable e){
				result = createEditModelAndView(actor, "actor.commit.error");
			}
		}
		return result;
	}
	
	@RequestMapping(value="/save-trainer", method = RequestMethod.POST, params = "save")
	public ModelAndView saveTrainer(@Valid Trainer actor, BindingResult binding){
		ModelAndView result;
		if(binding.hasErrors()){
			result = createEditModelAndView(actor);
		}else{
			try{
				trainerService.save(actor);
				result = new ModelAndView("redirect:/welcome/index.do");
			}catch(Throwable e){
				result = createEditModelAndView(actor, "actor.commit.error");
			}
		}
		return result;
	}
	
	@RequestMapping(value="/save-manager", method = RequestMethod.POST, params = "save")
	public ModelAndView saveManager(@Valid Manager actor, BindingResult binding){
		ModelAndView result;
		
		if(binding.hasErrors()){
			result = createEditModelAndView(actor);
		}else{
			try{
				managerService.save(actor);
				result = new ModelAndView("redirect:/welcome/index.do");
			}catch(Throwable e){
				result = createEditModelAndView(actor, "actor.commit.error");
			}
		}
		return result;
	}
	
	
	@RequestMapping(value="/save-customer-create", method = RequestMethod.POST, params = "save")
	public ModelAndView saveCustomerCreate(@Valid Customer actor, BindingResult binding){
		ModelAndView result;
		if(binding.hasErrors()){
			result=  new ModelAndView("actor/signup");
			result.addObject("actor", actor);
			result.addObject("customer", actor);
			result.addObject("trainer", actor);
			result.addObject("manager", actor);
			result.addObject("message", null);
			result.addObject("url", "actor/save-customer-create.do");
		}else{
			try{
				customerService.save(actor);
				result = new ModelAndView("redirect:/welcome/index.do");
			}catch(Throwable e){
				result=  new ModelAndView("actor/signup");
				result.addObject("actor", actor);
				result.addObject("customer", actor);
				result.addObject("trainer", actor);
				result.addObject("manager", actor);
				result.addObject("message", "actor.commit.error");
				result.addObject("url", "actor/save-manager-create.do");
			}
		}
		return result;
	}
	
	@RequestMapping(value="/save-trainer-create", method = RequestMethod.POST, params = "save")
	public ModelAndView saveTrainerCreate(@Valid Trainer actor, BindingResult binding){
		ModelAndView result;
		if(binding.hasErrors()){
			result=  new ModelAndView("actor/signup");
			result.addObject("actor", actor);
			result.addObject("customer", actor);
			result.addObject("trainer", actor);
			result.addObject("manager", actor);
			result.addObject("message", null);
			result.addObject("url", "actor/save-trainer-create.do");
		}else{
			try{
				trainerService.save(actor);
				result = new ModelAndView("redirect:/welcome/index.do");
			}catch(Throwable e){
				result=  new ModelAndView("actor/signup");
				result.addObject("actor", actor);
				result.addObject("customer", actor);
				result.addObject("trainer", actor);
				result.addObject("manager", actor);
				result.addObject("message", "actor.commit.error");
				result.addObject("url", "actor/save-manager-create.do");
			}
		}
		return result;
	}
	
	@RequestMapping(value="/save-manager-create", method = RequestMethod.POST, params = "save")
	public ModelAndView saveManagerCreate(@Valid Manager actor, BindingResult binding){
		ModelAndView result;
		if(binding.hasErrors()){
			result=  new ModelAndView("actor/signup");
			result.addObject("actor", actor);
			result.addObject("customer", actor);
			result.addObject("trainer", actor);
			result.addObject("manager", actor);
			result.addObject("message", null);
			result.addObject("url", "actor/save-manager-create.do");
		}else{
			try{
				managerService.save(actor);
				result = new ModelAndView("redirect:/welcome/index.do");
			}catch(Throwable e){
				result=  new ModelAndView("actor/signup");
				result.addObject("actor", actor);
				result.addObject("customer", actor);
				result.addObject("trainer", actor);
				result.addObject("manager", actor);
				result.addObject("message", "actor.commit.error");
				result.addObject("url", "actor/save-manager-create.do");
			}
		}
		return result;
	}
}
