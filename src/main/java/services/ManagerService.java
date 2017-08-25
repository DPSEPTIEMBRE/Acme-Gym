package services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Annotation;
import domain.Gym;
import domain.Manager;
import repositories.ManagerRepository;
import security.Authority;
import security.UserAccount;
import security.UserAccountRepository;

@Service
@Transactional
public class ManagerService {


	//Manager repositories

	@Autowired
	private ManagerRepository managerRepository;

	@Autowired
	private UserAccountRepository userAccountRepository;

//	@Autowired
//	private Md5PasswordEncoder encoder;
	

	//Constructor

	public ManagerService() {
		super();
	}

	//CRUD Methods

	public Manager create() {
		Manager manager= new Manager();

		manager.setActorName(new String());
		manager.setAnnotationStore(new ArrayList<Annotation>());
		manager.setAnnotationWriter(new ArrayList<Annotation>());
		manager.setCity(new String());
		manager.setCountry(new String());
		manager.setEmail(new String());
		manager.setGyms(new ArrayList<Gym>());
		manager.setPhone(new String());
		manager.setPostalAddress(new String());
		manager.setSurname(new String());

		Authority auth = new Authority();
		auth.setAuthority("MANAGER");
		UserAccount account= new UserAccount();
		account.setUsername(new String());
		account.setPassword(new String());
		account.setAuthorities(Arrays.asList(auth));
		account.setActivate(true);

		manager.setUserAccount(account);

		return manager;
	}

	public boolean exists(Integer arg0) {
		Assert.notNull(arg0);
		return managerRepository.exists(arg0);
	}

	public void delete(Manager arg0) {
		Assert.notNull(arg0);
		managerRepository.delete(arg0);
	}

	public List<Manager> findAll() {
		return managerRepository.findAll();
	}

	public Manager findOne(Integer arg0) {
		Assert.notNull(arg0);
		return managerRepository.findOne(arg0);
	}

	public List<Manager> save(List<Manager> entities) {
		Assert.notNull(entities);
		Assert.noNullElements(entities.toArray());
		return managerRepository.save(entities);
	}

	public Manager save(Manager manager) {
		Assert.notNull(manager);
		Manager mana = null;

		if(exists(manager.getId())){
			
			mana = findOne(manager.getId());
			mana.setActorName(manager.getActorName());
			mana.setCity(manager.getCity());
			mana.setCountry(manager.getCountry());
			mana.setEmail(manager.getEmail());
			mana.setPhone(manager.getPhone());
			mana.setPostalAddress(manager.getPostalAddress());
			mana.setSurname(manager.getSurname());
			mana.setGyms(manager.getGyms());
			mana.setUserAccount(manager.getUserAccount());
			
			mana = managerRepository.save(mana);
			

		}else{
			UserAccount account = manager.getUserAccount();
			account.setPassword(new Md5PasswordEncoder().encodePassword(account.getPassword(), null));
			account= userAccountRepository.save(account);
			manager.setUserAccount(account);
			mana = managerRepository.save(manager);
		}
		return mana;
	}


	//Others Methods


	public Object[] avgDesviationStarsByManagers() {
		Object[] res = managerRepository.avgDesviationStarsByManagers();
		 if(res==null) {
			 Object[] aux = {0.0,0.0};
			 res=aux;
		 }
		return res;
	}

	public Object[] avgDesviationNotesByManagers() {
		return managerRepository.avgDesviationNotesByManagers();
	}

	public Double avgStarsCountryByManagers() {
		Double res = managerRepository.avgStarsCountryByManagers();
		if(res==null) {
			res=0.0;
		}
		return res;
	}

	public Double avgStarsCityByManagers() {
		Double res = managerRepository.avgStarsCityByManagers();
		if(res==null) {
			res=0.0;
		}
		return res;
	}

	public List<Gym> gymsByManager(int manager_id) {
		Assert.notNull(manager_id);
		
		List<Gym> gyms=  managerRepository.gymsByManager(manager_id);
		List<Gym> res= new ArrayList<Gym>();
		
		for(Gym g: gyms) {
			if(!g.getIsDelete())
			{
				res.add(g);
			
			}
			}
		
		return res;
	}

	public Object[] minMaxAvgDesviationGymsByManagers() {
		return managerRepository.minMaxAvgDesviationGymsByManagers();
	}

	public List<Annotation> annotationsByManager(Integer manager_id) {
		return managerRepository.annotationsByManager(manager_id);
	}

	public Double avgStarsByManager(int manager_id) {
		Assert.notNull(manager_id);
		return managerRepository.avgStarsByManager(manager_id);
	}



}
