package services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.TabuWord;
import repositories.TabuWordRepository;

@Service
@Transactional
public class TabuWordService {
	
	//Manager repositories

	@Autowired
	private TabuWordRepository tabuWordRepository;

	//Constructor

	public TabuWordService() {
		super();
	}

	//CRUD Methods
	
	public TabuWord create() {
		TabuWord tabu = new TabuWord();

		tabu.setName(new String());
		
		return tabu;
	}
	
	public void delete(TabuWord arg0) {
		Assert.notNull(arg0);
		tabuWordRepository.delete(arg0);
	}

	public List<TabuWord> findAll() {
		return tabuWordRepository.findAll();
	}

	public TabuWord findOne(Integer arg0) {
		Assert.notNull(arg0);
		return tabuWordRepository.findOne(arg0);
	}

	public List<TabuWord> save(List<TabuWord> entities) {
		Assert.notNull(entities);
		Assert.noNullElements(entities.toArray());
		return tabuWordRepository.save(entities);
	}

	public boolean exists(Integer id) {
		return tabuWordRepository.exists(id);
	}

	public TabuWord save(TabuWord arg0) {
		Assert.notNull(arg0);
		TabuWord tabu= new TabuWord();
		if(exists(arg0.getId())) {
			tabu=tabuWordRepository.findOne(arg0.getId());
			tabu.setName(arg0.getName());
			return tabuWordRepository.save(tabu);
		}else {
			return tabuWordRepository.save(arg0);
		}
	}

	

}
