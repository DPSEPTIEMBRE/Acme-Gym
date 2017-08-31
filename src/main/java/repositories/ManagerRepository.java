package repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Annotation;
import domain.Gym;
import domain.Manager;

@Repository
public interface ManagerRepository extends JpaRepository<Manager, Integer> {

	//La media y desviación estándar del número de notas asociadas a managers
	@Query("select avg(c.annotationStore.size + c.annotationWriter.size),stddev(c.annotationStore.size + c.annotationWriter.size) from Manager c")
	Object[] avgDesviationNotesByManagers();

	//La media y desviación estándar del número de estrellas por managers
	@Query("select avg(s.rate),stddev(s.rate) from Manager c join c.annotationStore s")
	Object[] avgDesviationStarsByManagers();

	//La media de estrellas por manager, agrupadas por país
	@Query("select avg(s.rate) from Manager c join c.annotationStore s group by c.country ")
	Double avgStarsCountryByManagers();

	//La media de estrellas por manager, agrupadas por ciudad
	@Query("select avg(s.rate) from Manager c join c.annotationStore s group by c.city ")
	Double avgStarsCityByManagers();

	//Lista de gimnasios por manager
	@Query("select c.gyms from Manager c where c.id=?1 ")
	List<Gym> gymsByManager(int manager_id);

	//El mínimo, máximo, media y desviación estándar del número de gimnasios por mánager
	@Query("select min(c.gyms.size), max(c.gyms.size), avg(c.gyms.size),stddev(c.gyms.size) from Manager c")
	Object[] minMaxAvgDesviationGymsByManagers();

	//Anotaciones por manager
	@Query("select c.annotationStore from Manager c where c.id=?1")
	List<Annotation> annotationsByManager(Integer manager_id);
	
	//Media estrellas asociadas a manager
	@Query("select avg(n.rate) from Manager a join a.annotationStore n where a.id=?1")
	Double avgStarsByManager(int manager_id);

}
