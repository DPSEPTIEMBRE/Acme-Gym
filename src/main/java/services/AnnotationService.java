package services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Annotation;
import repositories.AnnotationRepository;

@Service
@Transactional
public class AnnotationService {

	//Manager repositories

	@Autowired
	private AnnotationRepository annotationRepository;

	//Constructor

	public AnnotationService() {
		super();
	}

	//CRUD Methods
	
	public void delete(Annotation arg0) {
		Assert.notNull(arg0);
		annotationRepository.delete(arg0);
	}

	public List<Annotation> findAll() {
		return annotationRepository.findAll();
	}

	public Annotation findOne(Integer arg0) {
		Assert.notNull(arg0);
		return annotationRepository.findOne(arg0);
	}

	public List<Annotation> save(List<Annotation> entities) {
		Assert.notNull(entities);
		Assert.noNullElements(entities.toArray());
		return annotationRepository.save(entities);
	}

	public Annotation save(Annotation arg0) {
		Assert.notNull(arg0);
		return annotationRepository.save(arg0);
	}
	
	

}
