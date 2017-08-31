package services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Administrator;
import domain.Annotation;
import repositories.AdministratorRepository;
import security.Authority;
import security.UserAccount;

@Service
@Transactional
public class AdministratorService {

	//Manager repositories

	@Autowired
	private AdministratorRepository administratorRepository;

	//Constructor

	public AdministratorService() {
		super();
	}

	//CRUD Methods

	public Administrator create() {
		Administrator admin= new Administrator();

		admin.setActorName(new String());
		admin.setAnnotationStore(new ArrayList<Annotation>());
		admin.setAnnotationWriter(new ArrayList<Annotation>());
		admin.setCity(new String());
		admin.setCountry(new String());
		admin.setEmail(new String());
		admin.setPhone(new String());
		admin.setPostalAddress(new String());
		admin.setSurname(new String());

		Authority auth = new Authority();
		auth.setAuthority("CUSTOMER");
		UserAccount account= new UserAccount();
		account.setAuthorities(Arrays.asList(auth));

		admin.setUserAccount(account);

		return admin;
	}

	public void delete(Administrator arg0) {
		Assert.notNull(arg0);
		administratorRepository.delete(arg0);
	}

	public boolean exists(Integer arg0) {
		Assert.notNull(arg0);
		return administratorRepository.exists(arg0);
	}

	public List<Administrator> findAll() {
		return administratorRepository.findAll();
	}

	public Administrator findOne(Integer arg0) {
		Assert.notNull(arg0);
		return administratorRepository.findOne(arg0);
	}

	public List<Administrator> save(List<Administrator> entities) {
		Assert.notNull(entities);
		Assert.noNullElements(entities.toArray());
		return administratorRepository.save(entities);
	}

	public Administrator save(Administrator administrator) {
		Assert.notNull(administrator);
		Administrator admin = null;

		if(exists(administrator.getId())){
			admin = findOne(administrator.getId());
			admin.setActorName(administrator.getActorName());
			admin.setCity(administrator.getCity());
			admin.setCountry(administrator.getCountry());
			admin.setEmail(administrator.getEmail());
			admin.setPhone(administrator.getPhone());
			admin.setPostalAddress(administrator.getPostalAddress());
			admin.setSurname(administrator.getSurname());
			admin.setAnnotationStore(administrator.getAnnotationStore());
			admin.setAnnotationWriter(administrator.getAnnotationWriter());
			admin = administratorRepository.save(admin);
		}else{
			admin = administratorRepository.save(administrator);
		}

		return admin;
	}

	//Others Methods

	public Object[] avgDesviationStarsByAdministrators() {
		 Object[] res = administratorRepository.avgDesviationStarsByAdministrators();
		 if(res[1]==null) {
			 res[1]=0.0;
		 } 
		 if(res[0]==null) {
			 res[0]=0.0;
		 }
		return res;
	}

	public Object[] avgDesviationNotesByAdministrators() {
		Object[] a = administratorRepository.avgDesviationNotesByAdministrators();
		if(a[1]==null) {
			a[1]=0.0;
		}
		return a;
	}

	public Double avgStarsCountryByAdministrators() {
		Double res = administratorRepository.avgStarsCountryByAdministrators();
		if(res==null) {
			res=0.0;
		}
		return res;
	}

	public Double avgStarsCityByAdministrators() {
		Double res = administratorRepository.avgStarsCityByAdministrators();
		if(res==null) {
			res=0.0;
		}
		return res;
	}

	public Double avgStarsByAdministrator(int administrator_id) {
		Assert.notNull(administrator_id);
		return administratorRepository.avgStarsByAdministrator(administrator_id);
	}





}
