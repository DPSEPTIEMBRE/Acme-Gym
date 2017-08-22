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

import domain.Activity;
import domain.Administrator;
import domain.Annotation;
import domain.Customer;
import domain.Gym;
import domain.Manager;
import domain.TabuWord;
import domain.Trainer;
import security.LoginService;
import services.ActivityService;
import services.AdministratorService;
import services.AnnotationService;
import services.CustomerService;
import services.GymService;
import services.ManagerService;
import services.TabuWordService;
import services.TrainerService;

@Controller
@RequestMapping("/annotation")
public class AnnotationController extends AbstractController {

	private static Integer actual=null;
	private static Integer toSave=null;
	
	//Services

	@Autowired
	private AnnotationService annotationService;
	
	@Autowired
	private GymService gymService;
	
	@Autowired
	private TrainerService trainerService;
	
	@Autowired
	private ManagerService managerService;
	
	@Autowired
	private ActivityService activityService;

	@Autowired
	private AdministratorService administratorService;
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private LoginService loginService;
	
	@Autowired
	private TabuWordService tabuWordService;

	// Constructors -----------------------------------------------------------

	public AnnotationController() {
		super();
	}

	//Actions

	@RequestMapping("/listByGym.do")
	public ModelAndView list(@RequestParam Integer q) {
		ModelAndView result;

		List<Annotation> annotations= new ArrayList<Annotation>();
		annotations.addAll(gymService.annotationsByGym(q));

		List<TabuWord> tabus = tabuWordService.findAll();
		List<Annotation> aux= new ArrayList<Annotation>();
		for(Annotation a: annotations) {
			for(TabuWord t: tabus) {
				if(a.getText().contains(t.getName())) {
					a.setText(changeText(a.getText(),t.getName()));
				}
			}
			aux.add(a);
		}
		annotations=aux;
		
		result = new ModelAndView("annotation/list");
		result.addObject("annotations", annotations);

		return result;
	}
	
	@RequestMapping("/listByTrainer.do")
	public ModelAndView listByTrainer(@RequestParam Integer q) {
		ModelAndView result;

		List<Annotation> annotations= new ArrayList<Annotation>();
		
		annotations.addAll(trainerService.annotationsByTrainer(q));
		
		List<TabuWord> tabus = tabuWordService.findAll();
		List<Annotation> aux= new ArrayList<Annotation>();
		for(Annotation a: annotations) {
			for(TabuWord t: tabus) {
				if(a.getText().contains(t.getName())) {
					a.setText(changeText(a.getText(),t.getName()));
				}
			}
			aux.add(a);
		}
		annotations=aux;
		
		result = new ModelAndView("annotation/list");
		result.addObject("annotations", annotations);
	

		return result;
	}
	
	@RequestMapping("/listByManager.do")
	public ModelAndView listByManager(@RequestParam Integer q) {
		ModelAndView result;

		List<Annotation> annotations= new ArrayList<Annotation>();
		
		annotations.addAll(managerService.annotationsByManager(q));
		
		List<TabuWord> tabus = tabuWordService.findAll();
		List<Annotation> aux= new ArrayList<Annotation>();
		for(Annotation a: annotations) {
			for(TabuWord t: tabus) {
				if(a.getText().contains(t.getName())) {
					a.setText(changeText(a.getText(),t.getName()));
				}
			}
			aux.add(a);
		}
		annotations=aux;
		
		result = new ModelAndView("annotation/list");
		result.addObject("annotations", annotations);

		return result;
	}
	
	@RequestMapping("/listByActivity.do")
	public ModelAndView listByActivity(@RequestParam Integer q) {
		ModelAndView result;

		List<Annotation> annotations= new ArrayList<Annotation>();
		
		annotations.addAll(activityService.annotationsByActivity(q));
		
		List<TabuWord> tabus = tabuWordService.findAll();
		List<Annotation> aux= new ArrayList<Annotation>();
		for(Annotation a: annotations) {
			for(TabuWord t: tabus) {
				if(a.getText().contains(t.getName())) {
					a.setText(changeText(a.getText(),t.getName()));
				}
			}
			aux.add(a);
		}
		annotations=aux;
		
		result = new ModelAndView("annotation/list");
		result.addObject("annotations", annotations);
		
		return result;
	}
	
	@RequestMapping("/create.do")
	public ModelAndView create(@RequestParam Integer q) {
		ModelAndView result;

		Annotation annotation= annotationService.create();
		
		actual=LoginService.getPrincipal().getId();
		toSave=q;
		
		result = new ModelAndView("annotation/create");
		result.addObject("annotation", annotation);
		if(activityService.exists(q)) {
			result.addObject("url","annotation/save-activity.do");
		}else if(administratorService.exists(q)){
			result.addObject("url","annotation/save-administrator.do");
		}else if(customerService.exists(q)) {
			result.addObject("url","annotation/save-customer.do");
		}else if(managerService.exists(q)) {
			result.addObject("url","annotation/save-manager.do");
		}else if(trainerService.exists(q)) {
			result.addObject("url","annotation/save-trainer.do");
		}else if(gymService.exists(q)) {
			result.addObject("url","annotation/save-gym.do");
		}
		

		return result;
	}
	
	@RequestMapping(value="/save-activity", method = RequestMethod.POST, params = "save")
	public ModelAndView saveActivity(@Valid Annotation annotation, BindingResult binding){
		ModelAndView result;
		if(binding.hasErrors()){
			result=  new ModelAndView("annotation/create");
			result.addObject("annotation", annotation);
			result.addObject("url", "annotation/save-activity.do");
		}else{
			try{
				annotation.setActorWrites(loginService.findActorByUserName(actual));
				annotation.setActivity(activityService.findOne(toSave));
				annotation.setActorStores(null);
				annotation.setGym(null);
				annotation = annotationService.save(annotation);
				Activity act= annotation.getActivity();
				List<Annotation> annots= act.getAnnotations();
				annots.add(annotation);
				act.setAnnotations(annots);
				activityService.save(act);
				result = new ModelAndView("redirect:/welcome/index.do");
				actual=null;
				toSave=null;
			}catch(Throwable e){
				result=  new ModelAndView("annotation/create");
				result.addObject("annotation", annotation);
				result.addObject("url", "annotation/save-activity.do");
				result.addObject("message", "annotation.failed.commit");
			}
		}
		return result;
	}
	
	@RequestMapping(value="/save-administrator", method = RequestMethod.POST, params = "save")
	public ModelAndView saveAdministrator(@Valid Annotation annotation, BindingResult binding){
		ModelAndView result;
		if(binding.hasErrors()){
			result=  new ModelAndView("annotation/create");
			result.addObject("annotation", annotation);
			result.addObject("url", "annotation/save-administrator.do");
		}else{
			try{
				annotation.setActorWrites(loginService.findActorByUserName(actual));
				annotation.setActivity(null);
				annotation.setActorStores(administratorService.findOne(toSave));
				annotation.setGym(null);
				annotation = annotationService.save(annotation);
				Administrator admin= (Administrator)annotation.getActorStores();
				List<Annotation> annots= admin.getAnnotationStore();
				annots.add(annotation);
				admin.setAnnotationStore(annots);
				administratorService.save(admin);
				result = new ModelAndView("redirect:/welcome/index.do");
				actual=null;
				toSave=null;
			}catch(Throwable e){
				result=  new ModelAndView("annotation/create");
				result.addObject("annotation", annotation);
				result.addObject("url", "annotation/save-administrator.do");
				result.addObject("message", "annotation.failed.commit");
			}
		}
		return result;
	}
	
	@RequestMapping(value="/save-customer", method = RequestMethod.POST, params = "save")
	public ModelAndView saveCustomer(@Valid Annotation annotation, BindingResult binding){
		ModelAndView result;
		if(binding.hasErrors()){
			result=  new ModelAndView("annotation/create");
			result.addObject("annotation", annotation);
			result.addObject("url", "annotation/save-customer.do");
		}else{
			try{
				annotation.setActorWrites(loginService.findActorByUserName(actual));
				annotation.setActivity(null);
				annotation.setActorStores(customerService.findOne(toSave));
				annotation.setGym(null);
				annotation = annotationService.save(annotation);
				Customer custom= (Customer)annotation.getActorStores();
				List<Annotation> annots= custom.getAnnotationStore();
				annots.add(annotation);
				custom.setAnnotationStore(annots);
				customerService.save(custom);
				result = new ModelAndView("redirect:/welcome/index.do");
				actual=null;
				toSave=null;
			}catch(Throwable e){
				result=  new ModelAndView("annotation/create");
				result.addObject("annotation", annotation);
				result.addObject("url", "annotation/save-customer.do");
				result.addObject("message", "annotation.failed.commit");
			}
		}
		return result;
	}
	
	@RequestMapping(value="/save-manager", method = RequestMethod.POST, params = "save")
	public ModelAndView saveManager(@Valid Annotation annotation, BindingResult binding){
		ModelAndView result;
		if(binding.hasErrors()){
			result=  new ModelAndView("annotation/create");
			result.addObject("annotation", annotation);
			result.addObject("url", "annotation/save-manager.do");
		}else{
			try{
				annotation.setActorWrites(loginService.findActorByUserName(actual));
				annotation.setActivity(null);
				annotation.setActorStores(managerService.findOne(toSave));
				annotation.setGym(null);
				annotation = annotationService.save(annotation);
				Manager mana= (Manager)annotation.getActorStores();
				List<Annotation> annots= mana.getAnnotationStore();
				annots.add(annotation);
				mana.setAnnotationStore(annots);
				managerService.save(mana);
				result = new ModelAndView("redirect:/welcome/index.do");
				actual=null;
				toSave=null;
			}catch(Throwable e){
				result=  new ModelAndView("annotation/create");
				result.addObject("annotation", annotation);
				result.addObject("url", "annotation/save-manager.do");
				result.addObject("message", "annotation.failed.commit");
			}
		}
		return result;
	}
	
	@RequestMapping(value="/save-trainer", method = RequestMethod.POST, params = "save")
	public ModelAndView saveTrainer(@Valid Annotation annotation, BindingResult binding){
		ModelAndView result;
		if(binding.hasErrors()){
			result=  new ModelAndView("annotation/create");
			result.addObject("annotation", annotation);
			result.addObject("url", "annotation/save-trainer.do");
		}else{
			try{
				annotation.setActorWrites(loginService.findActorByUserName(actual));
				annotation.setActivity(null);
				annotation.setActorStores(trainerService.findOne(toSave));
				annotation.setGym(null);
				annotation = annotationService.save(annotation);
				Trainer trai= (Trainer)annotation.getActorStores();
				List<Annotation> annots= trai.getAnnotationStore();
				annots.add(annotation);
				trai.setAnnotationStore(annots);
				trainerService.save(trai);
				result = new ModelAndView("redirect:/welcome/index.do");
				actual=null;
				toSave=null;
			}catch(Throwable e){
				result=  new ModelAndView("annotation/create");
				result.addObject("annotation", annotation);
				result.addObject("url", "annotation/save-trainer.do");
				result.addObject("message", "annotation.failed.commit");
			}
		}
		return result;
	}
	
	@RequestMapping(value="/save-gym", method = RequestMethod.POST, params = "save")
	public ModelAndView saveGym(@Valid Annotation annotation, BindingResult binding){
		ModelAndView result;
		if(binding.hasErrors()){
			result=  new ModelAndView("annotation/create");
			result.addObject("annotation", annotation);
			result.addObject("url", "annotation/save-gym.do");
		}else{
			try{
				annotation.setActorWrites(loginService.findActorByUserName(actual));
				annotation.setActivity(null);
				annotation.setActorStores(null);
				annotation.setGym(gymService.findOne(toSave));
				annotation = annotationService.save(annotation);
				Gym gym= annotation.getGym();
				List<Annotation> annots= gym.getAnnotations();
				annots.add(annotation);
				gym.setAnnotations(annots);
				gymService.save(gym);
				result = new ModelAndView("redirect:/welcome/index.do");
				actual=null;
				toSave=null;
			}catch(Throwable e){
				result=  new ModelAndView("annotation/create");
				result.addObject("annotation", annotation);
				result.addObject("url", "annotation/save-gym.do");
				result.addObject("message", "annotation.failed.commit");
			}
		}
		return result;
	}
	
	
	@RequestMapping("/delete.do")
	public ModelAndView delete(@RequestParam Integer q) {
		ModelAndView result;

		Annotation annotation= annotationService.findOne(q);
		try {
			annotationService.delete(annotation);
			result = new ModelAndView("redirect:/welcome/index.do");
		}catch (Throwable e) {
			result = new ModelAndView("redirect:/welcome/index.do");
		}
		
		return result;
	}
	
	private String changeText(String text, String tabu) {
		String res=null;
		String aux = "";
		for(int i=0;i<tabu.length();i++) {
			aux = aux + "*";
		}
		
		res= text.replaceAll(tabu, aux);
		
		return res;
	}
	
}
