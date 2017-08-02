package services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Trainer;
import repositories.TrainerRepository;

@Service
@Transactional
public class TrainerService {

	
	//Manager repositories

	@Autowired
	private TrainerRepository trainerRepository;

	//Constructor

	public TrainerService() {
		super();
	}

	//CRUD Methods
	
	public void delete(Trainer arg0) {
		Assert.notNull(arg0);
		trainerRepository.delete(arg0);
	}

	public List<Trainer> findAll() {
		return trainerRepository.findAll();
	}

	public Trainer findOne(Integer arg0) {
		Assert.notNull(arg0);
		return trainerRepository.findOne(arg0);
	}

	public List<Trainer> save(List<Trainer> entities) {
		Assert.notNull(entities);
		Assert.noNullElements(entities.toArray());
		return trainerRepository.save(entities);
	}

	public Trainer save(Trainer arg0) {
		Assert.notNull(arg0);
		return trainerRepository.save(arg0);
	}
	
	//Others Methods
	

	public Object[] avgDesviationNotesWrittersByTrainers() {
		return trainerRepository.avgDesviationNotesWrittersByTrainers();
	}

	public Object[] avgDesviationNotesStoreByTrainers() {
		return trainerRepository.avgDesviationNotesStoreByTrainers();
	}

	public Object[] avgDesviationStarsByTrainers() {
		return trainerRepository.avgDesviationStarsByTrainers();
	}

	public Double avgStarsCountryByTrainers() {
		return trainerRepository.avgStarsCountryByTrainers();
	}

	public Double avgStarsCityByTrainers() {
		return trainerRepository.avgStarsCityByTrainers();
	}


	
}
