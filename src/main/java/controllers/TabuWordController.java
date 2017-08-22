package controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import domain.TabuWord;
import services.TabuWordService;

@Controller
@RequestMapping("/tabuWord")
public class TabuWordController extends AbstractController{

	//Services
	
	@Autowired
	private TabuWordService tabuWordService;
	

	// Constructors -----------------------------------------------------------

	public TabuWordController() {
		super();
	}

	//Actions
	
	@RequestMapping("/list")
	public ModelAndView list() {
		ModelAndView result;

		List<TabuWord> tabus = tabuWordService.findAll();
		
		result = new ModelAndView("tabuWord/list");
		result.addObject("tabuWords", tabus);

		return result;
	}
	
	@RequestMapping("/create")
	public ModelAndView create() {
		ModelAndView result;

		TabuWord tabu = tabuWordService.create();
		
		result = new ModelAndView("tabuWord/create");
		result.addObject("tabuWord", tabu);
		result.addObject("url", "tabuWord/save.do");

		return result;
	}
	
	@RequestMapping(value = "/save", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid TabuWord tabu,BindingResult binding) {
		ModelAndView result;

		if(binding.hasErrors()) {
			result= new ModelAndView("tabuWord/create");
			result.addObject("tabuWord", tabu);
			result.addObject("message", "tabuWord.commit.error");
		}else {
			try {
				tabuWordService.save(tabu);
				result=new ModelAndView("redirect:/welcome/index.do");
			}catch (Throwable e) {
				result= new ModelAndView("tabuWord/create");
				result.addObject("tabuWord", tabu);
				result.addObject("message", "tabuWord.commit.error");
			}
		}

		return result;
	}
}
