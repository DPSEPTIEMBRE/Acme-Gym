package repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Activity;
import domain.Annotation;
import domain.Gym;
import domain.Trainer;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Integer> {

	//Entrenadores que ofrecen una actividad
	@Query("select a.trainers from Activity a where a.id=?1")
	List<Trainer> trainersByActivity(int activity_id);
	
	//Gimnasio que ofrece una actividad
	@Query("select a.gym from Activity a where a.id=?1")
	List<Gym> gymByActivity(int activity_id);
	
	//Notas asociadas a actividad
	@Query("select a.annotations from Activity a where a.id=?1")
	List<Annotation> annotationsByActivity(int activity_id);
}
