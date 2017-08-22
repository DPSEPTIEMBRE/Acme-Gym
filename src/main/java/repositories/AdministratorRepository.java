package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Administrator;

@Repository
public interface AdministratorRepository extends JpaRepository<Administrator, Integer> {

	//La media y desviación estándar del número de notas asociadas a administradores
	@Query("select avg(c.annotationStore.size + c.annotationWriter.size) from Administrator c")
	Object[] avgDesviationNotesByAdministrators();


	//La media y desviación estándar del número de estrellas por administradores
	@Query("select avg(s.rate) from Administrator c join c.annotationStore s")
	Object[] avgDesviationStarsByAdministrators();

	//La media de estrellas por administrador, agrupadas por país
	@Query("select avg(s.rate) from Administrator c join c.annotationStore s group by c.country")
	Double avgStarsCountryByAdministrators();

	//La media de estrellas por administrador, agrupadas por ciudad
	@Query("select avg(s.rate) from Administrator c join c.annotationStore s group by c.city")
	Double avgStarsCityByAdministrators();
	
	//Media estrellas asociadas a administrador
	@Query("select avg(n.rate) from Administrator a join a.annotationStore n where a.id=?1")
	Double avgStarsByAdministrator(int administrator_id);

}
