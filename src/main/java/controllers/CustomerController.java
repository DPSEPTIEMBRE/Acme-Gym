package controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import domain.Customer;
import services.CustomerService;

@Controller
@RequestMapping("/customer")
public class CustomerController {

	@Autowired
	CustomerService customerService;


	//Constructor
	public CustomerController() {
		super();
	}

	//Creation
	@RequestMapping(value = "/actor/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;

		result = createNewModelAndView(customerService.create(), null);

		return result;
	}

	@RequestMapping(value = "/actor/save", method = RequestMethod.POST, params="save")
	public ModelAndView saveCreate(@Valid Customer user, BindingResult binding) {
		ModelAndView result;

		for(FieldError e : binding.getFieldErrors()) {
			System.err.println(e.getField());
			System.err.println(e.getDefaultMessage());
		}

		if (binding.hasErrors()) {
			result = createNewModelAndView(user, null);
		} else {
			try {
				customerService.save_create(user);
				result = new ModelAndView("redirect:/welcome/index.do");
			} catch (Throwable th) {
				result = createNewModelAndView(user, "customer.commit.error");
			}
		}
		return result;
	}

	protected ModelAndView createNewModelAndView(Customer customer, String message) {
		ModelAndView result;
		result = new ModelAndView("customer/create");
		result.addObject("customer", customer);
		result.addObject("message", message);
		return result;
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;

		result = new ModelAndView("customer/list");
		result.addObject("customer", customerService.findAll());

		return result;
	}
	
	/*@RequestMapping(value = "/actor/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;

		result = createNewModelAndView(customerService.create(), null);

		return result;
	}
*/

	@RequestMapping(value = "/actor/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam Customer customer) {
		ModelAndView result = createEditModelAndView(customer, null);
		
		result.addObject(customer);
		
		return result;
	}

	@RequestMapping(value="/actor/edit", method=RequestMethod.POST, params = "save")
	public ModelAndView saveEdit(@Valid Customer customer, BindingResult binding) {
		ModelAndView result;
		if (binding.hasErrors()) {
			result = createEditModelAndView(customer, null);
		} else {
			try {
				customerService.save(customer);
				result = new ModelAndView("redirect:/customer/list.do");
			} catch (Throwable th) {
				result = createEditModelAndView(customer, "customer.commit.error");
			}
		}
		return result;
	}

	protected ModelAndView createEditModelAndView(Customer customer, String message) {

		ModelAndView result = new ModelAndView("customer/edit");
		result.addObject("customer", customer);
		result.addObject("message", message);

		return result;
	}

}
