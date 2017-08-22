/*
 * AdministratorController.java
 * 
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import domain.Administrator;
import domain.Customer;
import domain.Gym;
import domain.Manager;
import security.UserAccount;
import security.UserAccountRepository;
import services.AdministratorService;
import services.CustomerService;
import services.GymService;
import services.ManagerService;
import services.TrainerService;

@Controller
@RequestMapping("/administrator")
public class AdministratorController extends AbstractController {
	
	//Services
	
	@Autowired
	private AdministratorService administratorService;
	
	@Autowired
	private ManagerService managerService;
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private GymService gymService;
	
	@Autowired
	private TrainerService trainerService;
	
	@Autowired
	private UserAccountRepository userAccountRepository;

	// Constructors -----------------------------------------------------------

	public AdministratorController() {
		super();
	}

	// Actions	

	@RequestMapping("/edit")
	public ModelAndView edit(@RequestParam Integer q) {
		ModelAndView result;

		Administrator administrator= administratorService.findOne(q);

		result = new ModelAndView("administrator	/edit");
		result.addObject("administrator", administrator);

		return result;
	}
	
	
	@RequestMapping("/manager/list")
	public ModelAndView managers(@RequestParam Integer a) {
		ModelAndView result;

		List<Manager> managers= managerService.findAll();
		
		if(a==2) {
			List<Manager> aux= new ArrayList<Manager>();
			for(Manager m: managers) {
				if(m.getUserAccount().isEnabled()) {
					aux.add(m);
				}
			}
			managers=aux;
		}else if (a==3) {
			List<Manager> aux= new ArrayList<Manager>();
			for(Manager m: managers) {
				if(!m.getUserAccount().isEnabled()) {
					aux.add(m);
				}
			}
			managers=aux;
		}
		
		result = new ModelAndView("manager/list");
		result.addObject("managers", managers);
		result.addObject("a", a);

		return result;
	}
	
	@RequestMapping("/manager/remove")
	public ModelAndView removeManager(@RequestParam Integer q) {
		ModelAndView result;

		Manager manager= managerService.findOne(q);
		try {
			UserAccount account= manager.getUserAccount();
			account.setActivate(false);
			account= userAccountRepository.save(account);
			manager.setUserAccount(account);
			managerService.save(manager);
			result = new ModelAndView("redirect:/welcome/index.do");
		}catch (Throwable e) {
			result = new ModelAndView("manager/list");
			List<Manager> managers= managerService.findAll();
			List<Manager> aux= new ArrayList<Manager>();
			for(Manager m: managers) {
				if(m.getUserAccount().isEnabled()) {
					aux.add(m);
				}
			}
			managers=aux;
			result.addObject("managers", managers);
			result.addObject("a", 2);
		}
		

		return result;
	}
	
	@RequestMapping("/manager/admit")
	public ModelAndView admitManager(@RequestParam Integer q) {
		ModelAndView result;

		Manager manager= managerService.findOne(q);
		try {
			UserAccount account= manager.getUserAccount();
			account.setActivate(true);
			account= userAccountRepository.save(account);
			manager.setUserAccount(account);
			managerService.save(manager);
			result = new ModelAndView("redirect:/welcome/index.do");
		}catch (Throwable e) {
			result = new ModelAndView("manager/list");
			List<Manager> managers= managerService.findAll();
			List<Manager> aux= new ArrayList<Manager>();
			for(Manager m: managers) {
				if(m.getUserAccount().isEnabled()) {
					aux.add(m);
				}
			}
			managers=aux;
			result.addObject("managers", managers);
			result.addObject("a", 2);
		}
		

		return result;
	}
	
	
	@RequestMapping("/dashboard")
	public ModelAndView dashboard() {
		ModelAndView result;

		result = new ModelAndView("administrator/dashboard");
		Object[] a1= managerService.minMaxAvgDesviationGymsByManagers();
		Object[] a2= customerService.minMaxAvgDesviationGymsByCustomers();
		Object[] a3= gymService.minMaxAvgDesviationCustomersForGym();
		List<Gym> gyms= gymService.gymWithMoreActivities();
		
		String a4= "";
		if(gyms.size()>1) {
			for(Gym g: gyms) {
				a4= a4 + ", " + g.getName();
			}
		} else {
			a4=gyms.get(0).getName();
		}
		
		a4 = a4.substring(1,a4.length());
		List<Customer> customers= customerService.customersWithMoreActivities();
		
		String a5= "";
		if(customers.size()>1) {
			for(Customer c: customers) {
				a5= a5 + ", " + c.getActorName();
			}
		} else {
			a5=customers.get(0).getActorName();
		}
		a5 = a5.substring(1,a5.length());
		
		Object[] a61 = administratorService.avgDesviationNotesByAdministrators();
		Object[] a62 = customerService.avgDesviationNotesByCustomers();
		Object[] a63 = trainerService.avgDesviationNotesByTrainers();
		Object[] a64 = managerService.avgDesviationNotesByManagers();
		Object[] a65 = gymService.avgDesviationNotesByGym();
		
		Object[] a71 = administratorService.avgDesviationStarsByAdministrators();
		Object[] a72 = customerService.avgDesviationStarsByCustomers();
		Object[] a73 = trainerService.avgDesviationStarsByTrainers();
		Object[] a74 = managerService.avgDesviationStarsByManagers();
		Object[] a75 = gymService.avgDesviationStarsByGym();
		
		result.addObject("a1", a1[0] + " | " + a1[1] + " | " + a1[2]);
		result.addObject("a2", a2[0] + " | " + a2[1] + " | " + a2[2]);
		result.addObject("a3", a3[0] + " | " + a3[1] + " | " + a3[2]);
		result.addObject("a4", a4);
		result.addObject("a5", a5);
		result.addObject("a6", a61[0] + " | " + 
							   a62[0] + " | " +
							   a63[0] +" | " +
							   a64[0] +" | " +
							   a65[0]);
		
		result.addObject("a7", a71[0] + " | " + 
				   			   a72[0] + " | " +
				   			   a73[0] +" | " +
				   			   a74[0] +" | " +
				   			   a75[0]);
		
		result.addObject("a8", administratorService.avgStarsCityByAdministrators() + " | " + 
	   			  customerService.avgStarsCityByCustomers() + " | " +
	   			  trainerService.avgStarsCityByTrainers()+" | " + 
	   			  managerService.avgStarsCityByManagers());
		
		result.addObject("a9", administratorService.avgStarsCountryByAdministrators() + " | " + 
	   			  customerService.avgStarsCountryByCustomers() + " | " +
	   			  trainerService.avgStarsCountryByTrainers()+" | " +
	   			  managerService.avgStarsCountryByManagers());


		return result;
	}



}
