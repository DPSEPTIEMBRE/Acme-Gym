package services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;


import repositories.CustomerRepository;
import security.Authority;
import security.UserAccount;
import domain.Activity;
import domain.Annotation;
import domain.Customer;
import domain.Gym;

@Service
@Transactional
public class CustomerService {


	//Manager repositories

	@Autowired
	private CustomerRepository customerRepository;

	//Constructor

	public CustomerService() {
		super();
	}

	//CRUD Methods

	public Customer create() {
		Customer customer = new Customer();

		customer.setActorName(new String());
		customer.setSurname(new String());
		customer.setEmail(new String());
		customer.setPhone(new String());
		customer.setPostalAddress(new String());
		customer.setCity(new String());
		customer.setCountry(new String());
		customer.setAnnotationWriter(new ArrayList<Annotation>());
		customer.setAnnotationStore(new ArrayList<Annotation>());
		customer.setGyms(new ArrayList<Gym>());
		customer.setActivities(new ArrayList<Activity>());
		
		/*Authority a = new Authority();
		a.setAuthority("CUSTOMER");
		Collection<Authority> authorities = new ArrayList<Authority>();
		authorities.add(a);
		UserAccount ua = new UserAccount();
		ua.setUsername(new String());
		ua.setPassword(new String());
		ua.setAuthorities(authorities);
		customer.setUserAccount(ua);*/
		
		Authority a = new Authority();
		a.setAuthority(Authority.CUSTOMER);
		UserAccount account = new UserAccount();
		account.setAuthorities(Arrays.asList(a));
		customer.setUserAccount(account);
		System.out.println(customer.getUserAccount());
		
		return customer;
	}

	public void save_create(Customer user){
		
		Md5PasswordEncoder password = new Md5PasswordEncoder();
		String encodedPassword = password.encodePassword(user.getUserAccount().getPassword(), null);
		user.getUserAccount().setPassword(encodedPassword);
		Assert.notNull(user);
		customerRepository.save(user);
	}
	
	public List<Customer> findAll() {
		return customerRepository.findAll();
	}	

	public void delete(Customer arg0) {
		Assert.notNull(arg0);
		customerRepository.delete(arg0);
	}

	public Customer findOne(Integer arg0) {
		Assert.notNull(arg0);
		return customerRepository.findOne(arg0);
	}

	public List<Customer> save(List<Customer> entities) {
		Assert.notNull(entities);
		Assert.noNullElements(entities.toArray());
		return customerRepository.save(entities);
	}

	public void save(Customer arg0) {
		Assert.notNull(arg0);
		customerRepository.save(arg0);
	}
	
	public boolean exists(Integer arg0) {
		return customerRepository.exists(arg0);
	}

	//Others Methods

	public List<Customer> customersWithMoreActivities() {
		return customerRepository.customersWithMoreActivities();
	}

	public Object[] minMaxAvgDesviationGymsByCustomers() {
		return customerRepository.minMaxAvgDesviationGymsByCustomers();
	}

	public Object[] avgDesviationNotesWrittersByCustomers() {
		return customerRepository.avgDesviationNotesWrittersByCustomers();
	}

	public Object[] avgDesviationNotesStoreByCustomers() {
		return customerRepository.avgDesviationNotesStoreByCustomers();
	}

	public Object[] avgDesviationStarsByCustomers() {
		return customerRepository.avgDesviationStarsByCustomers();
	}

	public Double avgStarsCountryByCustomers() {
		return customerRepository.avgStarsCountryByCustomers();
	}

	public Double avgStarsCityByCustomers() {
		return customerRepository.avgStarsCityByCustomers();
	}
}
