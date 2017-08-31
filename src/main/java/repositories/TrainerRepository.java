package repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Annotation;
import domain.Trainer;

@Repository
public interface TrainerRepository extends JpaRepository<Trainer, Integer>  {
	
	//La media y desviación estándar del número de notas asociadas a entrenadores
	@Query("select avg(c.annotationStore.size + c.annotationWriter.size),stddev(c.annotationStore.size + c.annotationWriter.size) from Trainer c")
	Object[] avgDesviationNotesByTrainers();
	
	//La media y desviación estándar del número de estrellas por entrenadores
	@Query("select avg(s.rate),stddev(s.rate) from Trainer c join c.annotationStore s")
	Object[] avgDesviationStarsByTrainers();
	
	//La media de estrellas por entrenador, agrupadas por país
	@Query("select avg(s.rate) from Trainer c join c.annotationStore s group by c.country ")
	Double avgStarsCountryByTrainers();

	//La media de estrellas por entrenador, agrupadas por ciudad
	@Query("select avg(s.rate) from Trainer c join c.annotationStore s group by c.city ")
	Double avgStarsCityByTrainers();
	
	//Anotaciones por entrenador
	@Query("select c.annotationStore from Trainer c where c.id=?1 ")
	List<Annotation> annotationsByTrainer(Integer trainer_id);
	
	//Media estrellas asociadas a trainer
	@Query("select avg(n.rate) from Trainer a join a.annotationStore n where a.id=?1")
	Double avgStarsByTrainer(int trainer_id);
	
}
