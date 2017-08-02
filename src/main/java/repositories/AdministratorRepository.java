package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Administrator;

@Repository
public interface AdministratorRepository extends JpaRepository<Administrator, Integer> {

	//La media y desviaci�n est�ndar del n�mero de notas escritas por administrador
	@Query("select avg(c.annotationWriter.size) from Administrator c")
	Object[] avgDesviationNotesWrittersByAdministrators();

	//La media y desviaci�n est�ndar del n�mero de notas asociadas a administradores
	@Query("select avg(c.annotationStore.size) from Administrator c")
	Object[] avgDesviationNotesStoreByAdministrators();

	//La media y desviaci�n est�ndar del n�mero de estrellas por administradores
	@Query("select avg(s.rate) from Administrator c join c.annotationStore s")
	Object[] avgDesviationStarsByAdministrators();

	//La media de estrellas por administrador, agrupadas por pa�s
	@Query("select avg(s.rate) from Administrator c join c.annotationStore s group by c.country")
	Double avgStarsCountryByAdministrators();

	//La media de estrellas por administrador, agrupadas por ciudad
	@Query("select avg(s.rate) from Administrator c join c.annotationStore s group by c.city")
	Double avgStarsCityByAdministrators();
}
