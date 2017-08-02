package services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Customer;
import repositories.CustomerRepository;

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

	public Customer save(Customer arg0) {
		Assert.notNull(arg0);
		return customerRepository.save(arg0);
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
