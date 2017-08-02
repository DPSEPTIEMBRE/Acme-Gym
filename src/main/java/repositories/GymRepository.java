package repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Activity;
import domain.Annotation;
import domain.Gym;

@Repository
public interface GymRepository extends JpaRepository<Gym, Integer>{

	//Lista de actividades de un gimnasio especifico
	@Query("select g.activities from Gym g where g.id=?1")
	List<Activity> activitiesByGym(int gym_id);
	
	//Gimnasio que ofrece mas actividades
	@Query("select g from Gym g where g.activities.size = (select max(a.activities.size) from Gym a)")
	List<Gym> gymWithMoreActivities();
	
	//El mínimo, máximo, media y desviación estándar del número de clientes por gimnasio
	@Query("select min(g.customers.size), max(g.customers.size), avg(g.customers.size) from Gym g")
	Object[] minMaxAvgDesviationCustomersForGym();
	
	//Notas asociadas a gimnasio
	@Query("select a.annotations from Gym a where a.id=?1")
	List<Annotation> annotationsByGym(int activity_id);
}
