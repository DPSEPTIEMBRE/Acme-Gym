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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import services.AdministratorService;
import services.CustomerService;
import services.GymService;
import services.ManagerService;
import services.TrainerService;

@Controller
@RequestMapping("/administrator")
public class AdministratorController extends AbstractController {
	
	// Services -------------------------------------------------------
		@Autowired
		private AdministratorService administratorService;

		@Autowired
		private GymService gymService;

		@Autowired
		private ManagerService managerService;

		@Autowired
		private CustomerService	customerService;

		@Autowired
		private TrainerService trainerService;


	// Constructors -----------------------------------------------------------

	public AdministratorController() {
		super();
	}

	// Action-1 ---------------------------------------------------------------		

	@RequestMapping("/action-1")
	public ModelAndView action1() {
		ModelAndView result;

		result = new ModelAndView("administrator/action-1");

		return result;
	}

	// Action-2 ---------------------------------------------------------------

	@RequestMapping("/action-2")
	public ModelAndView action2() {
		ModelAndView result;

		result = new ModelAndView("administrator/action-2");

		return result;
	}
	
	@RequestMapping("/dashboard")
	public ModelAndView dashboard() {
		ModelAndView result;

		result = new ModelAndView("administrator/dashboard");

		//Level C
		//El mínimo, máximo, media y desviación estándar del número de gimnasios por mánager
		result.addObject("avgGymsManager", managerService.minMaxAvgDesviationGymsByManagers());
		

		//El mínimo, máximo, media y desviación estándar del número de gimnasios por cliente.
		result.addObject("avgGymsCustomer", customerService.minMaxAvgDesviationGymsByCustomers());

		//El mínimo, máximo, media y desviación estándar del número de clientes por gimnasio.
		result.addObject("avgCustomersGym", gymService.minMaxAvgDesviationCustomersForGym());

		//El gimnasio que ofrece más actividades. La cuenta debe ignorar actividades canceladas.
		result.addObject("gymMoreActivities", gymService.gymWithMoreActivities());

		//Los clientes que se han apuntado a más actividades.
		result.addObject("customerMoreActivities", customerService.customersWithMoreActivities());

		//Level B
		//La media y desviación estándar del número de notas por entidad apropiada.
		result.addObject("avgDesvAnnotationAdministratorWritten", administratorService.avgDesviationNotesWrittersByAdministrators());
		result.addObject("avgDesvAnnotationAdministratorStored", administratorService.avgDesviationNotesStoreByAdministrators());
		result.addObject("avgDesvAnnotationManagerWritten", managerService.avgDesviationNotesWrittersByManagers());
		result.addObject("avgDesvAnnotationManagerStored", managerService.avgDesviationNotesStoreByManagers());
		result.addObject("avgDesvAnnotationCustomerWritten", customerService.avgDesviationNotesWrittersByCustomers());
		result.addObject("avgDesvAnnotationCustomerStored", customerService.avgDesviationNotesStoreByCustomers());
		result.addObject("avgDesvAnnotationTrainerWritten", trainerService.avgDesviationNotesWrittersByTrainers());
		result.addObject("avgDesvAnnotationTrainerStored", trainerService.avgDesviationNotesStoreByTrainers());

		//La media y desviación estándar del número de estrellas por entidad apropiada.
		result.addObject("avgDesvStarsAdministrator", administratorService.avgDesviationStarsByAdministrators());
		result.addObject("avgDesvStarsManager", managerService.avgDesviationStarsByManagers());
		result.addObject("avgDesvStarsCustomer", customerService.avgDesviationStarsByCustomers());
		result.addObject("avgDesvStarsTrainer", trainerService.avgDesviationStarsByTrainers());

		//La media de estrellas por actor, agrupadas por país.
		result.addObject("avgStarsAdministratorCountry", administratorService.avgStarsCountryByAdministrators());
		result.addObject("avgStarsManagerCountry", managerService.avgStarsCountryByManagers());
		result.addObject("avgStarsCustomerCountry", customerService.avgStarsCountryByCustomers());
		result.addObject("avgStarsTrainerCountry", trainerService.avgStarsCountryByTrainers());

		//La media de estrellas por actor, agrupadas por ciudad.
		result.addObject("avgStarsAdministratorCity", administratorService.avgStarsCityByAdministrators());
		result.addObject("avgStarsManagerCity", managerService.avgStarsCityByManagers());
		result.addObject("avgStarsCustomerCity", customerService.avgStarsCityByCustomers());
		result.addObject("avgStarsTrainerCity", trainerService.avgStarsCityByTrainers());

		return result;

	}
}
