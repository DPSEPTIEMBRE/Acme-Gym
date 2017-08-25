package services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Activity;
import domain.Annotation;
import domain.Customer;
import domain.Gym;
import repositories.CustomerRepository;
import security.Authority;
import security.UserAccount;
import security.UserAccountRepository;

@Service
@Transactional
public class CustomerService {

	//Manager repositories

	@Autowired
	private CustomerRepository customerRepository;

//	@Autowired
//	private Md5PasswordEncoder encoder;
	
	@Autowired
	private UserAccountRepository userAccountRepository;
	

	//Constructor

	public CustomerService() {
		super();
	}

	//CRUD Methods

	public Customer create() {
		Customer customer= new Customer();

		customer.setActorName(new String());
		customer.setAnnotationStore(new ArrayList<Annotation>());
		customer.setAnnotationWriter(new ArrayList<Annotation>());
		customer.setCity(new String());
		customer.setCountry(new String());
		customer.setEmail(new String());
		customer.setPhone(new String());
		customer.setPostalAddress(new String());
		customer.setSurname(new String());

		Authority auth = new Authority();
		auth.setAuthority("CUSTOMER");
		UserAccount account= new UserAccount();
		account.setAuthorities(Arrays.asList(auth));
		account.setUsername(new String());
		account.setPassword(new String());
		account.setActivate(true);

		customer.setUserAccount(account);
		customer.setActivities(new ArrayList<Activity>());
		customer.setGyms(new ArrayList<Gym>());

		return customer;
	}

	public boolean exists(Integer arg0) {
		Assert.notNull(arg0);
		return customerRepository.exists(arg0);
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

	public Customer save(Customer customer) {
		Assert.notNull(customer);
		Customer cust = null;

		if(exists(customer.getId())){
			cust = findOne(customer.getId());
			cust.setActorName(customer.getActorName());
			cust.setCity(customer.getCity());
			cust.setCountry(customer.getCountry());
			cust.setEmail(customer.getEmail());
			cust.setPhone(customer.getPhone());
			cust.setPostalAddress(customer.getPostalAddress());
			cust.setSurname(customer.getSurname());
			cust.setActivities(customer.getActivities());
			cust.setAnnotationStore(customer.getAnnotationStore());
			cust.setAnnotationWriter(customer.getAnnotationWriter());
			cust.setGyms(customer.getGyms());
			cust.setUserAccount(customer.getUserAccount());
			cust = customerRepository.save(cust);
		}else{
			UserAccount account = customer.getUserAccount();
			account.setPassword(new Md5PasswordEncoder().encodePassword(account.getPassword(), null));
			account= userAccountRepository.save(account);
			customer.setUserAccount(account);
			cust = customerRepository.save(customer);
		}
		return cust;
	}

	//Others Methods

	public List<Customer> customersWithMoreActivities() {
		return customerRepository.customersWithMoreActivities();
	}

	public Object[] minMaxAvgDesviationGymsByCustomers() {
		return customerRepository.minMaxAvgDesviationGymsByCustomers();
	}


	public Object[] avgDesviationNotesByCustomers() {
		return customerRepository.avgDesviationNotesByCustomers();
	}

	public Object[] avgDesviationStarsByCustomers() {
		Object[] res = customerRepository.avgDesviationStarsByCustomers();
		 if(res==null) {
			 Object[] aux = {0.0,0.0};
			 res=aux;
		 }
		return res;
	}

	public Double avgStarsCountryByCustomers() {
		Double res = customerRepository.avgStarsCountryByCustomers();
		if(res==null) {
			res=0.0;
		}
		return res;
	}

	public Double avgStarsCityByCustomers() {
		Double res = customerRepository.avgStarsCityByCustomers();
		if(res==null) {
			res=0.0;
		}
		return res;
	}

	public Double avgStarsByCustomer(int customer_id) {
		Assert.notNull(customer_id);
		return customerRepository.avgStarsByCustomer(customer_id);
	}



}
