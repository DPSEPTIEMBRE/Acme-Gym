package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Trainer;

@Repository
public interface TrainerRepository extends JpaRepository<Trainer, Integer>  {

	//La media y desviaci�n est�ndar del n�mero de notas escritas por entrenador
	@Query("select avg(c.annotationWriter.size) from Trainer c")
	Object[] avgDesviationNotesWrittersByTrainers();

	//La media y desviaci�n est�ndar del n�mero de notas asociadas a entrenadores
	@Query("select avg(c.annotationStore.size) from Trainer c")
	Object[] avgDesviationNotesStoreByTrainers();
	
	//La media y desviaci�n est�ndar del n�mero de estrellas por entrenadores
	@Query("select avg(s.rate) from Trainer c join c.annotationStore s")
	Object[] avgDesviationStarsByTrainers();
	
	//La media de estrellas por entrenador, agrupadas por pa�s
	@Query("select avg(s.rate) from Trainer c join c.annotationStore s group by c.country ")
	Double avgStarsCountryByTrainers();

	//La media de estrellas por entrenador, agrupadas por ciudad
	@Query("select avg(s.rate) from Trainer c join c.annotationStore s group by c.city ")
	Double avgStarsCityByTrainers();
	
}
